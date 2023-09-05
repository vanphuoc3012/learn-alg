package com.phuoc.learn.alg.concurrent.chap03.clientserver.common;

public abstract class Command {
    protected final String[] command;
    private boolean cacheable;

    public Command(String[] command) {
        this.command = command;
        cacheable = true;
    }

    /**
     * Excute the command
     *
     * @return A string with the response of the command
     */
    public abstract String execute();

    public String[] getCommand() {
        return command;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }
}