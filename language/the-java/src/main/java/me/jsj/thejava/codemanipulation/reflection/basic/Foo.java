package me.jsj.thejava.codemanipulation.reflection.basic;

public class Foo {
    public static String publicStaticStringField = "publicStaticStringField";

    private String privateStringField = "privateStringField";

    public Foo() {
    }

    public Foo(String privateStringField) {
        this.privateStringField = privateStringField;
    }

    private void privateVoidMethod() {
        System.out.println("privateVoidMethod");
    }

    public int sum(int a, int b) {
        return a + b;
    }
}
