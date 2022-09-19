package me.jsj.item8.autoclosable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutoClosableIsGoodTest {

    @Test
    void testAutoClosable() {
        try (AutoClosableIsGood good = new AutoClosableIsGood()) {
            //TODO 자원 반납 처리 완료
        } catch (Exception e) {
            if (e.getClass().equals(NullPointerException.class)) {
                System.out.println("자원 반납 성공 했으나 inputStream이 null이여서 NPE 발생");
            }
        }
    }
}