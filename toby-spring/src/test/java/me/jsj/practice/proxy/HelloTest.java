package me.jsj.practice.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

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

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }
}