package com.phuoc.learn.alg.concurrent.chap03.clientserver.common;

public class StopCommand extends Command {
    public StopCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        return "Server stopped";
    }
}
