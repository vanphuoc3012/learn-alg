package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server.ConcurrentServer;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.log.Logger;

import java.net.Socket;

public class ConcurrentCancelCommand extends ConcurrentCommand {
    public ConcurrentCancelCommand(Socket socket, String[] command) {
        super(socket, command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        ConcurrentServer.cancelTasks(getUsername());
        String message = "Tasks of user " + getUsername() + " has been cancelled.";
        Logger.sendMessage(message);
        return message;
    }
}
