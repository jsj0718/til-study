package me.jsj.thejava.eighttoeleven.staticanddefaultmethod;

public interface Bar {
    default void printNameUpperCase() {
        System.out.println("BAR");
    }
}
