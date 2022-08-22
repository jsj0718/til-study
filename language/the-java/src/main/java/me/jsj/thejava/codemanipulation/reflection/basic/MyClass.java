package me.jsj.thejava.codemanipulation.reflection.basic;

import me.jsj.thejava.codemanipulation.reflection.annotation.MyAnnotation;

@MyAnnotation
public class MyClass {
    private static String privateStaticField = "privateStaticField";

    private static final String privateStaticFinalField = "privateStaticFinalField";
    private String privateField = "privateField";

    @MyAnnotation
    public String publicField = "publicField";

     protected String protectedField = "protectedField";

    public MyClass() {
    }

    public MyClass(String privateField, String publicField, String protectedField) {
        this.privateField = privateField;
        this.publicField = publicField;
        this.protectedField = protectedField;
    }

    private String privateMethod() {
        return "privateMethod";
    }

    public String publicMethod() {
        return "publicMethod";
    }

    public Integer publicIntegerMethod() {
        return 100;
    }
}
