package com.phuoc.learn.alg.concurrent.chap03.knn.parallel.individual;

import com.phuoc.learn.alg.concurrent.chap03.knn.data.Distance;
import com.phuoc.learn.alg.concurrent.chap03.knn.data.Sample;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class KnnClassifierParallelIndividual {
    private final List<? extends Sample> dataSet;
    private final int k;
    private final ThreadPoolExecutor executor;
    private final int numThreads;
    private final boolean parallelSort;

    public KnnClassifierParallelIndividual(List<? extends Sample> dataSet, int k, int factors,
                                           boolean parallelSort) {
        this.dataSet = dataSet;
        this.k = k;
        this.numThreads = factors * Runtime.getRuntime().availableProcessors();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        this.parallelSort = parallelSort;
    }

    public String classify(Sample example) throws InterruptedException {
        Distance[] distances = new Distance[dataSet.size()];
        CountDownLatch endController = new CountDownLatch(dataSet.size());

        int index = 0;
        for (Sample localExample : dataSet) {
            IndividualDistanceTask task = new IndividualDistanceTask(distances, index, localExample,
                                                                     example, endController);
            executor.execute(task);
            index++;
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
