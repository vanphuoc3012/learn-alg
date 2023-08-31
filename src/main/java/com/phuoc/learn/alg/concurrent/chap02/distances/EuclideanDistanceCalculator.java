package com.phuoc.learn.alg.concurrent.chap02.distances;

import com.phuoc.learn.alg.concurrent.chap02.data.Sample;

public class EuclideanDistanceCalculator {
    /**
     * @param ex1
     * @param ex2
     * @return The Euclidean distance between the two examples
     */
    public static double calculate(Sample ex1, Sample ex2) {
        double ret = 0.0d;

        double[] data1 = ex1.getExample();
        double[] data2 = ex2.getExample();

        if (data1.length != data2.length) {
            throw new IllegalArgumentException("Vector doesn't have the same length");
        }

        for (int i = 0; i < data1.length; i++) {
            ret += Math.pow(data1[i] - data2[i], 2);
        }
        return Math.sqrt(ret);
    }
}
