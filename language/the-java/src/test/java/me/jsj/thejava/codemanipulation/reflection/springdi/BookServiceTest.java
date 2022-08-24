package me.jsj.thejava.codemanipulation.reflection.springdi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    void testDI() {
        assertThat(bookService).isNotNull();
        assertThat(bookService.getBookRepository()).isNotNull();

    }
}