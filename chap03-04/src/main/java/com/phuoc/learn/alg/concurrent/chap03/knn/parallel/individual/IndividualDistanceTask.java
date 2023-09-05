package com.phuoc.learn.alg.concurrent.chap03.knn.parallel.individual;

import com.phuoc.learn.alg.concurrent.chap03.knn.data.Distance;
import com.phuoc.learn.alg.concurrent.chap03.knn.data.Sample;
import com.phuoc.learn.alg.concurrent.chap03.knn.distances.EuclideanDistanceCalculator;

import java.util.concurrent.CountDownLatch;

public class IndividualDistanceTask implements Runnable {

    private final Distance[] distances;
    private final int index;
    private final Sample localExample;
    private final Sample example;
    private final CountDownLatch endController;

    public IndividualDistanceTask(Distance[] distances, int index, Sample localExample,
                                  Sample example, CountDownLatch endController) {
        this.distances = distances;
        this.index = index;
        this.localExample = localExample;
        this.example = example;
        this.endController = endController;
    }

    @Override
    public void run() {
        Distance dist = new Distance();
        dist.setIndex(index);
        dist.setDistance(EuclideanDistanceCalculator.calculate(localExample, example));
        distances[index] = dist;
        endController.countDown();
    }
}
