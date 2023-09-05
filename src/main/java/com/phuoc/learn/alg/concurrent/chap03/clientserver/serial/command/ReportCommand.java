package com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Command;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

public class ReportCommand extends Command {

    public ReportCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[1]);
    }
}
