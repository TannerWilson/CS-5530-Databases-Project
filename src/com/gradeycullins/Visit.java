package com.gradeycullins;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Visit {
    String login;
    int tid;
    int pid;
    Date to;
    Date from;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                "VALUES ('" + login + "', '" + tid + "', '" + pid + "', '" + sdf.format(to) + "', '"+ sdf.format(from) +"');";

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
