package com.phuoc.learn.alg.concurrent.matrix.multiplication;

import com.phuoc.learn.alg.concurrent.matrix.multiplication.parallel.group.ParallelGroupMultiplier;
import com.phuoc.learn.alg.concurrent.matrix.multiplication.parallel.individual.ParallelIndividualMultiplier;
import com.phuoc.learn.alg.concurrent.matrix.multiplication.parallel.row.ParallelRowMultiplier;
import com.phuoc.learn.alg.concurrent.matrix.multiplication.serial.SerialMultiplier;
import com.phuoc.learn.alg.concurrent.matrix.multiplication.util.MatrixGenerator;

import java.util.Date;

public class MatrixApplication {
    public static void main(String[] args) {
        double[][] matrix1 = MatrixGenerator.generate(2000, 2000);
        double[][] matrix2 = MatrixGenerator.generate(2000, 2000);
        double[][] resultSerial = new double[matrix1.length][matrix2[0].length];
        double[][] resultIndividual = new double[matrix1.length][matrix2[0].length];
        double[][] resultRow = new double[matrix1.length][matrix2[0].length];
        double[][] resultGroup = new double[matrix1.length][matrix2[0].length];

        System.out.println("Start serial multiplier...");
        Date start = new Date();
        SerialMultiplier.multiply(matrix1, matrix2, resultSerial);
        Date end = new Date();
        System.out.printf("Serial: %d%n", end.getTime() - start.getTime());

        System.out.println("Start individual multiplier...");
        start = new Date();
        ParallelIndividualMultiplier.multiply(matrix1, matrix2, resultIndividual);
        end = new Date();
        System.out.printf("Individual: %d%n", end.getTime() - start.getTime());

        System.out.println("Start row multiplier...");
        start = new Date();
        ParallelRowMultiplier.multiply(matrix1, matrix2, resultRow);
        end = new Date();
        System.out.printf("Row: %d%n", end.getTime() - start.getTime());

        System.out.println("Start group multiplier...");
        start = new Date();
        ParallelGroupMultiplier.multiply(matrix1, matrix2, resultGroup);
        end = new Date();
        System.out.printf("Group: %d%n", end.getTime() - start.getTime());
    }
}
