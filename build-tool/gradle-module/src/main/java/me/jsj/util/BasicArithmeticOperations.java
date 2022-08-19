package me.jsj.util;

import java.math.BigDecimal;

public class BasicArithmeticOperations {
    public static BigDecimal plus(String a, String b) {
        return plus(new BigDecimal(a), new BigDecimal(b));
    }

    public static BigDecimal plus(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public static BigDecimal minus(String a, String b) {
        return minus(new BigDecimal(a), new BigDecimal(b));
    }

    public static BigDecimal minus(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public static BigDecimal multiply(String a, String b) {
        return multiply(new BigDecimal(a), new BigDecimal(b));
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public static BigDecimal divide(String a, String b) {
        return divide(new BigDecimal(a), new BigDecimal(b));
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b);
    }


}
