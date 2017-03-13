package com.gradeycullins;

import java.sql.SQLException;

/**
 * Class to represent Temporary Housing objects in the database
 */
public class TH {

    int tid; // Primary key
    String name;
    String owner;
    String type;
    String phoneNum;
    String address;
    String url;
    int yearBuilt;


    public TH(String owner, String name, String type, String phoneNum, String address, int yearBuilt) {
        this.name = name;
        this.type = type;
        this.phoneNum = phoneNum;
        this.address = address;
        this.yearBuilt = yearBuilt;
        this.owner = owner;
    }

    /**
     * Add new TH into the database. Save the
     */
    public boolean insert()
    {
        String insert = "INSERT INTO `5530db58`.`th` (`name`, `owner`, `type`, `tel_no`, `year_built`) " +
                "VALUES ('"+name+"', '"+owner+"', '"+type+"', '"+phoneNum+"', '"+yearBuilt+"');";

        Connector connector = Connector.getConnector();

        if (connector == null)
            return false;

        try {
            connector.statement.execute(insert);
            connector.closeConnection();
            return true;
        } catch(SQLException e) {
            System.out.print("Insert failed");
            e.printStackTrace();
            return false;
        }
    }


}
