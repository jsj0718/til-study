package me.jsj.thejava.codemanipulation.reflection.basic;

import me.jsj.thejava.codemanipulation.reflection.annotation.AnotherAnnotation;

@AnotherAnnotation
public class MyExtendedClass extends MyClass implements MyInterface{
    public MyExtendedClass(String privateField, String publicField, String protectedField) {
        super(privateField, publicField, protectedField);
    }
}
