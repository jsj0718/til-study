package me.jsj.item8.outerclass;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LambdaExampleTest {

    @Test
    void testLambda() {
        LambdaExample example = new LambdaExample();
        Field[] declaredFields = example.instanceLambda.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("field.getType() = " + field.getType());
            System.out.println("field.getName() = " + field.getName());
        }
    }
}