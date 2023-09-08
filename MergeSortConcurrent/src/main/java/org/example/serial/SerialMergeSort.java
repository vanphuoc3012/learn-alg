package org.example.serial;

import org.example.common.AmazonMetaData;

import java.util.Random;

public class SerialMergeSort {

    public static void main(String[] args) {
        Random random = new Random();
        AmazonMetaData[] data = new AmazonMetaData[10];
        for (int i = 0; i < 10; i++) {
            data[i] = new AmazonMetaData();
            data[i].setSalesrank(random.nextLong());
        }

        SerialMergeSort mergeSort = new SerialMergeSort();
        mergeSort.mergeSort(data);
    }

    public void mergeSort(Comparable[] data) {
        mergeSort(data, 0, data.length);
    }

    public void mergeSort(Comparable[] data, int start, int end) {
        if (end - start < 2) return;

        int middle = (start + end) >>> 1;
        mergeSort(data, start, middle);
        mergeSort(data, middle, end);
        merge(data, start, middle, end);
    }

    private void merge(Comparable[] data, int start, int middle, int end) {
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
