package org.example.reader.advanced;

import org.example.reader.basic.NewsTask;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
    private RunnableScheduledFuture<V> task;
    private NewsExecutor executor;
    private long startDate;
    private String name;

    public ExecutorTask(Runnable runnable, V result, RunnableScheduledFuture<V> task,
                        NewsExecutor executor) {
        super(runnable, result);
        this.task = task;
        this.executor = executor;
        this.name = ((NewsTask) runnable).getName();
        this.startDate = new Date().getTime();
    }

    @Override
    public boolean isPeriodic() {
        return task.isPeriodic();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delday;
        if (!isPeriodic()) {
            delday = task.getDelay(unit);
        } else {
            if (startDate == 0) {
                delday = task.getDelay(unit);
            } else {
                Date now = new Date();
                delday = startDate - now.getTime();
                delday = unit.convert(delday, TimeUnit.MILLISECONDS);
            }
        }
        return delday;
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getStartDate(), ((ExecutorTask<V>) o).getStartDate());
    }

    @Override
    public void run() {
        if (isPeriodic() && (!executor.isShutdown())) {
            super.runAndReset();
            Date now = new Date();
            startDate = now.getTime() + Timer.getPeriod();
            executor.getQueue().add(this);
            System.out.println("Start Date: " + new Date(startDate));
        }
    }

    public RunnableScheduledFuture<V> getTask() {
        return task;
    }

    public NewsExecutor getExecutor() {
        return executor;
    }

    public long getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
    }
}
