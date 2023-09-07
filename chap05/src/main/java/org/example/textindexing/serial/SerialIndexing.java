package org.example.textindexing.serial;

import org.example.textindexing.common.DocumentParser;

import java.io.File;
import java.util.*;

public class SerialIndexing {
    public static void main(String[] args) {
        Date start, end;
        File source = new File("data/textindexing/data");
        File[] files = source.listFiles();
        Map<String, List<String>> invertedIndex = new HashMap<>();

        start = new Date();
        for (File f : files) {
            DocumentParser parser = new DocumentParser();
            if (f.getName().endsWith(".txt")) {
                Map<String, Integer> voc = parser.parse(f.getAbsolutePath());
                updateInvertedIndex(voc, invertedIndex, f.getName());
            }
        }
        end = new Date();
        System.out.println("Execution Time: " + (end.getTime() - start.getTime()) + " ms");
        System.out.println("invertedIndex: " + invertedIndex.size());
    }

    private static void updateInvertedIndex(Map<String, Integer> voc,
                                            Map<String, List<String>> invertedIndex,
                                            String fileName) {
        for (String word : voc.keySet()) {
            if (word.length() >= 3) {
                invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(fileName);
            }
        }
    }
}
