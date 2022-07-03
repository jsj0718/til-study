package me.jsj.practice.effectivejava.item1;

import me.jsj.item1.advantage3.HelloServiceV2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EffectiveJavaItem1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("me.jsj.hello.KoreanHelloService");
        Constructor<?> constructor = aClass.getConstructor();
        HelloServiceV2 helloServiceV2 = (HelloServiceV2) constructor.newInstance();
        
        System.out.println(helloServiceV2.hello());
    }
}
