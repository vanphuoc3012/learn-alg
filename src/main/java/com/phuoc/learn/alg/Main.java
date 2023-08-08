package com.phuoc.learn.alg;

import com.phuoc.learn.alg.graph.Graph;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        File tinyG = Paths.get("tinyG.txt").toFile();
        File mediumG = Paths.get("mediumG.txt").toFile();
        File largeG = Paths.get("largeG.txt").toFile();
//        FileReader fileReader = new FileReader(tinyG);
//        BufferedReader br = new BufferedReader(fileReader);
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }

        InputStream is = new FileInputStream(largeG);
        Scanner scanner = new Scanner(new BufferedInputStream(is));

        Graph graph = new Graph(scanner);
        System.out.println(graph);

    }
}


