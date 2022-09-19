package me.jsj.item8.finalizer_attack;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void 일반_계정() {
        Account account = new Account("jsj");
        account.transfer(BigDecimal.valueOf(10.4), "hello");
    }

    @Test
    void 푸틴_계정() {
        assertThrows(IllegalArgumentException.class, () -> new Account("푸틴"));
    }

    @Test
    void 푸틴_계정_broken() throws InterruptedException {
        Account account = null;
        try {
            account = new BrokenAccount("푸틴");
        } catch (Exception e) {
            System.out.println("예외 발생 이후 GC 실행");
        }

        System.gc();
        Thread.sleep(3000L);
    }
}