package com.phuoc.learn.alg.concurrent.chap03.clientserver.serial.command;

import com.phuoc.learn.alg.concurrent.chap03.clientserver.common.Command;
import com.phuoc.learn.alg.concurrent.chap03.clientserver.wdi.data.WDIDAO;

public class QueryCommand extends Command {
    public QueryCommand(String[] commands) {
        super(commands);
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
                return "ERROR;; Bad command";
            }
        }
        return "ERROR; Bad command";
    }
}
