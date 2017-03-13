package com.gradeycullins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Gradey Cullins
 * <p>
 * Object used to connect to our MySQL database
 */
public class Connector {

    public static Connector instance = null;
    public Connection connection;
    public Statement statement;

    private final String USERNAME = "5530u58";
    private final String PASSWORD = "o3tve4iq";
    private final String URL = "jdbc:mysql://georgia.eng.utah.edu/5530db58?autoReconnect=true&useSSL=false";

    public static Connector getInstance() {
        if (instance == null) {
            try {
                instance = new Connector();
            } catch (Exception e) {
                System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
                System.err.println(e.getMessage());
            }
        }

        return instance;
    }

    private Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            this.statement = connection.createStatement();
        } catch (Exception e) {
            System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connector getConnector() {
        Connector connector;
        int i = 0;
        while (i++ < 1000) { // attempt to connect 1000 times
            try {
                connector = new Connector();
                return connector;
            } catch (Exception e) {
                System.out.println("attempt to connect to DB failed");
                return null;
            }
        }
        return null;
    }
}