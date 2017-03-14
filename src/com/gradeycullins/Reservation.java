package com.gradeycullins;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Reservation {
    String login;
    int tid;
    int pid;
    Date to;
    Date from;
    float cost;

    public Reservation(String login, int tid, int pid) {
        this.login = login;
        this.tid = tid;
        this.pid = pid;
    }

    public boolean insert() {
        String insert = "INSERT INTO `5530db58`.`visit` (`login`, `tid`, `pid`, `to`, `from`, `cost`) " +
                "VALUES ('" + login + "', '" + tid + "', '" + pid + "', '" + to + "', '"+ from +"', '"+ cost +"');";

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
