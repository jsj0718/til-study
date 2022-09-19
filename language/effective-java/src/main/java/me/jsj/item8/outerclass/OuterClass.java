package me.jsj.item8.outerclass;

import java.lang.reflect.Field;

public class OuterClass {
    class InnerClass {
        public void hello() {
            //Inner Class에서 Outer Class의 private 메소드를 참조하는 방법
            OuterClass.this.hi();
        }
    }

    private void hi() {

    }

    protected void printField() {
        Field[] declaredFields = InnerClass.class.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("field.getType() = " + field.getType());
            System.out.println("field.getName() = " + field.getName());
        }
    }
}
