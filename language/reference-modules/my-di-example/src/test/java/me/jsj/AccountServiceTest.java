package me.jsj;

import me.jsj.thejava.codemanipulation.reflection.mydi.ContainerService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest {

    @Test
    void testDI() {
        AccountRepository accountRepository = ContainerService.getObject(AccountRepository.class);
        assertThat(accountRepository).isNotNull();

        AccountService accountService = ContainerService.getObject(AccountService.class);
        assertThat(accountService).isNotNull();
        assertThat(accountService.accountRepository).isNotNull();

        accountService.join();
    }
}