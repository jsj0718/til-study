package me.jsj.item3.functionalinterface;

@FunctionalInterface
public interface MyFunction {
    String valueOf(Integer integer);

    static String hello() {
        return "Hello";
    }
}
