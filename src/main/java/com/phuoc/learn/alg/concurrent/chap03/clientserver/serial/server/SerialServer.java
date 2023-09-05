package com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.server;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Command;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Constants;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.command.ErrorCommand;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.command.QueryCommand;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.command.ReportCommand;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.command.StopCommand;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    public static void main(String[] args) {
        WDIDAO dao = WDIDAO.getDAO();
        boolean stopServer = false;
        System.out.println("Initialization completed.");
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERIAL_PORT)) {
            do {
                try (Socket clientSocket = serverSocket.accept(); PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(),
                        true); BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))) {
                    String line = in.readLine();
                    Command command;
                    String[] commandData = line.split(";");
                    System.err.println("Command: " + commandData[0]);
                    switch (commandData[0]) {
                        case "q":
                            System.err.println("Query");
                            command = new QueryCommand(commandData);
                            break;
                        case "r":
                            System.err.println("Report");
                            command = new ReportCommand(commandData);
                            break;
                        case "z":
                            System.err.println("Stop");
                            command = new StopCommand(commandData);
                            stopServer = true;
                            break;
                        default:
                            System.err.println("Error");
                            command = new ErrorCommand(commandData);
                    }
                    String response = command.execute();
                    System.err.println(response);
                    out.println(response);
                }
            } while (!stopServer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
