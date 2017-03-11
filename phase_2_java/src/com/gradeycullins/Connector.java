package com.gradeycullins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Gradey Cullins on 3/10/17.
 */
public class Connector {
    public Connection con;
    public Statement stmt;
    public Connector() throws Exception {
        try {
            String userName = "5530u58";
            String password = "o3tve4iq";
            String url = "jdbc:mysql://georgia.eng.utah.edu/5530db58?autoReconnect=true&useSSL=false";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            con = DriverManager.getConnection (url, userName, password);

            stmt = con.createStatement();
            //stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch(Exception e) {
            System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
            System.err.println(e.getMessage());
            throw(e);
        }
    }

    public void closeConnection() throws Exception{
        con.close();
    }
}
