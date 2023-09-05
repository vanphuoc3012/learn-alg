package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.server;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command.*;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.executor.ServerExecutor;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.executor.ServerTask;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.cache.ParallelCache;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.parallel.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RequestTask implements Runnable {
    private final LinkedBlockingQueue<Socket> pendingConnections;
    private final ServerExecutor executor = new ServerExecutor();
    private final ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController;

    public RequestTask(LinkedBlockingQueue<Socket> pendingConnections,
                       ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController) {
        this.pendingConnections = pendingConnections;
        this.taskController = taskController;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Socket clientSocket = pendingConnections.take();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();

                Logger.sendMessage(line);
                ConcurrentCommand command;

                ParallelCache cache = ConcurrentServer.getCache();
                String ret = cache.get(line);
                if (ret == null) {
                    String[] commandData = line.split(";");
                    System.out.println("Command: " + commandData[0]);
                    switch (commandData[0]) {
                        case "q":
                            System.err.println("Query");
                            command = new ConcurrentQueryCommand(clientSocket, commandData);
                            break;
                        case "r":
                            System.err.println("Report");
                            command = new ConcurrentReportCommand(clientSocket, commandData);
                            break;
                        case "s":
                            System.err.println("Status");
                            command = new ConcurrentStatusCommand(executor, clientSocket,
                                                                  commandData);
                            break;
                        case "z":
                            System.err.println("Stop");
                            command = new ConcurrentStopCommand(clientSocket, commandData);
                            break;
                        default:
                            System.err.println("Error");
                            command = new ConcurrentErrorCommand(clientSocket, commandData);
                            break;
                    }
                    ServerTask<?> controller = (ServerTask<?>) executor.submit(command);
                    storeController(command.getUsername(), controller, command);
                } else {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println(ret);
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {

            }
        }
    }

    private void storeController(String username, ServerTask<?> controller,
                                 ConcurrentCommand command) {
        taskController.computeIfAbsent(username,
                                       k -> (ConcurrentMap<ConcurrentCommand, ServerTask<?>>) new ConcurrentHashMap<>().put(
                                               command, controller));
    }

    public void shutdown() {
        String message = "Request Task: " + pendingConnections.size() + " pending connections.";
        Logger.sendMessage(message);
        executor.shutdown();
    }

    public void terminate() {
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            executor.writeStatistics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
