package me.jsj.practice.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTest {

    @Test
    void simpleProxy() {
        Hello hello = new HelloTarget();

        assertThat(hello.sayHello("jsj")).isEqualTo("Hello jsj");
        assertThat(hello.sayHi("jsj")).isEqualTo("Hi jsj");
        assertThat(hello.sayThankYou("jsj")).isEqualTo("Thank You jsj");
    }

    @Test
    void uppercaseProxy() {
        HelloUppercase proxyHello = new HelloUppercase(new HelloTarget());

        assertThat(proxyHello.sayHello("jsj")).isEqualTo("HELLO JSJ");
        assertThat(proxyHello.sayHi("jsj")).isEqualTo("HI JSJ");
        assertThat(proxyHello.sayThankYou("jsj")).isEqualTo("THANK YOU JSJ");
    }

    @Test
    void uppercaseDynamicProxy() {
        Hello proxyHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );

        assertThat(proxyHello.sayHello("jsj")).isEqualTo("HELLO JSJ");
        assertThat(proxyHello.sayHi("jsj")).isEqualTo("HI JSJ");
        assertThat(proxyHello.sayThankYou("jsj")).isEqualTo("THANK YOU JSJ");
    }

    @Test
    void proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Hello proxyHello = (Hello) proxyFactoryBean.getObject();

        assertThat(proxyHello.sayHello("jsj")).isEqualTo("HELLO JSJ");
        assertThat(proxyHello.sayHi("jsj")).isEqualTo("HI JSJ");
        assertThat(proxyHello.sayThankYou("jsj")).isEqualTo("THANK YOU JSJ");
    }

    @Test
    void pointcutAdvice() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxyHello = (Hello) proxyFactoryBean.getObject();

        assertThat(proxyHello.sayHello("jsj")).isEqualTo("HELLO JSJ");
        assertThat(proxyHello.sayHi("jsj")).isEqualTo("HI JSJ");
        assertThat(proxyHello.sayThankYou("jsj")).isEqualTo("Thank You jsj");
    }

    @Test
    void classNamePointcutAdvisor() {
        //포인트컷 준비
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            public ClassFilter getClassFilter() {
                return clazz -> clazz.getSimpleName().startsWith("HelloT");
            }
        };

        classMethodPointcut.setMappedName("sayH*");

        //테스트
        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget {};
        checkAdviced(new HelloWorld(), classMethodPointcut, false);

        class HelloToby extends HelloTarget {};
        checkAdviced(new HelloToby(), classMethodPointcut, true);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxyHello = (Hello) proxyFactoryBean.getObject();

        if (adviced) {
            assertThat(proxyHello.sayHello("jsj")).isEqualTo("HELLO JSJ");
            assertThat(proxyHello.sayHi("jsj")).isEqualTo("HI JSJ");
            assertThat(proxyHello.sayThankYou("jsj")).isEqualTo("Thank You jsj");
        } else {
            assertThat(proxyHello.sayHello("jsj")).isEqualTo("Hello jsj");
            assertThat(proxyHello.sayHi("jsj")).isEqualTo("Hi jsj");
            assertThat(proxyHello.sayThankYou("jsj")).isEqualTo("Thank You jsj");
        }
    }


    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }
}