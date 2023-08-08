package com.phuoc.learn.alg.concurrent;

public class JavaConcurrent {
//    public static void main(String[] args) throws InterruptedException {
//        Thread th1 = new Thread(() -> {
//            Thread currentThread = Thread.currentThread();
//            System.out.println(currentThread.getName() + " priority = " + currentThread.getPriority());
//        });
//
//        Thread th2 = new Thread(() -> {
//            Thread currentThread = Thread.currentThread();
//            System.out.println(currentThread.getName() + " priority = " + currentThread.getPriority());
//        });
//
//        th1.setPriority(Thread.MAX_PRIORITY);
//        th2.setPriority(Thread.MIN_PRIORITY);
//
//        th1.start();
//        th2.start();
//
//        th1.join();
//        th2.join();
//    }

    public static void main(String[] args) throws InterruptedException {
        Thread th1 = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + " priority = " + currentThread.getPriority() + " state = " + currentThread.getState());
        });

        th1.start();
        th1.join();

        System.out.println("*2* State: " + Thread.currentThread().getState());
    }
}
