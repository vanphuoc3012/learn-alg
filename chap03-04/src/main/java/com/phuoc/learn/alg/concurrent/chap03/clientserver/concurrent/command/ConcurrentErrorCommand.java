package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

import java.net.Socket;

public class ConcurrentErrorCommand extends ConcurrentCommand {
    public ConcurrentErrorCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();

        if (command.length == 3) {
            return dao.query(command[1], command[2]);
        } else if (command.length == 4) {
            try {
                return dao.query(command[1], command[2], Short.parseShort(command[3]));
            } catch (Exception e) {
                return "ERROR;Bad Command";
            }
        }
        return "ERROR;Bad Command";
    }
}
