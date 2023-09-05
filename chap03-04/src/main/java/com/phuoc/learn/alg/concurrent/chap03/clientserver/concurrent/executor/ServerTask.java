package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.executor;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command.ConcurrentCommand;

import java.util.concurrent.FutureTask;

public class ServerTask<V> extends FutureTask<V> implements Comparable<ServerTask<V>> {
    private ConcurrentCommand command;

    public ServerTask(ConcurrentCommand command) {
        super(command, null);
        this.command = command;
    }

    public ConcurrentCommand getCommand() {
        return command;
    }

    public void setCommand(ConcurrentCommand command) {
        this.command = command;
    }

    @Override
    public int compareTo(ServerTask<V> other) {
        return command.compareTo(other.getCommand());
    }
}
