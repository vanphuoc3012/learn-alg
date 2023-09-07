package org.example.keywordextraction.concurrent;

import org.example.keywordextraction.common.Word;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Phaser;

public class ConcurrentKeywordExtraction {
    public static void main(String[] args) {
        Date start, end;
        ConcurrentHashMap<String, Word> globalVoc = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Integer> globalKeywords = new ConcurrentHashMap<>();

        start = new Date();
        File source = new File("data");
        File[] files = source.listFiles(f -> f.getName().endsWith(".txt"));

        if (files == null) {
            System.out.println("The data folder not found!!");
            return;
        }

        ConcurrentLinkedDeque<File> concurrentFileListPhase1 = new ConcurrentLinkedDeque<>(
                Arrays.asList(files));
        ConcurrentLinkedDeque<File> concurrentFileListPhase2 = new ConcurrentLinkedDeque<>(
                Arrays.asList(files));

        int numDocuments = files.length;

        int factor = 1;
        if (args.length > 0) {
            factor = Integer.parseInt(args[0]);
        }

        int numTasks = factor * Runtime.getRuntime().availableProcessors();

        Phaser phaser = new Phaser();

        Thread[] threads = new Thread[numTasks];
        KeywordExtractionTasks[] tasks = new KeywordExtractionTasks[numTasks];
        for (int i = 0; i < numTasks; i++) {
            tasks[i] = new KeywordExtractionTasks(globalVoc, globalKeywords,
                                                  concurrentFileListPhase1,
                                                  concurrentFileListPhase2, phaser, "Task " + i,
                                                  i == 0, numDocuments);
            phaser.register();
            System.out.println(phaser.getArrivedParties() + " tasks arrived to the Parser.");
        }

        for (int i = 0; i < numTasks; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < numTasks; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Is Terminated: " + phaser.isTerminated());
        end = new Date();
        System.out.println("Execution Time: " + (end.getTime() - start.getTime()));
        System.out.println("Vocabulary Size: " + globalVoc.size());
        System.out.println("Number of Documents: " + numDocuments);
    }
}
