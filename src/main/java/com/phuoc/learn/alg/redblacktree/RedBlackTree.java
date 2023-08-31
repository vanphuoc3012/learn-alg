package com.phuoc.learn.alg.redblacktree;

public class RedBlackTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value val;
        Node left;
        Node right;
        int N;
        boolean color;
    }

    private boolean isRed(Node x) {
        if (x == null) return BLACK;
        return x.color;
    }
}
