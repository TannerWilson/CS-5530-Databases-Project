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
        properties = new HashMap<>();
    }

    public void getTh(int minPrice, int maxPrice, String _owner, String _name, String city,
                      String state, List<String> keywords, String _category) {

        String whereStatement = "WHERE 1=1";
        if (minPrice != -1)
            whereStatement += " AND p.price >= " + minPrice;
        if (maxPrice != -1)
            whereStatement += " AND p.price <= " + maxPrice;
        if (!_owner.isEmpty())
            whereStatement += " AND t.owner='" + _owner + "'";
        if (!_name.isEmpty())
            whereStatement += " AND t.name='" + _name + "'";
        if (!city.isEmpty())
            whereStatement += " AND t.address LIKE '%" + city + "%'";
        if (!state.isEmpty())
            whereStatement += " AND t.address LIKE '%" + state + "%'";
        if  (!keywords.isEmpty())
            for (int i = 0; i < keywords.size(); ++i)
                whereStatement += " AND k.word='" + keywords.get(i) + "'";
        if (!_category.isEmpty())
            whereStatement += "t.category='" + _category + "'\n";

        String selectQuery =
                "SELECT *\n" +
                "FROM " + Connector.DATABASE + ".th t " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".period p " +
                "ON t.tid=p.tid " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".keyword k " +
                "ON t.tid=k.tid " +
                whereStatement;

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
            System.err.println("exiting . . .");
            System.exit(0);
        }
    }


    /**
     * Pulls all TH's owned by a given user.
     * @param login
     */
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
                String category = resultSet.getString("category");
                String phoneNum = resultSet.getString("phone_num");
                String address = resultSet.getString("address");
                String url = resultSet.getString("url");
                int yearBuilt = resultSet.getInt("year_built");
                Th newTh = new Th(tid, owner, name, category, phoneNum, address, url, yearBuilt);
                properties.put(tid, newTh);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while attempting to retrieve the listing of temporary housings.");
            e.printStackTrace();
        }
    }
}
