package com.gradeycullins;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Gradey Cullins on 3/13/17.
 */
public class ThManager {
    protected Map<Integer, Th> properties; // most recently user-queried properties
    protected Map<Integer, List<Period>> periods; // th.tid -> list of avail. periods
    protected Map<Integer, List<Keyword>> keywords; // th.tid -> list of pertaining keywords

    public ThManager() {
        properties = new HashMap<>();
        periods = new HashMap<>();
        keywords = new HashMap<>();
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
                "SELECT * " +
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
                periods.put(tid, new LinkedList<>());

                // construct a period
                int pid = resultSet.getInt("pid");
                if (pid != 0) {
                    String from = resultSet.getString("from");
                    String to = resultSet.getString("to");
                    Date fromDate = Period.sdf.parse(from);
                    Date toDate = Period.sdf.parse(to);
                    int price = resultSet.getInt("price");
                    Period newPeriod = new Period(pid, tid, fromDate, toDate, price);
                    periods.get(tid).add(newPeriod);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("exiting . . .");
            System.exit(0);
        } catch (ParseException e) {
            System.out.println("Date could not be parsed. Exiting . . .");
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

    public ArrayList<String> getSuggestedProperties(ArrayList<Reservation> reservations, User user){

        ArrayList<String> results = new ArrayList<>();

        for(Reservation r : reservations) {

            String housesToSuggest = "SELECT t.name\n" +
                    "FROM 5530db58.th t\n" +
                    "WHERE t.tid IN (\n" +
                    "SELECT v.tid\n" +
                    "FROM 5530db58.visit v\n" +
                    "WHERE v.tid <> "+r.tid+" AND v.login IN (\n" +
                    "SELECT v1.login\n" +
                    "FROM 5530db58.visit v1\n" +
                    "WHERE v1.login <> '" + user.login + "' AND v1.tid = "+r.tid+"));";

            ResultSet resultSet;
            try {
                resultSet = Connector.getInstance().statement.executeQuery(housesToSuggest);

                while(resultSet.next()){
                    String houseName = resultSet.getString("name");
                    results.add(houseName);
                }

            }catch (SQLException e){
                System.out.println("Failed to get Suggestions");

            }
        }
        return results;
    }
}
