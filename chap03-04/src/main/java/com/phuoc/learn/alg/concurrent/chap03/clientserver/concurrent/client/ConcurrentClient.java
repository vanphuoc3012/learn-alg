package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.client;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDI;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentClient implements Runnable {
    private final String username;
    private final ThreadPoolExecutor executor;

    public ConcurrentClient(String username, ThreadPoolExecutor executor) {
        this.username = username;
        this.executor = executor;
    }

    @Override
    public void run() {
        WDIDAO dao = WDIDAO.getDAO();
        List<WDI> data = dao.getData();
        Date globalStart, globalEnd;
        Random randomGenerator = new Random();
        globalStart = new Date();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                QueryTask task = new QueryTask(data, username);
                executor.submit(task);
            }
            ReportTask task = new ReportTask(data, username);
            executor.submit(task);
        }

        globalEnd = new Date();
        System.out.println(
                "Total time: " + ((globalEnd.getTime() - globalStart.getTime()) / 1000) + " " + "seconds");
    }
}
