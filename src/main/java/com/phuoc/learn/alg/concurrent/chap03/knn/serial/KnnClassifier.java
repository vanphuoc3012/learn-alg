package com.phuoc.learn.alg.concurrent.chap03.knn.serial;

import com.phuoc.learn.alg.concurrent.chap03.knn.data.Distance;
import com.phuoc.learn.alg.concurrent.chap03.knn.data.Sample;
import com.phuoc.learn.alg.concurrent.chap03.knn.distances.EuclideanDistanceCalculator;

import java.util.*;

public class KnnClassifier {
    private final List<? extends Sample> dataSet;
    private final int k;

    public KnnClassifier(List<? extends Sample> dataSet, int k) {
        this.dataSet = dataSet;
        this.k = k;
    }

    public String classify(Sample example) {
        Distance[] distances = new Distance[dataSet.size()];
        int index = 0;

        for (Sample localSample : dataSet) {
            Distance dist = new Distance();
            dist.setIndex(index);
            dist.setDistance(EuclideanDistanceCalculator.calculate(localSample, example));
            distances[index++] = dist;
        }

        Arrays.sort(distances);

        Map<String, Integer> results = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Sample localExample = dataSet.get(distances[i].getIndex());
            String tag = localExample.getTag();
            results.merge(tag, 1, (a, b) -> a + b);
        }

        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
