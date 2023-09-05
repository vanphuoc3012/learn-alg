package com.phuoc.learn.alg.concurrent;

import java.util.ArrayList;
import java.util.List;

public class JavaConcurrent {
    private static int globalCounter = 0;
    private static final Object obj = new Object();

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Group 1");
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(threadGroup, new MyThread());
            t.start();
            threadList.add(t);
        }

        threadGroup.interrupt();

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Global counter: " + globalCounter);
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(99999);
            } catch (InterruptedException e) {
            }
            synchronized (obj) {
                globalCounter++;
            }
        }
    }
}
