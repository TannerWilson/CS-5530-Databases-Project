package com.gradeycullins;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gradey Cullins on 3/14/17.
 */
public class Period {
    protected int pid;
    protected int tid;
    protected Date from;
    protected Date to;
    protected int price;

    // globally access date format
    protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Period(int pid, int tid, Date from, Date to, int price) {
        this.pid = pid;
        this.tid = tid;
        this.to = to;
        this.from = from;
        this.price = price;
    }

    public Period(int tid, Date from, Date to, int price) {
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
        String insert = "INSERT INTO `5530db58`.`period` (`tid`, `from`, `to`, `price`) " +
                "VALUES ('" + tid + "', '" + formatFrom() + "', '" + formatTo() + "', '"+ price +"');";

        try {
            Connector.getInstance().statement.execute(insert);
            return true;
        } catch (SQLException e) {
            System.out.println("Insert failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Format from to sql datetime format
     * @return
     */
    public String formatFrom() {
        return from.getYear() + "-" + from.getMonth() + "-" + from.getDay() + " " + from.getHours() + ":00:00";
    }

    /**
     * Format to to sql datetime format
     * @return
     */
    public String formatTo() {
        return to.getYear() + "-" + to.getMonth() + "-" + to.getDay() + " " + to.getHours() + ":00:00";
    }

}
