package org.example.serial;

import org.example.common.AmazonMetaData;
import org.example.common.AmazonMetaDataLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

public class SerialMetaData {
    public static void main(String[] args) {
        Path path = Paths.get("data/amazon-meta.txt");
        Date start, end;
        SerialMergeSort mergeSorter = new SerialMergeSort();
        for (int i = 0; i < 10; i++) {
            AmazonMetaData[] data = AmazonMetaDataLoader.load(path);
            AmazonMetaData[] data2 = data.clone();

            start = new Date();
            Arrays.sort(data);
            end = new Date();
            System.out.println("Arrays.sort: " + (end.getTime() - start.getTime()) + " ms");

            start = new Date();
            mergeSorter.mergeSort(data2);
            end = new Date();
            System.out.println("Merge sort: " + (end.getTime() - start.getTime()) + " ms");

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
