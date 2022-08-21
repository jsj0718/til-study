package me.jsj.thejava.eighttoeleven.completablefuture.callable;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println(helloFuture.isDone());
        System.out.println("Start");

        helloFuture.cancel(false);

        System.out.println(helloFuture.isDone());

        helloFuture.get();

        System.out.println("End");

        executorService.shutdown();
*/

/*
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000);
            return "Hello";
        };

        Callable<String> jsj = () -> {
            Thread.sleep(3000);
            return "JSJ";
        };

        Callable<String> java = () -> {
            Thread.sleep(1000);
            return "Java";
        };

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, jsj, java));
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        executorService.shutdown();
*/

/*
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Callable<String> hello = () -> {
            Thread.sleep(2000);
            return "Hello";
        };

        Callable<String> jsj = () -> {
            Thread.sleep(3000);
            return "JSJ";
        };

        Callable<String> java = () -> {
            Thread.sleep(1000);
            return "Java";
        };

        String result = executorService.invokeAny(Arrays.asList(hello, jsj, java));
        System.out.println(result);

        executorService.shutdown();
    }
*/
}
