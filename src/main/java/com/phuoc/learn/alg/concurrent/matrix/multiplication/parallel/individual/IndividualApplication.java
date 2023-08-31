package com.phuoc.learn.alg.concurrent.matrix.multiplication.parallel.individual;

import com.phuoc.learn.alg.concurrent.matrix.multiplication.util.MatrixGenerator;

import java.util.Date;

public class IndividualApplication {
    public static void main(String[] args) {
        double[][] matrix1 = MatrixGenerator.generate(2000, 2000);
        double[][] matrix2 = MatrixGenerator.generate(2000, 2000);

        double[][] resultSerial = new double[matrix1.length][matrix2[0].length];

        Date start = new Date();

        ParallelIndividualMultiplier.multiply(matrix1, matrix2, resultSerial);

        Date end = new Date();

        System.out.printf("Serial: %d%n", end.getTime() - start.getTime());
    }
}
