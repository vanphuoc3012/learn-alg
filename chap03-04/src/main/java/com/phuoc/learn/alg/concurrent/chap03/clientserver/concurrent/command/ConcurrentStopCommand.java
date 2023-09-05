package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server.ConcurrentServer;

import java.net.Socket;

public class ConcurrentStopCommand extends ConcurrentCommand {
    public ConcurrentStopCommand(Socket socket, String[] command) {
        super(socket, command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        ConcurrentServer.shutdown();
        return "Server stopped";
    }
}
