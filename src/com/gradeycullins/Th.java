package com.gradeycullins;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Class to represent Temporary Housing objects in the database
 */
public class Th {

    int tid; // Primary key
    String owner;
    String name;
    String category;
    String phoneNum;
    String address;
    String url;
    int yearBuilt;

    LinkedList<Period> periods = new LinkedList<>();
    protected int lowestPrice = -1; // lowest priced available period. -1 means no recorded pricing
    protected int averageScore = -1; // average feedback score. -1 means no recording score
    protected LinkedList<String> keywords = new LinkedList<>();

    protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Th(int tid, String owner, String name, String category, String phoneNum,
              String address, String url, int yearBuilt) {

        this.tid = tid;
        this.owner = owner;
        this.name = name;
        this.category = category;
        this.phoneNum = phoneNum;
        this.address = address;
        this.url = url;
        this.yearBuilt = yearBuilt;
    }

    public Th(String owner, String name, String category, String phoneNum, String address, String url, int yearBuilt) {
        this.name = name;
        this.owner = owner;
        this.category = category;
        this.phoneNum = phoneNum;
        this.address = address;
        this.url = url;
        this.yearBuilt = yearBuilt;
    }

    public Th(String owner, String name, String category, String phoneNum, String address, int yearBuilt) {
        this.name = name;
        this.category = category;
        this.phoneNum = phoneNum;
        this.address = address;
        this.yearBuilt = yearBuilt;
        this.owner = owner;
    }

    /**
     * Add new Th into the database.
     * Save the user login as owner.
     */
    public boolean insert()
    {
        String insert = "INSERT INTO `5530db58`.`th` (`name`, `owner`, category, `phone_num`, `address`, `year_built`) " +
                "VALUES ('"+name+"', '"+owner+"', '"+category+"', '"+phoneNum+"', '"+address+"', '"+yearBuilt+"');";

        try {
            Connector.getInstance().statement.execute(insert);
            Connector.getInstance().closeConnection();
            return true;
        } catch(SQLException e) {
            System.out.println("Insert failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Edits information in regarding the current TH
     * @param feildName: what column is being changed
     * @param newValue
     * @return
     */
    public boolean updateField(String feildName, String newValue, int tid){

       String update = "UPDATE `5530db58`.`th` SET `"+ feildName +"`='"+ newValue +"' WHERE `tid`='"+tid+"';";

        try {
            Connector.getInstance().statement.execute(update);
            Connector.getInstance().closeConnection();
            return true;
        } catch(SQLException e) {
            System.out.println("Update failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Get all the periods associated with this th
     * @return
     */
    public void getAvailPeriods() {
        String query = "SELECT * FROM `5530db58`.`period` WHERE tid="+tid+";";

        ResultSet resultSet;

        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);

            while (resultSet.next()) {
                int pid = resultSet.getInt("pid");
                int tid = resultSet.getInt("tid");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                Date fromDate = Period.sdf.parse(from);
                Date toDate = Period.sdf.parse(to);
                int price = resultSet.getInt("price");

                Period p = new Period(pid, tid, fromDate, toDate, price);
                periods.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve available periods.");
        } catch (ParseException e) {
            System.out.println("Could not parse date.");
        }
    }
}
