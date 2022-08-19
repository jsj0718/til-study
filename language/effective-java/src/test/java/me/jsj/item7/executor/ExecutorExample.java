package me.jsj.item7.executor;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ExecutorExample {

    @Test
    void threadTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new CustomThread(countDownLatch);
        thread.start();

        System.out.println(Thread.currentThread() + " hello");

        countDownLatch.await();
    }

    @Test
    void threadTestUsingRunnable() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);

        //쓰레드를 계속해서 생성 -> 자원 낭비가 심하다.
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Task(countDownLatch));
            thread.start();
        }

        System.out.println(Thread.currentThread() + " hello");

        countDownLatch.await();
    }

    @Test
    void threadTestUsingSingle() throws InterruptedException {
        //하나의 쓰레드로 처리
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            service.submit(new Task(countDownLatch));
        }

        System.out.println(Thread.currentThread() + " hello");

        service.shutdown();
        countDownLatch.await();
    }

    @Test
    void threadTestUsingFixed() throws InterruptedException {
        int numberOfCpu = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU 개수: " + numberOfCpu);

        //정해진 쓰레드 수만큼 사용
        //Blocking Queue 사용 (Concurrency에 안전)
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            service.submit(new Task(countDownLatch));
        }
        System.out.println(Thread.currentThread() + " hello");

        service.shutdown();
        countDownLatch.await();
    }

    @Test
    void threadTestUsingCache() throws InterruptedException {
        //현재 작업을 안하는 쓰레드가 있다면 그것을 사용하고, 없다면 새로 생성한다.
        //작업을 위한 공간이 하나 (쓰레드가 무한정 늘어날 위험 존재)
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            service.submit(new Task(countDownLatch));
        }

        System.out.println(Thread.currentThread() + " hello");

        service.shutdown();
        countDownLatch.await();
    }

    @Test
    void threadTestUsingScheduled() throws InterruptedException {
        //스케줄을 사용한 쓰레드 -> 스케줄을 감안하여 순서가 변경될 수 있다.
        //몇 초 뒤에 실행 또는 주기적으로 실행 등이 가능하다.
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 100; i++) {
            service.schedule(new Task(countDownLatch), 1, TimeUnit.SECONDS);
        }

        System.out.println(Thread.currentThread() + " hello");

        service.shutdown();
        countDownLatch.await();
    }

    @Test
    void threadTestUsingCallable() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        Future<String> submit = service.submit(new TaskUsingCallable());

        System.out.println(Thread.currentThread() + " hello");

        System.out.println(submit.get()); //Blocking Call (2초가 걸리는 구간)

        service.shutdown();
    }

    static class CustomThread extends Thread {
        private CountDownLatch countDownLatch;

        public CustomThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread() + " world");
            countDownLatch.countDown();
        }
    }

    static class Task implements Runnable {
        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread() + " world");
            countDownLatch.countDown();
        }
    }

    static class TaskUsingCallable implements Callable {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000L);
            return Thread.currentThread() + " world";
        }
    }

}
