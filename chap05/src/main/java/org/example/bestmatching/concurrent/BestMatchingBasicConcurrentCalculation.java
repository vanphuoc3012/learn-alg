package org.example.bestmatching.concurrent;

import org.example.bestmatching.common.BestMatchingData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BestMatchingBasicConcurrentCalculation {
    private BestMatchingBasicConcurrentCalculation() {
    }

    public static BestMatchingData getBestMatchingWords(String word,
                                                        List<String> dictionary) throws ExecutionException, InterruptedException {
        int coreNums = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreNums);

        int dictSize = dictionary.size();
        int step = dictSize / coreNums;
        int startIndex, endIndex;
        List<Future<BestMatchingData>> futuresResult = new ArrayList<>();

        for (int i = 0; i < coreNums; i++) {
            startIndex = i * step;
            if (i == coreNums - 1) {
                endIndex = dictSize;
            } else {
                endIndex = (i + 1) * step;
            }

            BestMatchingBasicTask task = new BestMatchingBasicTask(startIndex, endIndex, dictionary,
                                                                   word);
            Future<BestMatchingData> future = executor.submit(task);
            futuresResult.add(future);
        }
        executor.shutdown();

        List<String> words = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;
        for (Future<BestMatchingData> f : futuresResult) {
            BestMatchingData data = f.get();

            if (data.getDistance() < minDistance) {
                words.clear();
                minDistance = data.getDistance();
                words.addAll(data.getWords());
            } else if (data.getDistance() == minDistance) {
                words.addAll(data.getWords());
            }
        }
        BestMatchingData ret = new BestMatchingData();
        ret.setWords(words);
        ret.setDistance(minDistance);

        return ret;
    }
}
