package me.jsj.thejava.codemanipulation.dynamicproxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testDI() {
        assertThat(bookRepository).isNotNull();

        Book book = new Book();
        book.setTitle("the java");
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getTitle()).isEqualTo("the java");
    }
}