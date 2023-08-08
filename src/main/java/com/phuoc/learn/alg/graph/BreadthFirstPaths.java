package com.phuoc.learn.alg.graph;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class BreadthFirstPaths {
    private final boolean[] marked;
    private final int[] edgeTo;
    private final int s;
    private final Graph graph;

    public BreadthFirstPaths(Graph G, int s) {
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.s = s;
        this.graph = G;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> fifo = new PriorityQueue<>();
        fifo.add(s);
        marked[s] = true;

        while(!fifo.isEmpty()) {
            int v = fifo.poll();
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!isMarked(w)) {
                    fifo.add(w);
                    edgeTo[w] = v;
                    marked[w] = true;
                }
            }
        }

    }

    public boolean isMarked(int v) {
        return marked[v];
    }

    public List<Integer> pathTo(int v) {
        List<Integer> pathTo = new ArrayList<>();
        if (hasPathTo(v)) {
            for (int x = v; x != s; x = edgeTo[x]) {
                pathTo.add(x);
            }
        }
        pathTo.add(s);
        return pathTo;
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public void printEdgeTo() {
        System.out.println("Edge to: " + Arrays.toString(edgeTo));
    }

    public static void main(String[] args) throws IOException {
        File tinyPG = Paths.get("tinyPG.txt").toFile();

        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(tinyPG)));

        Graph graph = new Graph(scanner);

        BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(graph, 0);

        breadthFirstPaths.printEdgeTo();
        System.out.println(breadthFirstPaths.pathTo(4));
    }
}
