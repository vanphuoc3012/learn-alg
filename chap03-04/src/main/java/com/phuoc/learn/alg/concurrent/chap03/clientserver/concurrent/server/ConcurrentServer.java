package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Constants;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command.ConcurrentCancelCommand;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command.ConcurrentCommand;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.executor.ServerTask;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.cache.ParallelCache;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.log.Logger;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentServer {
    private static ParallelCache cache;
    private static volatile boolean stopped = false;
    private static LinkedBlockingQueue<Socket> pendingConnections;
    private static ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController;
    private static Thread requestThread;
    private static RequestTask requestTask;

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException, InterruptedException {
        WDIDAO dao = WDIDAO.getDAO();
        cache = new ParallelCache();
        Logger.initializeLog();
        pendingConnections = new LinkedBlockingQueue<>();
        taskController = new ConcurrentHashMap<>();
        requestTask = new RequestTask(pendingConnections, taskController);
        requestThread = new Thread(requestTask);
        requestThread.start();

        System.out.println("Initialization completed.");

        serverSocket = new ServerSocket(Constants.CONCURRENT_PORT);
        do {
            try {
                Socket clientSocket = serverSocket.accept();
                pendingConnections.put(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!stopped);
        finishServer();
        System.out.println("Shutting down cache");
        cache.shutdown();
        System.out.println("Cache ok!!" + new Date());
    }

    private static void finishServer() {
        System.out.println("Shutting down the server...");
        requestTask.shutdown();
        requestTask.terminate();
        System.out.println("Shutting down Request task");
        requestThread.interrupt();
        System.out.println("Request task ok");
        System.out.println("Closing socket");
        System.out.println("Shutting down logge");
        Logger.sendMessage("Shutting down the logger");
        Logger.shutdown();
        System.out.println("Logger ok");
        System.out.println("Main server thread ended");
    }

    public static void shutdown() {
        stopped = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ParallelCache getCache() {
        return cache;
    }

    public static void finishTask(String username, ConcurrentCommand command) {
        ConcurrentMap<ConcurrentCommand, ServerTask<?>> userTasks = taskController.get(username);
        userTasks.remove(command);
        String message = "Task with code " + command.hashCode() + " has finished";
        Logger.sendMessage(message);
    }

    public static void cancelTasks(String username) {
        ConcurrentMap<ConcurrentCommand, ServerTask<?>> userTasks = taskController.get(username);
        if (userTasks == null) {
            return;
        }

        int taskNumber = 0;
        Iterator<ServerTask<?>> it = userTasks.values().iterator();
        while (it.hasNext()) {
            ServerTask<?> task = it.next();
            ConcurrentCommand command = task.getCommand();
            if (!(command instanceof ConcurrentCancelCommand) && task.cancel(true)) {
                taskNumber++;
                Logger.sendMessage(
                        "Task with code " + command.hashCode() + " cancelled: " + command.getClass()
                                                                                         .getSimpleName());
                it.remove();
            }
        }
        String message = taskNumber + " task has been cancelled";
        Logger.sendMessage(message);
    }
}
