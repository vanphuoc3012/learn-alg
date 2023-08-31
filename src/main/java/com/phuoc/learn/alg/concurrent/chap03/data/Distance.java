package com.phuoc.learn.alg.concurrent.chap03.data;

public class Distance implements Comparable<Distance> {
    private int index;
    private double distance;

    @Override
    public int compareTo(Distance o) {
        if (this.distance < o.getDistance()) {
            return -1;
        }
        if (this.distance > o.getDistance()) {
            return 1;
        }
        return 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
