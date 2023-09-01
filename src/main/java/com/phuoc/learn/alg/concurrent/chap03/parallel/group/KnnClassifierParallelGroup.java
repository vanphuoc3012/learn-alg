package com.phuoc.learn.alg.concurrent.chap03.parallel.group;

import com.phuoc.learn.alg.concurrent.chap03.data.Distance;
import com.phuoc.learn.alg.concurrent.chap03.data.Sample;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class KnnClassifierParallelGroup {
    private final List<? extends Sample> dataSet;
    private final int k;
    private final int numThreads;
    private final boolean parallelSort;
    private ThreadPoolExecutor executor;

    public KnnClassifierParallelGroup(List<? extends Sample> dataSet, int k, int factor,
                                      boolean parallelSort) {
        this.dataSet = dataSet;
        this.k = k;
        this.numThreads = factor * Runtime.getRuntime().availableProcessors();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        this.parallelSort = parallelSort;
    }

    public String classify(Sample example) throws Exception {

        Distance[] distances = new Distance[dataSet.size()];
        CountDownLatch endController = new CountDownLatch(numThreads);

        int length = dataSet.size() / numThreads;
        int startIndex = 0;
        int endIndex = length;

        for (int i = 0; i < numThreads; i++) {
            GroupDistanceTask task = new GroupDistanceTask(distances, startIndex, endIndex, dataSet,
                                                           example, endController);
            startIndex = endIndex;
            if (i < numThreads - 2) {
                endIndex = endIndex + length;
            } else {
                endIndex = dataSet.size();
            }
            executor.execute(task);
        }
        endController.await();

        if (parallelSort) {
            Arrays.parallelSort(distances);
        } else {
            Arrays.sort(distances);
        }

        Map<String, Integer> results = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Sample localExample = dataSet.get(distances[i].getIndex());
            String tag = localExample.getTag();
            results.merge(tag, 1, (a, b) -> a + b);
        }

        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void destroy() {
        executor.shutdown();
    }
}
