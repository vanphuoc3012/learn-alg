package com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.log;

import java.util.concurrent.TimeUnit;

public class LogTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Start log task");

        try {
            while (!Thread.interrupted()) {
                TimeUnit.SECONDS.sleep(1);
                Logger.writeLogs();
            }
        } catch (InterruptedException e) {

        }
        Logger.writeLogs();
    }
}
