package com.gradeycullins;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Reservation {
    String login;
    int tid;
    int pid;
    Date from;
    Date to;
    float cost;
    int pricePerNight;
    String houseName;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Reservation(String login, int tid, int pid) {
        this.login = login;
        this.tid = tid;
        this.pid = pid;
    }

    public Reservation(String login, int tid, int pid, Date from, Date to, int price, String name) {
        this.login = login;
        this.tid = tid;
        this.pid = pid;
        this.from = from;
        this.to = to;
        pricePerNight = price;
        houseName = name;

        // Compute cost
        cost = computeCost();
    }

    public Reservation(String login, int tid, int pid, Date from, Date to, float cost) {
        this.login = login;
        this.tid = tid;
        this.pid = pid;
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    /**
     * Inserts this reservation into the database
     * @return success of insert
     */
    public boolean insert() {
        String insert = "INSERT INTO `5530db58`.`reservation` (`login`, `tid`, `pid`, `to`, `from`, `cost`) " +
                "VALUES ('" + login + "', '" + tid + "', '" + pid + "', '" + sdf.format(to) + "', '"+ sdf.format(from)
                +"', '"+ cost +"');";

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
     * Coputes the total cost of the reservation
     */
    public float computeCost(){
        long diff = getDateDiff(from, to, TimeUnit.DAYS);
//        long days = TimeUnit.MILLISECONDS.toDays(diff);
        return diff * pricePerNight;
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

}
