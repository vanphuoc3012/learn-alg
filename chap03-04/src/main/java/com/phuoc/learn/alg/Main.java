package com.phuoc.learn.alg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println(fr(9));
    }

    static int fr(int n) {
        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;

        return fr(n, arr);
    }

    static int fr(int n, int[] arr) {
        if (n < 2) return arr[n];

        if (arr[n] != 0) return arr[n];
        int v = fr(n - 1, arr) + fr(n - 2, arr);
        arr[n] = v;
        return v;
    }
}


