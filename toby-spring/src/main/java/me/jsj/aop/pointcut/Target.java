package me.jsj.aop.pointcut;

public class Target implements TargetInterface {
    @Override
    public void hello() {

    }

    @Override
    public void hello(String s) {

    }

    @Override
    public int minus(int a, int b) throws RuntimeException {
        return 0;
    }

    @Override
    public int plus(int a, int b) throws RuntimeException {
        return 0;
    }

    public void method() {

    }
}
