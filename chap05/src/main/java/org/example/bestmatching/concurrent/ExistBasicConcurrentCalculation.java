package org.example.bestmatching.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExistBasicConcurrentCalculation {
    public static boolean existWord(String word,
                                    List<String> dictionary) throws ExecutionException, InterruptedException {
        int coreNums = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreNums);

        int dictSize = dictionary.size();
        int step = dictSize / coreNums;
        int startIndex, endIndex;

        List<ExistBasicTask> tasks = new ArrayList<>();

        for (int i = 0; i < coreNums; i++) {
            startIndex = i * step;
            if (i == coreNums - 1) {
                endIndex = dictionary.size();
            } else {
                endIndex = (i + 1) * step;
            }
            ExistBasicTask task = new ExistBasicTask(startIndex, endIndex, dictionary, word);
            tasks.add(task);
        }

        try {
            return executor.invokeAny(tasks);
        } catch (ExecutionException | InterruptedException e) {
            if (e.getCause() instanceof NoSuchElementException) return false;
            throw e;
        } finally {
            executor.shutdown();
        }
    }
}
