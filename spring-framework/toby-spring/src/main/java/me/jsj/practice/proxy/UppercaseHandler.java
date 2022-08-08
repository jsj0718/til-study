package me.jsj.practice.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    Object target;

    public UppercaseHandler(Object target) {
        this.target = target; //위임을 위해 주입 받는다.
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);  //타겟으로 위임. 인터페이스의 메소드 모든 호출에 적용
        if (ret instanceof String && method.getName().startsWith("say")) {
            return ((String) ret).toUpperCase(); //부가기능 제공
        }
        return ret;
    }
}
