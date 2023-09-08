package org.example.concurrent;

import org.example.common.AmazonMetaData;
import org.example.common.AmazonMetaDataLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

public class ConcurrentMergeSortMain {
    public static void main(String[] args) {
        Path path = Paths.get("data/amazon-meta.txt");
        Date start, end;
        for (int i = 0; i < 10; i++) {
            AmazonMetaData[] data = AmazonMetaDataLoader.load(path);
            AmazonMetaData[] data2 = data.clone();

            start = new Date();
            Arrays.parallelSort(data);
            end = new Date();
            System.out.println("Arrays.parallelSort: " + (end.getTime() - start.getTime()));

            ConcurrentMergeSort mergeSort = new ConcurrentMergeSort();
            start = new Date();
            mergeSort.mergeSort(data2);
            end = new Date();
            System.out.println("Concurrent merge sort: " + (end.getTime() - start.getTime()));

            for (int j = 0; j < data.length; j++) {
                if (data[j].compareTo(data2[j]) != 0) {
                    System.err.println("Wrong sort");
                    System.exit(-1);
                }
            }
            System.out.println("Both arrays are equal!!");
        }
    }
}
