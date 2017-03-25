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

    public ThManager() {
        properties = new HashMap<>();
        order = new LinkedList<>();
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
        if (!_category.isEmpty())
            whereStatement += "t.category='" + _category + "' ";

        // ordering
        String orderStatement = "";
        if (order == ASCENDING_PRICE) {
            orderStatement = " ORDER BY min_price ASC";
        } else if (order == DESCENDING_PRICE) {
            orderStatement = " ORDER BY min_price DESC";
        } else if (order == DESCENDING_RATING) {
            orderStatement = " ORDER BY avg_score DESC";
        } else if (order == ASCENDING_RATING) {
            orderStatement = " ORDER BY avg_score ASC";
        } else if (order == DESCENDING_TRUSTED_RATING) {
            orderStatement = " ORDER BY ";
        } else if (order == ASCENDING_TRUSTED_RATING) {
            orderStatement = " ORDER BY ";
        }

        String selectQuery =
                "SELECT DISTINCT t.tid, t.owner, t.name, t.category, t.phone_num, t.address, t.url, t.year_built, " +
                        "MIN(p.price) as min_price, AVG(f.score) as avg_score " +
                "FROM " + Connector.DATABASE + ".th t " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".period p " +
                "ON t.tid=p.tid " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".keyword k " +
                "ON t.tid=k.tid " +
                "LEFT OUTER JOIN " + Connector.DATABASE + ".feedback f " +
                "ON f.tid=t.tid " +
                whereStatement + " GROUP BY t.tid " + orderStatement;

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

                // joined columns
                int lowestPrice = resultSet.getInt("min_price");
                int averageScore = resultSet.getInt("avg_score");

                // store the th from query result
                Th newTh = new Th(tid, owner, name, category, phoneNum, address, url, yearBuilt);

                newTh.lowestPrice = lowestPrice;
                newTh.averageScore = averageScore;

                if (!keywords.isEmpty()) {
                    // TODO query for keywords . . . this is unholy
                    String keywordQuery = "" +
                            "SELECT k.word " +
                            "FROM keyword k " +
                            "WHERE k.tid=" + tid;

                    resultSet = Connector.getInstance().statement.executeQuery(keywordQuery);
                    while (resultSet.next())
                        newTh.keywords.add(resultSet.getString("word"));
                }

                // only add th's with all matching keywords
                if (newTh.keywords.containsAll(keywords)) {
                    // store the order in which THs were returned
                    this.properties.put(tid, newTh);

                    // store the order of results based on user input in 'order'
                    if (!this.order.contains(tid))
                        this.order.add(tid);
                }
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
