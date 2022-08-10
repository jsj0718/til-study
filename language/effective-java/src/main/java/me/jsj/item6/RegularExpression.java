package me.jsj.item6;

import java.util.regex.Pattern;

public class RegularExpression {

    private static final Pattern SPLIT_PATTERN = Pattern.compile(",");

    public static long getSplitTime(String name, String regex) {
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            name.split(regex);
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static long getSplitTimeUsingPattern(String name) {
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            SPLIT_PATTERN.split(name);
        }
        long end = System.nanoTime();
        return end - start;
    }
}
