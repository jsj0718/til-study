package me.jsj.config;

import lombok.RequiredArgsConstructor;
import me.jsj.aop.advice.TransactionAdvice;
import me.jsj.aop.factorybean.TxProxyFactoryBean;
import me.jsj.aop.pointcut.NameMatchClassMethodPointcut;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;
import me.jsj.domain.user.service.chapter6.UserServiceCh6V1;
import me.jsj.domain.user.service.chapter6.UserServiceImpl;
import me.jsj.domain.user.service.chapter6.UserServiceTx;
import me.jsj.domain.user.service.chapter5.MockMailSender;
import me.jsj.practice.factorybean.Message;
import me.jsj.practice.factorybean.MessageFactoryBean;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class BeanConfig {
    private final UserDaoCh5V1 userDao;
    private final PlatformTransactionManager transactionManager;

/*
    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.server.com");
        return mailSender;
    }
*/

    @Bean
    public MailSender mockMailSender() {
        return new MockMailSender();
    }

    @Bean
    public MessageFactoryBean message() {
        return new MessageFactoryBean("Factory Bean");
    }

/*
    @Bean
    @Qualifier("userServiceTx")
    public UserServiceCh6V1 userServiceTx() {
        return new UserServiceTx(userServiceImpl(), transactionManager);
    }
*/

    @Bean
    public UserServiceCh6V1 userService() {
        return new UserServiceImpl(userDao, mockMailSender());
    }


/*
    @Bean
    public TxProxyFactoryBean userService() {
        return new TxProxyFactoryBean(userServiceImpl(), transactionManager, "upgradeLevels", UserServiceCh6V1.class);
    }
*/

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager);
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
        pointcut.setMappedClassName("*ServiceImpl");
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

/*
    @Bean
    public ProxyFactoryBean userService() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userServiceImpl());
        proxyFactoryBean.setInterceptorNames("transactionAdvisor");
        return proxyFactoryBean;
    }
*/
}
