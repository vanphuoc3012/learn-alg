package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Command;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server.ConcurrentServer;

public class ConcurrentStopCommand extends Command {
    public ConcurrentStopCommand(String[] command) {
        super(command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        ConcurrentServer.shutdown();
        return "Server stopped";
    }
}
