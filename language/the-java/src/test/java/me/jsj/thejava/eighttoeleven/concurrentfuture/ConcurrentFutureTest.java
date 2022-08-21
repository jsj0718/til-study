package me.jsj.thejava.eighttoeleven.concurrentfuture;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentFutureTest {

    @Test
    void testFuture() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<String> future = service.submit(() -> "hello");

        //Blocking Call (아래 로직은 이것이 실행이 끝날 때까지 진행되지 않는다.)
        assertThat(future.get()).isEqualTo("hello");
    }

    @Test
    void testCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("jsj"); //기본값 설정

        assertThat(future.get()).isEqualTo("jsj");
    }

    @Test
    void testCompletableFutureUsingStaticFactoryMethod() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("jsj");

        assertThat(future.get()).isEqualTo("jsj");
    }

    @Test
    void testRunAsync() throws ExecutionException, InterruptedException {
        //반환값이 없는 경우 runAsync() 사용
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("Hello " + Thread.currentThread().getName()));

        //get()의 경우 체크 예외를 던져야 한다.
        future.get();

        //join()의 경우 체크 예외를 언체크 예외로 변경해주므로 예외를 던질 필요가 없다.
//        future.join();
    }

    @Test
    void testSupplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        assertThat(future.get()).isEqualTo("Hello");
    }

    @Test
    void testThreadPoolAsync() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello Future: " + Thread.currentThread().getName());
            return "Hello";
        }, service).thenRunAsync(() -> {
            System.out.println("Hello Callback: " + Thread.currentThread().getName());
        }, service);

        assertThat(future.get()).isNull();
    }

    @Test
    void testAsyncUsingThenApply() throws ExecutionException, InterruptedException {
        //CompletableFuture를 통해 get() 호출 이전에 callback을 정의할 수 있다.
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello Future: " + Thread.currentThread().getName());
            return "Hello";
        }).thenApply(s -> {
            System.out.println("parameter = " + s);
            System.out.println("Hello Callback: " + Thread.currentThread().getName());
            return s.toUpperCase();
        });

        assertThat(future.get()).isEqualTo("HELLO");
    }

    @Test
    void testAsyncUsingThenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello Future: " + Thread.currentThread().getName());
            return "Hello";
        }).thenAccept(s -> {
            System.out.println("parameter = " + s);
            System.out.println("Hello Callback: " + Thread.currentThread().getName());
        });

        assertThat(future.get()).isNull();
    }

    @Test
    void testAsyncUsingThenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello Future: " + Thread.currentThread().getName());
            return "Hello";
        }).thenRun(() -> {
            System.out.println("Hello Callback: " + Thread.currentThread().getName());
        });

        assertThat(future.get()).isNull();
    }

    @Test
    void testMultiFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        String result = "";

        result += hello.get();
        result += world.get();

        assertThat(result).isEqualTo("HelloWorld");
    }

    @Test
    void testMultiFutureUsingCompose() throws ExecutionException, InterruptedException {
        //두 Future 간 연관관계가 존재하는 경우
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> future = hello.thenCompose(s -> getWorld(s));

        assertThat(future.get()).isEqualTo("HelloWorld");
    }

    private CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return message + "World";
        });
    }

    @Test
    void testMultiFutureUsingCombine() throws ExecutionException, InterruptedException {
        //두 Future 간 연관관계가 존재하지 않는 경우
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> h + w);

        assertThat(future.get()).isEqualTo("HelloWorld");
    }

    @Test
    void testMultiFutureUsingAllOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        List<CompletableFuture> futures = Arrays.asList(hello, world);

        //allOf()는 Task들을 모아서 한 번에 Future 지정
        //모든 Task가 같은 타입일거라는 보장이 없고, 중간에 오류가 발생했을 수도 있기 때문에 타입은 Void이다.
//        CompletableFuture<Void> future = CompletableFuture.allOf(hello, world)
//                .thenAccept(System.out::println);
//        assertThat(future.get()).isNull();

        //NonBlocking
        CompletableFuture[] futureArray = futures.toArray(new CompletableFuture[futures.size()]);
        CompletableFuture<List<Object>> futureList = CompletableFuture.allOf(futureArray)
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));

        assertThat(futureList.get().get(0)).isEqualTo("Hello");
        assertThat(futureList.get().get(1)).isEqualTo("World");
    }

    @Test
    void testMultiFutureUsingAnyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        //hello 또는 world 중 먼저 실행되는 것만 진행
        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, world).thenAccept(System.out::println);
        future.get();
    }

    @Test
    void testAsyncExceptionUsingExceptionally() throws ExecutionException, InterruptedException {
        boolean throwError = true;

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (throwError) throw new IllegalArgumentException();
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(e -> "Error");

        if (throwError) assertThat(future.get()).isEqualTo("Error");
        else assertThat(future.get()).isEqualTo("Hello");
    }

    @Test
    void testAsyncExceptionUsingHandle() throws ExecutionException, InterruptedException {
        boolean throwError = true;

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (throwError) throw new IllegalArgumentException();
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result, ex) -> {
            if (ex == null) return result;
            return "Error";
        });

        if (throwError) assertThat(future.get()).isEqualTo("Error");
        else assertThat(future.get()).isEqualTo("Hello");
    }

}
