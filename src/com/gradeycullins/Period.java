package com.gradeycullins;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by Gradey Cullins on 3/14/17.
 */
public class Period {
    protected int pid;
    protected int tid;
    protected SimpleDateFormat from;
    protected SimpleDateFormat to;
    protected int price;

    public Period(int pid, int tid, SimpleDateFormat from, SimpleDateFormat to, int price) {
        this.pid = pid;
        this.tid = tid;
        this.to = to;
        this.from = from;
        this.price = price;
    }

    public Period(int tid, SimpleDateFormat from, SimpleDateFormat to, int price) {
        this.tid = tid;
        this.from = from;
        this.to = to;
        this.price = price;
    }

    /**
     * Inserts a new period into the database
     * @return
     */
    public boolean insert(){
        String insert = "INSERT INTO `5530db58`.`period` (`tid`, `to`, `from`, `price`) " +
                "VALUES ('" + tid + "', '" + to + "', '" + from + "', '"+ price +"');";

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
