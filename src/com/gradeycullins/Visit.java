package com.gradeycullins;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Visit {
    String login;
    int tid;
    int pid;
    SimpleDateFormat to;
    SimpleDateFormat from;

    public Visit(String login, int tid, int pid) {
        this.login = login;
        this.tid = tid;
        this.pid = pid;
    }

    /**
     * Inserts this visit into the database
     * @return success of insert
     */
    public boolean insert() {
        String insert = "INSERT INTO `5530db58`.`visit` (`login`, `tid`, `pid`, `to`, `from`) " +
                "VALUES ('" + login + "', '" + tid + "', '" + pid + "', '" + to + "', '"+ from +"');";

        try {
            Connector.getInstance().statement.execute(insert);
            return true;
        } catch (SQLException e) {
            System.out.println("Insert failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

}
