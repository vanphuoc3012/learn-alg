package com.phuoc.learn.alg.concurrent.matrix.multiplication.parallel.individual;

import java.util.ArrayList;
import java.util.List;

public class ParallelIndividualMultiplier {

    public static void multiply(double[][] matrix1, double[][] matrix2, double[][] result) {
        List<Thread> threadList = new ArrayList<>();
        int rows1 = matrix1.length;
        int columns1 = matrix1[0].length;

        int rows2 = matrix2.length;
        int columns2 = matrix2[0].length;

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns2; j++) {
                IndividualMultiplierTask task = new IndividualMultiplierTask(result, matrix1,
                                                                             matrix2, i, j);
                Thread thread = new Thread(task);
                thread.start();
                threadList.add(thread);

                if (threadList.size() % 10 == 0) {
                    waitForThreads(threadList);
                }
            }
        }
    }

    private static void waitForThreads(List<Thread> threadList) {
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        threadList.clear();
    }
}
