package com.phuoc.learn.alg.graph;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.*;

public class DeepFirstPaths {
    private final boolean[] markedStack;
    private final int[] edgeTo;
    private int countStack;
    private int s;

    public DeepFirstPaths(Graph G, int s) {
        markedStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfsStack(G,s);
    }

    private void dfsStack(Graph G, int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        markedStack[v] = true;

        while (!stack.isEmpty()) {
            int k = stack.pop();
            markedStack[k] = true;
            for (int w : G.adj(k)) {
                if (!marked(w)) {
                    stack.push(w);
                    edgeTo[w] = k;
                }
            }
        }
    }

    public boolean marked(int w) {
        return markedStack[w];
    }

    public int countStack() {
        return countStack;
    }

    public void printMarked() {
        System.out.println("Mark stack: " + Arrays.toString(markedStack));
    }

    public void printEdgeTo() {
        System.out.println("Edge to: " + Arrays.toString(edgeTo));
    }

    public boolean hasPathTo(int v) {
        return markedStack[v];
    }

    public List<Integer> pathTo(int v) {
        if (hasPathTo(v)) {
            List<Integer> paths = new ArrayList<>();
            for (int x = v; x !=s; x = edgeTo[x]) {
                paths.add(x);
            }
            paths.add(s);
            return paths;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        File tinyPG = Paths.get("tinyPG.txt").toFile();

        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(tinyPG)));

        Graph graph = new Graph(scanner);

        DeepFirstPaths deepFirstPaths = new DeepFirstPaths(graph, 0);

        deepFirstPaths.printMarked();
        deepFirstPaths.printEdgeTo();
        System.out.println(deepFirstPaths.pathTo(4));
    }
}
