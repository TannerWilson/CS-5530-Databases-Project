package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gradey Cullins on 3/13/17.
 */
public class ThManager {
    protected Map<Integer, Th> properties;

    public ThManager() {
        String selectQuery = "SELECT * FROM `5530db58`.`th`";
        ResultSet resultSet;
        properties = new HashMap<>();

        // populate the properties variable preemptively
        try {
            resultSet = Connector.getInstance().statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                int tid = resultSet.getInt("tid");
                String owner = resultSet.getString("owner");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String phoneNum = resultSet.getString("phone_num");
                String address = resultSet.getString("address");
                String url = resultSet.getString("url");
                int yearBuilt = resultSet.getInt("year_built");
                Th newTh = new Th(tid, owner, name, type, phoneNum, address, url, yearBuilt);
                properties.put(tid, newTh);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while attempting to retrieve the listing of temporary housings.");
            e.printStackTrace();
        }
    }

    public void getUserProperties(String login){
        String query = "SELECT * FROM `5530db58`.`th` where owner='"+login+"';";


        ResultSet resultSet;
        properties = new HashMap<>();

        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);
            while (resultSet.next()) {
                int tid = resultSet.getInt("tid");
                String owner = resultSet.getString("owner");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String phoneNum = resultSet.getString("phone_num");
                String address = resultSet.getString("address");
                String url = resultSet.getString("url");
                int yearBuilt = resultSet.getInt("year_built");
                Th newTh = new Th(tid, owner, name, type, phoneNum, address, url, yearBuilt);
                properties.put(tid, newTh);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while attempting to retrieve the listing of temporary housings.");
            e.printStackTrace();
        }
    }
}
