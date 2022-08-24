package me.jsj.thejava.codemanipulation.dynamicproxy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    Book book;
    BookService bookService;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));

        book = new Book();
        book.setTitle("the java");
        setProxyBookService();
    }

    private void setProxyBookService() {
        bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
                new InvocationHandler() {
                    BookService bookService = new DefaultBookService();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("rent")) {
                            System.out.println(">>>>>> 로그 a");
                            Object invoke = method.invoke(bookService, args);
                            System.out.println(">>>>>> 로그 b");
                            return invoke;
                        }
                        return method.invoke(bookService, args);
                    }
                });
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);

        book = null;
        bookService = null;
    }

    @Test
    void testProxyPattern_rent() {
        BookService bookService = new ProxyBookService();
        bookService.rent(book);

        assertThat(outContent.toString()).contains(">>>>>> 로그 a", "rent = the java", ">>>>>> 로그 b");
    }

    @Test
    void testProxyPattern_returnBookTitle() {
        BookService bookService = new ProxyBookService();
        bookService.returnBookTitle(book);

        assertThat(outContent.toString()).contains("returnBookTitle = the java");
    }

    @Test
    void testProxyReflection_rent() {
        bookService.rent(book);
        assertThat(outContent.toString()).contains(">>>>>> 로그 a", "rent = the java", ">>>>>> 로그 b");
    }

    @Test
    void testProxyReflection_returnBookTitle() {
        bookService.returnBookTitle(book);
        assertThat(outContent.toString()).contains("returnBookTitle = the java");
    }
}