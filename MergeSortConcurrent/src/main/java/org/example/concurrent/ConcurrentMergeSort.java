package org.example.concurrent;

import java.util.concurrent.ForkJoinPool;

public class ConcurrentMergeSort {
    public void mergeSort(Comparable[] data) {
        MergeSortTask task = new MergeSortTask(data, 0, data.length, null);
        ForkJoinPool.commonPool().invoke(task);
    }
}
