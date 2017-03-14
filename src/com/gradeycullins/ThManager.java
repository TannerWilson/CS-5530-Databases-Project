package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
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
    }

    public void getTh(int minPrice, int maxPrice, String _owner, String _name, String city,
                      String state, List<String> keywords, String _category) {
        String selectQuery =
                "SELECT *\n" +
                "FROM " + Connector.DATABASE + ".th t\n" +
                "WHERE\n" +
                "t.name='" + _name + "'";

        try {
            ResultSet resultSet = Connector.getInstance().statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                int tid = resultSet.getInt("tid");
                String owner = resultSet.getString("owner");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                String phoneNum = resultSet.getString("phone_num");
                String address = resultSet.getString("address");
                String url = resultSet.getString("url");
                int yearBuilt = resultSet.getInt("year_built");
                Th newTh = new Th(tid, owner, name, category, phoneNum, address, url, yearBuilt);
                properties.put(tid, newTh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
