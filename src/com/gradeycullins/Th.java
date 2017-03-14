package com.gradeycullins;

import java.sql.SQLException;

/**
 * Class to represent Temporary Housing objects in the database
 */
public class Th {

    int tid; // Primary key
    String owner;
    String name;
    String type;
    String phoneNum;
    String address;
    String url;
    int yearBuilt;


    public Th(int tid, String owner, String name, String type, String phoneNum, String address, String url, int yearBuilt) {
        this.tid = tid;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.phoneNum = phoneNum;
        this.address = address;
        this.url = url;
        this.yearBuilt = yearBuilt;
    }

    public Th(String owner, String name, String type, String phoneNum, String address, int yearBuilt) {
        this.name = name;
        this.type = type;
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
                "VALUES ('"+name+"', '"+owner+"', '"+type+"', '"+phoneNum+"', '"+address+"', '"+yearBuilt+"');";

//        Connector connector = Connector.getConnector();
//        if (connector == null)
//            return false;

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

}
