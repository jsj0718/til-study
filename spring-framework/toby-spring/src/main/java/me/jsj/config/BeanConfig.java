package me.jsj.config;

import lombok.RequiredArgsConstructor;
import me.jsj.domain.user.service.chapter5.MockMailSender;
import me.jsj.practice.factorybean.MessageFactoryBean;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

@RequiredArgsConstructor
@Configuration
public class BeanConfig {
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

/*
    @Bean
    public UserServiceCh6V1 userService() {
        return new UserServiceImpl(userDao, mockMailSender());
    }
*/


/*
    @Bean
    public TxProxyFactoryBean userService() {
        return new TxProxyFactoryBean(userServiceImpl(), transactionManager, "upgradeLevels", UserServiceCh6V1.class);
    }
*/

/*
    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager);
    }
*/

    @Bean
    public TransactionInterceptor transactionAdvice() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        Properties properties = new Properties();
        properties.setProperty("get*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("*", "PROPAGATION_REQUIRED");
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
    }

/*
    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
        pointcut.setMappedClassName("*ServiceImpl");
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }
*/

    @Bean
    public AspectJExpressionPointcut transactionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* *..*ServiceImpl.upgrade*(..))");
//        pointcut.setExpression("execution(* *..*Service*.*(..))");
        pointcut.setExpression("bean(*Service)");
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
