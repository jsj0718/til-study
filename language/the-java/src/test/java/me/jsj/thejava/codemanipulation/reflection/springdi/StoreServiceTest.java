package me.jsj.thejava.codemanipulation.reflection.springdi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoreServiceTest {

    @Autowired
    StoreService storeService;

    @Test
    void testDI() {
        assertThat(storeService).isNotNull();
        assertThat(storeService.getBookRepository()).isNotNull();

    }
}