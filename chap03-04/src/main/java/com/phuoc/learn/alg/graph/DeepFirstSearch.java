package com.phuoc.learn.alg.graph;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class DeepFirstSearch {
    private boolean[] markedRecursive;
    private boolean[] markedStack;
    private int countRecursive;
    private int countStack;

    public DeepFirstSearch(Graph G, int s) {
        markedRecursive = new boolean[G.V()];
        markedStack = new boolean[G.V()];
        dfsRecursive(G, s);
        dfsStack(G,s);
    }

    private void dfsRecursive(Graph G, int v) {
        markedRecursive[v] = true;
        countRecursive++;
        for (int w : G.adj(v)) {
            if (!markedRecursive[w]) dfsRecursive(G, w);
        }
    }

    private void dfsStack(Graph G, int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        markedStack[v] = true;

        while (!stack.isEmpty()) {
            int k = stack.pop();
            countStack++;
            for (int w : G.adj(k)) {
                if (!markedStack[w]) {
                    markedStack[w] = true;
                    stack.push(w);
                }
            }
        }
    }

    public boolean marked(int w) {
        return markedRecursive[w];
    }

    public int countRecursive() {
        return countRecursive;
    }

    public int countStack() {
        return countStack;
    }

    public void printMarked() {
        System.out.println("Mark recursive: " + Arrays.toString(markedRecursive));
        System.out.println("Mark stack: " + Arrays.toString(markedStack));
    }

    public static void main(String[] args) throws IOException {
        File tinyG = Paths.get("tinyCG.txt").toFile();

        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(tinyG)));

        Graph graph = new Graph(scanner);
        DeepFirstSearch deepFirstSearch = new DeepFirstSearch(graph, 4);

        System.out.println("Vertices connected to 4: " + deepFirstSearch.countRecursive());
        System.out.println("Vertices connected to 4: " + deepFirstSearch.countRecursive());

        deepFirstSearch.printMarked();
    }
}
