package me.jsj.thejava.codemanipulation.dynamicproxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

class BookStoreServiceTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testCGLIB_hi() {
        BookStoreService bookStoreService = (BookStoreService) Enhancer.create(BookStoreService.class, getMethodInterceptor());
        bookStoreService.hi();

        assertThat(outContent.toString()).contains("aaaa", "Hi", "bbbb");
    }

    @Test
    void testCGLIB_bye() {
        BookStoreService bookStoreService = (BookStoreService) Enhancer.create(BookStoreService.class, getMethodInterceptor());
        bookStoreService.bye();

        assertThat(outContent.toString()).contains("Bye");
        assertThat(outContent.toString()).doesNotContain("aaaa", "bbbb");
    }

    private MethodInterceptor getMethodInterceptor() {
        MethodInterceptor handler = new MethodInterceptor() {
            BookStoreService bookStoreService = new BookStoreService();

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getName().equals("hi")) {
                    System.out.println("aaaa");
                    Object invoke = method.invoke(bookStoreService, args);
                    System.out.println("bbbb");
                    return invoke;
                }
                return method.invoke(bookStoreService, args);
            }
        };
        return handler;
    }

    @Test
    void testByteBuddy_hi() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends BookStoreService> proxyClass = getProxyClass("hi", "aaaa", "bbbb");

        BookStoreService bookStoreService = proxyClass.getConstructor().newInstance();
        bookStoreService.hi();

        assertThat(outContent.toString()).contains("aaaa", "Hi", "bbbb");
    }

    @Test
    void testByteBuddy_bye() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends BookStoreService> proxyClass = getProxyClass("bye", "test1", "test2");

        BookStoreService bookStoreService = proxyClass.getConstructor().newInstance();
        bookStoreService.bye();

        assertThat(outContent.toString()).contains("test1", "Bye", "test2");
    }

    private Class<? extends BookStoreService> getProxyClass(String methodName, String log1, String log2) {
        Class<? extends BookStoreService> proxyClass = new ByteBuddy().subclass(BookStoreService.class)
                .method(named(methodName)).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    BookStoreService bookStoreService = new BookStoreService();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(log1);
                        Object invoke = method.invoke(bookStoreService, args);
                        System.out.println(log2);
                        return invoke;
                    }
                }))
                .make().load(BookStoreService.class.getClassLoader()).getLoaded();
        return proxyClass;
    }
}