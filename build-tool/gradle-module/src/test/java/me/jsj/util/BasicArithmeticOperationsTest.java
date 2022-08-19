package me.jsj.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicArithmeticOperationsTest {
    @Test
    void testAdd() {
        assertThat(new BigDecimal(2)).isEqualTo(BasicArithmeticOperations.plus("1", "1"));
        assertThat(new BigDecimal(2)).isEqualTo(BasicArithmeticOperations.plus(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void testMinus() {
        assertThat(BigDecimal.ZERO).isEqualTo(BasicArithmeticOperations.minus("1", "1"));
        assertThat(BigDecimal.ZERO).isEqualTo(BasicArithmeticOperations.minus(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void testMultiply() {
        assertThat(BigDecimal.ONE).isEqualTo(BasicArithmeticOperations.multiply("1", "1"));
        assertThat(BigDecimal.ONE).isEqualTo(BasicArithmeticOperations.multiply(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void testDivide() {
        assertThat(BigDecimal.ONE).isEqualTo(BasicArithmeticOperations.divide("1", "1"));
        assertThat(BigDecimal.ONE).isEqualTo(BasicArithmeticOperations.divide(BigDecimal.ONE, BigDecimal.ONE));
        assertThrows(ArithmeticException.class, () -> {
            BasicArithmeticOperations.divide(BigDecimal.ONE, BigDecimal.ZERO);
        });
    }
}