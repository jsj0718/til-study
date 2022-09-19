package me.jsj.item8.outerclass;

import org.junit.jupiter.api.Test;

class OuterClassTest {

    @Test
    void testOuterAndInnerClass() {
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();

        System.out.println("innerClass = " + innerClass);

        outerClass.printField();
    }
}