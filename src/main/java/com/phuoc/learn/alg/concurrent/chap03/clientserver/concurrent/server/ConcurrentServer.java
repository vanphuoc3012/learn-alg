package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Constants;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.cache.ParallelCache;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.log.Logger;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConcurrentServer {

    private static ThreadPoolExecutor executor;
    private static ParallelCache cache;
    private static ServerSocket serverSocket;
    private static volatile boolean stopped = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        WDIDAO dao = WDIDAO.getDAO();

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
        cache = new ParallelCache();
        Logger.initializeLog();

        System.out.println("Initialization completed: ");

        serverSocket = new ServerSocket(Constants.CONCURRENT_PORT);

        do {
            try {
                Socket clientSocket = serverSocket.accept();
                RequestTask task = new RequestTask(clientSocket);
                executor.execute(task);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!stopped);

        executor.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Shutting down cache");
        cache.shutdown();
        System.out.println("Cache ok");
        System.out.println("Main server thread ended");
    }

    public static void shutdown() {
        stopped = true;
        System.out.println("Shutting down the server...");
        System.out.println("Shutting down executor");
        executor.shutdown();
        System.out.println("Executor ok");
        System.out.println("Closing socket");

        try {
            serverSocket.close();
            System.out.println("Socket ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Shutting down logger");
        Logger.sendMessage("Shutting down the logger");
        Logger.shutdown();
        System.out.println("Logger ok");
    }

    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public static ParallelCache getCache() {
        return cache;
    }
}
