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
    // orderings
    protected final int DESCENDING_PRICE = 1;
    protected final int ASCENDING_PRICE = 2;
    protected final int DESCENDING_RATING = 3;
    protected final int ASCENDING_RATING = 4;
    protected final int DESCENDING_TRUSTED_RATING = 5;
    protected final int ASCENDING_TRUSTED_RATING = 6;

    protected Map<Integer, Th> properties; // most recently user-queried properties
    protected LinkedList<Integer> order; // stores the order of th's returned by user ordering
    protected Map<Integer, List<Period>> periods; // th.tid -> list of avail. periods
    protected Map<Integer, List<Feedback>> feedbacks; // th.tid -> feedback of th
    protected Map<Integer, List<Keyword>> keywords; // th.tid -> list of pertaining keywords

    public ThManager() {
        properties = new HashMap<>();
        order = new LinkedList<>();
        feedbacks = new HashMap<>();
        periods = new HashMap<>();
        keywords = new HashMap<>();
    }

    public void getTh(int minPrice, int maxPrice, String _owner, String _name, String city,
                      String state, List<String> keywords, String _category, int order) {

        // where conditional(s)
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
            whereStatement += "t.category='" + _category + "' ";

        // ordering
        String orderStatement = "";
        if (order == ASCENDING_PRICE) {
            orderStatement = " ORDER BY p.price DESC";
        } else if (order == DESCENDING_PRICE) {
            orderStatement = " ORDER BY p.price ASC";
        } else if (order == DESCENDING_RATING) {
            orderStatement = " ORDER BY f.score DESC";
        } else if (order == ASCENDING_RATING) {
            orderStatement = " ORDER BY ";
        } else if (order == DESCENDING_TRUSTED_RATING) {
            orderStatement = " ORDER BY ";
        } else if (order == ASCENDING_TRUSTED_RATING) {
            orderStatement = " ORDER BY ";
        }

        String selectQuery =
                "SELECT * " +
                "FROM " + Connector.DATABASE + ".th t " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".period p " +
                "ON t.tid=p.tid " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".keyword k " +
                "ON t.tid=k.tid " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".feedback f " +
                "ON f.tid=t.tid " +
                whereStatement + orderStatement;

        try {
            ResultSet resultSet = Connector.getInstance().statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                // retrieve th columns for later storage
                int tid = resultSet.getInt("tid");
                String owner = resultSet.getString("owner");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                String phoneNum = resultSet.getString("phone_num");
                String address = resultSet.getString("address");
                String url = resultSet.getString("url");
                int yearBuilt = resultSet.getInt("year_built");

                // store the th from query result
                Th newTh = new Th(tid, owner, name, category, phoneNum, address, url, yearBuilt);
                this.properties.put(tid, newTh);

                // initialized the lists to hold th periods and feedbacks
                this.periods.put(tid, new LinkedList<>());
                this.feedbacks.put(tid, new LinkedList<>());

                // store the order of results based on user input in 'order'
                if (!this.order.contains(tid))
                    this.order.add(tid);

                // construct a period
                int pid = resultSet.getInt("pid");
                if (pid != 0) { // if this period exists . . .
                    String from = resultSet.getString("from");
                    String to = resultSet.getString("to");
                    Date fromDate = Period.sdf.parse(from);
                    Date toDate = Period.sdf.parse(to);
                    int price = resultSet.getInt("price");
                    Period newPeriod = new Period(pid, tid, fromDate, toDate, price);
                    periods.get(tid).add(newPeriod);
                }

                // construct a feedback
                int fid = resultSet.getInt("fid");
                if (fid != 0) { // if this feedback exists . . .
                    String login = resultSet.getString("login");
                    int score = resultSet.getInt("score");
                    String description = resultSet.getString("description");
                    float usefulness = resultSet.getFloat("usefulness");
                    Feedback newFeedback = new Feedback(login, score, description, usefulness, tid);
                    this.feedbacks.get(tid).add(newFeedback);
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
}
