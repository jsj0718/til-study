package me.jsj.item6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralsTest {

    @Test
    void regexTest() {
        String s = "MCMLXXVI";

        boolean result = false;
        long fastStart = System.nanoTime();
        for (int i=0; i<100; i++) {
            result = RomanNumerals.isRomanNumeralFast(s);
        }
        long fastEnd = System.nanoTime();
        long fastResult = fastEnd - fastStart;

        result = false;
        long slowStart = System.nanoTime();
        for (int i=0; i<100; i++) {
            result = RomanNumerals.isRomanNumeralSlow(s);
        }
        long slowEnd = System.nanoTime();
        long slowResult = slowEnd - slowStart;

        assertThat(slowResult).isGreaterThan(fastResult);
    }
}