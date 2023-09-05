package com.phuoc.learn.alg.concurrent.chap03.clientserver.concurrent.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Command;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

public class ConcurrentReportCommand extends Command {
    public ConcurrentReportCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[1]);
    }
}
