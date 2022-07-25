package me.jsj.aop.factorybean;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.jsj.aop.handler.TransactionHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

@Setter
@AllArgsConstructor
public class TxProxyFactoryBean implements FactoryBean<Object> {

    private Object target;
    private PlatformTransactionManager transactionManager;
    private String pattern;
    private Class<?> serviceInterface; //다이나믹 프록시 생성 시 필요 (UserService 외 인터페이스를 가진 타깃에도 적용 가능)

    @Override
    public Object getObject() throws Exception {
        TransactionHandler transactionHandler = new TransactionHandler(target, transactionManager, pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[] {serviceInterface}, transactionHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false; //getObject()는 같은 객체를 반환하지 않는다.
    }
}
