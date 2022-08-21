package me.jsj.thejava.eighttoeleven.completablefuture.concurrent;

public class App {
//    public static void main(String[] args) throws InterruptedException {
/*
        //방법 1
        MyThread myThread = new MyThread();
        myThread.start();
*/

        //방법 2
//        Thread thread = new Thread(() -> {
//            System.out.println("Thread by runnable : " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
//        });
//        thread.start();
//
//        System.out.println("Hello : " + Thread.currentThread().getName());
//        thread.join();
//        System.out.println(thread + " is finished");
//    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread by thread : " + Thread.currentThread().getName());
        }
    }
}

