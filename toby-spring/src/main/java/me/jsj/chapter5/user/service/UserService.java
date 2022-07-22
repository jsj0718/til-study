package me.jsj.chapter5.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jsj.chapter5.user.dao.UserDao;
import me.jsj.chapter5.user.domain.Level;
import me.jsj.chapter5.user.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Getter
@RequiredArgsConstructor
@Service
public class UserService implements UserLevelUpgradePolicy {

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    private final UserDao userDao;
    private final PlatformTransactionManager transactionManager;
    private final MailSender mailSender;

    /*
        //V1
        public void upgradeLevels() {
            List<User> users = userDao.getAll();
            users.stream().forEach(user -> {
                Boolean changed = null;
                if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                    user.setLevel(Level.SILVER);
                    changed = true;
                } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                    user.setLevel(Level.GOLD);
                    changed = true;
                } else if (user.getLevel() == Level.GOLD) {
                    changed = false;
                } else {
                    changed = false;
                }

                if (changed) {
                    userDao.update(user);
                }
            });
        }
    */
/*
    //V2
    public void upgradeLevels() throws Exception {
        TransactionSynchronizationManager.initSynchronization(); //트랜잭션 동기화 관리자를 통해 동기화 작업 초기화
        Connection conn = DataSourceUtils.getConnection(dataSource); //DataSourceUtils.getConnection()은 DB 커넥션 생성과 동기화를 함께 지원해주는 유틸리티 메소드이다.
        conn.setAutoCommit(false);

        try {
            userDao.getAll().stream().forEach(user -> {
                if (canUpgradeLevel(user)) upgradeLevel(user);
            });
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource); //스프링 유틸리티 메소드를 통해 DB 커넥션을 닫는다.
            //동기화 작업 종료 및 정리
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }
*/

    //V3
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userDao.getAll().stream().forEach(user -> {
                if (canUpgradeLevel(user)) upgradeLevel(user);
            });
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

/*
    //V1
    private void sendUpgradeEmail(User user) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.ksug.org");
        Session s = Session.getInstance(props, null);

        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress("useradmin@ksug.org"));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(user.getEmail())));
            mimeMessage.setSubject("Upgrade 안내");
            mimeMessage.setText("사용자 님의 등급이 " + user.getLevel().name() + "로 승급하였습니다.");
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
*/
    //V2
    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님 등급이 " + user.getLevel().name());

        mailSender.send(mailMessage);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
