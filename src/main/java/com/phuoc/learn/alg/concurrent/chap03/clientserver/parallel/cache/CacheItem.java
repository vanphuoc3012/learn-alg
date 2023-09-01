package com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.cache;

import java.util.Date;

public class CacheItem {
    private String command;
    private String response;
    private Date createDate;

    private Date accessDate;

    public CacheItem(String command, String response) {
        createDate = new Date();
        accessDate = new Date();
        this.command = command;
        this.response = response;
    }

    public String getCommand() {
        return command;
    }

    public String getResponse() {
        return response;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }
}
