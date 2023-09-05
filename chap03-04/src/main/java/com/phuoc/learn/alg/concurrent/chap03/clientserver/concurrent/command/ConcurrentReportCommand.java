package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

import java.net.Socket;

public class ConcurrentReportCommand extends ConcurrentCommand {
    public ConcurrentReportCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[3]);
    }
}
