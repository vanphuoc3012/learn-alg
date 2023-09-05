package org.example.main;

import org.example.reader.basic.NewsSystem;

import java.util.concurrent.TimeUnit;

public class BasicMain {
    public static void main(String[] args) {
        NewsSystem system = new NewsSystem("data/sources.txt");
        Thread t = new Thread(system);
        t.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.shutdown();
    }
}
