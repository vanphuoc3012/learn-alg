package org.example.textindexing.concurrent;

import org.example.textindexing.common.Document;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class MultipleConcurrentIndexing {
    public static void main(String[] args) throws IOException {
        Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 3, warmups = 1)
    public static void start() {
        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Math.max(1, numCores - 1));
        ExecutorCompletionService<List<Document>> completionService = new ExecutorCompletionService<>(
                executor);
        final int NUMBER_OF_DOCUMENT = 100;
        Date start, end;

        File source = new File("data/textindexing/data");
        File[] files = source.listFiles();

        ConcurrentHashMap<String, StringBuffer> invertedIndex = new ConcurrentHashMap<>();

        MultipleInvertedIndexTask invertedIndexTask1 = new MultipleInvertedIndexTask(
                completionService, invertedIndex);
        MultipleInvertedIndexTask invertedIndexTask2 = new MultipleInvertedIndexTask(
                completionService, invertedIndex);
        Thread thread1 = new Thread(invertedIndexTask1);
        Thread thread2 = new Thread(invertedIndexTask2);
        thread1.start();
        thread2.start();

        start = new Date();
        List<File> taskFiles = new ArrayList<>();
        for (File file : files) {
            taskFiles.add(file);
            if (taskFiles.size() == NUMBER_OF_DOCUMENT) {
                MultipleIndexingTask task = new MultipleIndexingTask(taskFiles);
                completionService.submit(task);
                taskFiles = new ArrayList<>();
            }

            if (executor.getQueue().size() > 10) {
                do {
                    try {
                        {
                            TimeUnit.MILLISECONDS.sleep(50);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (executor.getQueue().size() > 10);
            }
        }
        if (taskFiles.size() > 0) {
            MultipleIndexingTask task = new MultipleIndexingTask(taskFiles);
            completionService.submit(task);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            thread1.interrupt();
            thread2.interrupt();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = new Date();
        System.out.println("Execution Time: " + (end.getTime() - start.getTime()));
        System.out.println("invertedIndex: " + invertedIndex.size());
        System.out.println(invertedIndex.get("book").length());
    }
}
