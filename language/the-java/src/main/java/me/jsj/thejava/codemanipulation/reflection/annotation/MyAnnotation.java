package me.jsj.thejava.codemanipulation.reflection.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    String value() default "jsj";

    int number() default 10;
}
