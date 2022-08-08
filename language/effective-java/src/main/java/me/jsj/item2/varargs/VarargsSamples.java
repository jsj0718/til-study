package me.jsj.item2.varargs;

import java.util.Arrays;

public class VarargsSamples {

    public void printNumbers(int... numbers) {
        System.out.println(numbers.getClass().getCanonicalName());
        System.out.println(numbers.getClass().getComponentType());
        Arrays.stream(numbers).forEach(System.out::println);
    }

/*
    public static void main(String[] args) {
        VarargsSamples varargsSamples = new VarargsSamples();
        varargsSamples.printNumbers(1, 2, 3);
    }
*/
}
