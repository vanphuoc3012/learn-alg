package com.phuoc.learn.alg.concurrent.matrix.multiplication.parallel.group;

import java.util.ArrayList;
import java.util.List;

public class ParallelGroupMultiplier {
    public static void multiply(double[][] matrix1, double[][] matrix2, double[][] result) {
        List<Thread> threadList = new ArrayList<>();

        int rows1 = matrix1.length;

        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Available processors: " + numThreads);

        int startIndex, endIndex, step;

        step = rows1 / numThreads;
        startIndex = 0;
        endIndex = step;

        for (int i = 0; i < numThreads; i++) {
            GroupMultiplierTask task = new GroupMultiplierTask(result, matrix1, matrix2, startIndex,
                                                               endIndex);
            Thread thread = new Thread(task);
            thread.start();
            threadList.add(thread);
            startIndex = endIndex;
            endIndex = i == numThreads - 2 ? rows1 : endIndex + step;
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
