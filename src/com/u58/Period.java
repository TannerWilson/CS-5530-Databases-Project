package com.u58;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
                "VALUES ('" + tid + "', '" + sdf.format(from) + "', '" + sdf.format(to) + "', '"+ price +"');";

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
//        return from.getYear() + "-" + from.getMonth() + "-" + from.getDay() + " " + from.getHours() + ":00:00";
        return sdf.format(from);
    }

    /**
     * Format to to sql datetime format
     * @return
     */
    public String formatTo() {
//        return to.getYear() + "-" + to.getMonth() + "-" + to.getDay() + " " + to.getHours() + ":00:00";
        return sdf.format(to);
    }

    /**
     * Converts SQL DATETIME to java Date object
     */
    public Date sqlToDate(String timeStamp){
        String[] firstSplit = timeStamp.split(" ");
        String[] left = firstSplit[0].split("-");
        String[] right = firstSplit[1].split(":");

        return new Date(Integer.parseInt(left[0]), Integer.parseInt(left[1]), Integer.parseInt(left[2]));
    }

    public boolean checkReservations(Reservation res){
        String query = "SELECT * FROM `5530db58`.`reservation` WHERE pid="+this.pid+";";

        // Pull all reservations associated with this period
        ArrayList<Reservation> reservations = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                int tid = resultSet.getInt("tid");
                int pid = resultSet.getInt("pid");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                Date fromDate = Period.sdf.parse(from);
                Date toDate = Period.sdf.parse(to);
                float cost = resultSet.getFloat("cost");

                Reservation p = new Reservation(login, tid, pid, fromDate, toDate, cost);
                reservations.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve available periods.");
        } catch (ParseException e) {
            System.out.println("Could not parse date.");
        }

        for(Reservation current : reservations){
            long currentTo = current.to.getTime();
            long currentFrom = current.from.getTime();
            long insertTo = res.to.getTime();
            long insertFrom = res.from.getTime();

            boolean fromCheck = (insertFrom >= currentFrom) && (insertFrom <= currentTo);
            boolean toCheck = (insertTo >= currentFrom) && (insertTo <= currentTo);

            if(fromCheck || toCheck)
                return false;
        }

        return true;
    }

    /**
     * Evaluates and splits the selected period if the given
     * reservation lands somewhere in the middle of the two dates.
     * Creates a new period to the "Left" and the "Right" of the current
     * period when viewing time as a number line. Deletes the current
     * period from the database.
     * @param res
     */
    public void updatePeriods(Reservation res){
        // Check and update from dates
        long diff = getDateDiff(from, res.from, TimeUnit.DAYS);
        if(diff !=0 && diff > 0){ // Dates differ
            Period leftNew = new Period(this.tid, this.from, res.from,this.price);
        }

        // Check and update to dates
        long diff2 = getDateDiff(to, res.to, TimeUnit.DAYS);
        if(diff2 !=0 && diff2 < 0){ // Dates differ
            Period rightNew = new Period(this.tid, res.to, this.to, this.price);
        }

        // New Period associated with reservation


        deletePeriod();
    }

    public void deletePeriod(){
       String delete = "DELETE FROM `5530db58`.`period` WHERE `pid`='"+ this.pid +"';";

        ResultSet resultSet;
        try {
            resultSet = Connector.getInstance().statement.executeQuery(delete);
        }catch (SQLException e){
            System.out.println("Failed to delete period");
        }
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
