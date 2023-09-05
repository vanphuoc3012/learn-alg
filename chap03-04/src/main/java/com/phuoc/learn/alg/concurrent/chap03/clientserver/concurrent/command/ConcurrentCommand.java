package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Command;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server.ConcurrentServer;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.cache.ParallelCache;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.log.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ConcurrentCommand extends Command implements Comparable<ConcurrentCommand>, Runnable {
    private final Socket socket;
    private final String username;
    private final byte priority;

    public ConcurrentCommand(Socket socket, String[] command) {
        super(command);
        this.socket = socket;
        this.username = command[1];
        priority = Byte.parseByte(command[2]);
    }

    public byte getPriority() {
        return priority;
    }

    @Override
    public abstract String execute();

    @Override
    public int compareTo(ConcurrentCommand o) {
        return Byte.compare(o.getPriority(), this.getPriority());
    }

    @Override
    public void run() {
        String message = "Running a Task: Useraname: " + username + "; Priority: " + priority;
        Logger.sendMessage(message);
        String ret = execute();
        ParallelCache cache = ConcurrentServer.getCache();
        if (isCacheable()) {
            cache.put(String.join(";", command), ret);
        }

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }
}
