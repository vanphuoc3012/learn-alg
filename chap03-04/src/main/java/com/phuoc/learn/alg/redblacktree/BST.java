package com.phuoc.learn.alg.redblacktree;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int comp = key.compareTo(x.key);
        if (comp < 0) return get(x.left, key);
        if (comp > 0) return get(x.right, key);
        return x.val;
    }

    public void put(Key k, Value v) {
        root = put(root, k, v);
    }

    private Node put(Node x, Key k, Value v) {
        if (x == null) return new Node(k, v, 1);
        int comp = k.compareTo(x.key);
        if (comp < 0) {
            x.left = put(x.left, k, v);
        } else if (comp > 0) {
            x.right = put(x.right, k, v);
        } else {
            x.val = v;
        }

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    private class Node {
        private final Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }
}
