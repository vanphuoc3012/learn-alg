package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.executor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorStatistics {
    private final AtomicLong executionTime = new AtomicLong(0);
    private final AtomicInteger numTasks = new AtomicInteger(0);

    public void addExecutionTime(long time) {
        executionTime.addAndGet(time);
    }

    public AtomicLong getExecutionTime() {
        return executionTime;
    }

    public AtomicInteger getNumTasks() {
        return numTasks;
    }

    public void addTasks() {
        numTasks.incrementAndGet();
    }

    @Override
    public String toString() {
        return "ExecutorStatistics{" + "executionTime=" + executionTime + ", numTasks=" + numTasks + '}';
    }
}
