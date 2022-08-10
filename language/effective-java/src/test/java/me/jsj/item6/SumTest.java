package me.jsj.item6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SumTest {

    @Test
    void diffBoxingAndPrimitive() {
        long boxingStart = System.nanoTime();
        Sum.sumByBoxing();
        long boxingEnd = System.nanoTime();
        long boxingResult = boxingEnd - boxingStart;

        long primitiveStart = System.nanoTime();
        Sum.sumByPrimitive();
        long primitiveEnd = System.nanoTime();
        long primitiveResult = primitiveEnd - primitiveStart;

        assertThat(boxingResult).isGreaterThan(primitiveResult);
    }
}