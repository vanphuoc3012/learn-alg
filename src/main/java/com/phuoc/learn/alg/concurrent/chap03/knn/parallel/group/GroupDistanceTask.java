package com.phuoc.learn.alg.concurrent.chap03.knn.parallel.group;

import com.phuoc.learn.alg.concurrent.chap03.knn.data.Distance;
import com.phuoc.learn.alg.concurrent.chap03.knn.data.Sample;
import com.phuoc.learn.alg.concurrent.chap03.knn.distances.EuclideanDistanceCalculator;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GroupDistanceTask implements Runnable {
    private final Distance[] distances;
    private final int startIndex, endIndex;
    private final Sample example;
    private final List<? extends Sample> dataset;
    private final CountDownLatch endController;

    public GroupDistanceTask(Distance[] distances, int startIndex, int endIndex,

                             List<? extends Sample> dataset, Sample example,
                             CountDownLatch endController) {
        this.distances = distances;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.example = example;
        this.dataset = dataset;
        this.endController = endController;
    }

    @Override
    public void run() {
        for (int index = startIndex; index < endIndex; index++) {
            Sample localExample = dataset.get(index);
            Distance dist = new Distance();
            dist.setIndex(index);
            dist.setDistance(EuclideanDistanceCalculator.calculate(localExample, example));

            distances[index] = dist;
        }
        endController.countDown();
    }
}
