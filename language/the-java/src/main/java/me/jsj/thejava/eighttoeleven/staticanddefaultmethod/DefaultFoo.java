package me.jsj.thejava.eighttoeleven.staticanddefaultmethod;

public class DefaultFoo implements Foo {

    private String name;

    public DefaultFoo(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(name);
    }

    @Override
    public void printNameUpperCase() {
        System.out.println(name.toUpperCase());
    }

    @Override
    public String getName() {
        return name;
    }
}
