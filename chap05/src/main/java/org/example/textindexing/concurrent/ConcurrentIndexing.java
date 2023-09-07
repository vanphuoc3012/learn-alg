package org.example.textindexing.concurrent;

import org.example.textindexing.common.Document;

import java.io.File;
import java.util.Date;
import java.util.concurrent.*;

public class ConcurrentIndexing {
    public static void main(String[] args) {
        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Math.max(1, numCores - 1));
        ExecutorCompletionService<Document> completionService = new ExecutorCompletionService<>(
                executor);
        ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> invertedIndex = new ConcurrentHashMap<>();

        Date start, end;

        File source = new File("data/textindexing/data");
        File[] files = source.listFiles();

        InvertedIndexTask invertedIndexTask1 = new InvertedIndexTask(completionService,
                                                                     invertedIndex);
        Thread thread1 = new Thread(invertedIndexTask1);
        InvertedIndexTask invertedIndexTask2 = new InvertedIndexTask(completionService,
                                                                     invertedIndex);
        Thread thread2 = new Thread(invertedIndexTask2);
        thread1.start();
        thread2.start();

        start = new Date();
        for (File file : files) {
            IndexingTask task = new IndexingTask(file);
            completionService.submit(task);
            if (executor.getQueue().size() > 1000) {
                do {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (executor.getQueue().size() > 1000);
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            thread1.interrupt();
            thread2.interrupt();
            thread1.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        end = new Date();

        System.out.println("Execution Time: " + (end.getTime() - start.getTime()));
        System.out.println("invertedIndex: " + invertedIndex.size());
    }
}
