package org.example.concurrent;

import org.example.serial.SerialMergeSort;

import java.util.concurrent.CountedCompleter;

public class MergeSortTask extends CountedCompleter<Void> {
    private Comparable[] data;
    private int start;
    private int end;
    private int middle;

    public MergeSortTask(Comparable[] data, int start, int end, MergeSortTask parent) {
        super(parent);
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public void compute() {
        if (end - start >= 1024) {
            middle = (start - end) >>> 1;
            MergeSortTask task1 = new MergeSortTask(data, start, middle, this);
            MergeSortTask task2 = new MergeSortTask(data, middle, end, this);
            addToPendingCount(1);
            task1.fork();
            task2.fork();
        } else {
            new SerialMergeSort().mergeSort(data, start, end);
            tryComplete();
        }
    }

    @Override
    public void onCompletion(CountedCompleter<?> caller) {
        if (middle == 0) return;

        int length = end - start + 1;
        Comparable[] temp = new Comparable[length];
        int i, j, index;
        i = start;
        j = middle;
        index = 0;

        while (i < middle && j < end) {
            if (data[i].compareTo(data[j]) <= 0) {
                temp[index] = data[i];
                i++;
            } else {
                temp[index] = data[j];
                j++;
            }
            index++;
        }

        while (i < middle) {
            temp[index] = data[i];
            i++;
            index++;
        }

        while (j < end) {
            temp[index] = data[j];
            j++;
            index++;
        }

        for (index = 0; index < (end - start); index++) {
            data[index + start] = temp[index];
        }
    }
}
