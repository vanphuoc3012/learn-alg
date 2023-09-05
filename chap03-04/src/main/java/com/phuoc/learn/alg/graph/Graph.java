package com.phuoc.learn.alg.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
    private final int V;
    private int E;
    private List<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = new List[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public Graph(Scanner scanner) throws IOException {
        this(scanner.nextInt());
        int E = scanner.nextInt();
        for (int i = 0; i<E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            addEdge(v,w);
        }
    }

    public int V() {return V;}
    public int E(){return E;}

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w: G.adj(v)) degree++;
        return degree;
    }

    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        } return max;
    }

    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for(int w : G.adj(v)) if (v == w) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V).append(" vertices, ").append(E).append(" edges\n");

        for (int v = 0; v < V; v++) {
            sb.append(v).append(": ");
              for (int w: this.adj(v)) {
                  sb.append(" ")
                          .append(w)
                          .append(" ");
              }
            sb.append("\n");
        }

        return sb.toString();
    }
}
