package me.jsj.domain.user.service.chapter6;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jsj.domain.user.Level;
import me.jsj.domain.user.UserV2;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserServiceCh6V1 {

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    private final UserDaoCh5V1 userDao;
    private final MailSender mailSender;

    public void upgradeLevels() {
        userDao.getAll().stream().forEach(user -> {
            if (canUpgradeLevel(user)) upgradeLevel(user);
        });
    }

    public boolean canUpgradeLevel(UserV2 user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    public void upgradeLevel(UserV2 user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(UserV2 user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님 등급이 " + user.getLevel().name());

        mailSender.send(mailMessage);
    }

    public void add(UserV2 user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    @Override
    public UserV2 get(String id) {
        return userDao.get(id);
    }

    @Override
    public List<UserV2> getAll() {
        return userDao.getAll();
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public void update(UserV2 user) {
        userDao.update(user);
    }
}
