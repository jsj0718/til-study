package me.jsj.item7.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    //pop()을 진행해도 elements는 계속해서 자원을 참조한다.
    public Object popV1() {
        if (size == 0) throw new EmptyStackException();
        return elements[--size];
    }

    //null을 참조함으로써 자원 해제
    public Object popV2() {
        if (size == 0) throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size);
        }
    }
}
