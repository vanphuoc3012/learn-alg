package com.phuoc.learn.alg.concurrent.matrix.multiplication.util;

import java.util.Random;

public class MatrixGenerator {

    public static double[][] generate(int rows, int columns) {
        double[][] ret = new double[rows][columns];
        Random rd = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                ret[i][j] = rd.nextDouble() * 10;
            }
        }
        return ret;
    }
}
