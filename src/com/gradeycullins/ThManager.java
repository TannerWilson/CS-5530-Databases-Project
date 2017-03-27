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
    protected User user;

    public ThManager() {
        properties = new HashMap<>();
        order = new LinkedList<>();
    }

    public void getTh(int minPrice, int maxPrice, String _owner, String _name, String city,
                      String state, List<String> keywords, String _category, int order) {

        // where conditional(s)
        String whereStatement = "WHERE 1=1 ";
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

        boolean onlyTrustedRatings = false;
        String trustedRatingCondition = " ";

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
        } else if (order == DESCENDING_TRUSTED_RATING || order == ASCENDING_TRUSTED_RATING) {
            onlyTrustedRatings = true;
            trustedRatingCondition = "" +
                    " AND f.login = ANY " +
                    "(" +
                    "SELECT f1.login " +
                    "FROM feedback f1, trust t1 " +
                    "WHERE f1.login=t1.trustee " +
                    "AND t1.truster='" + this.user.login + "' " +
                    ") ";
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
                "ON f.tid=t.tid " + trustedRatingCondition +
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

                // filter non-trusted user feedbacks
                if (order == ASCENDING_TRUSTED_RATING || order == DESCENDING_TRUSTED_RATING) {
                    String sqlOrder = (order == ASCENDING_TRUSTED_RATING) ? "ASC" : "DESC";

                }

                float averageScore = resultSet.getFloat("avg_score");

                // store the th from query result
                Th newTh = new Th(tid, owner, name, category, phoneNum, address, url, yearBuilt);

                newTh.lowestPrice = lowestPrice;
                newTh.averageScore = averageScore;

                if (!keywords.isEmpty()) {
                    String keywordQuery = "" +
                            "SELECT k.word " +
                            "FROM keyword k " +
                            "WHERE k.tid=" + tid;

                    // create a new mysql statement to avoid overwriting TH query results
                    Statement keywordStatement = Connector.getInstance().connection.createStatement();

                    ResultSet keywordResultSet = keywordStatement.executeQuery(keywordQuery);

                    while (keywordResultSet.next())
                        newTh.keywords.add(keywordResultSet.getString("word"));

                    // only add th's with all matching keywords
                    if (newTh.keywords.containsAll(keywords)) {
                        // store the order in which THs were returned
                        this.properties.put(tid, newTh);

                        // store the order of results based on user input in 'order'
                        if (!this.order.contains(tid))
                            this.order.add(tid);
                    }
                } else {
                    // store the order of results based on user input in 'order'
                    if (!this.order.contains(tid))
                        this.order.add(tid);

                    this.properties.put(tid, newTh);
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

        HashMap<String, Integer> initial = new HashMap<>();

        for(Reservation r : reservations) {

            String housesToSuggest = "SELECT t.name, t.tid\n" +
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
                    int tid = resultSet.getInt("tid");
                    initial.put(houseName, tid);
                }

            }catch (SQLException e){
                System.out.println("Failed to get Suggestions");

            }
        }
        return sortSuggestions(initial);
    }

    public ArrayList<String> sortSuggestions(HashMap<String, Integer> suggestions){

        TreeMap<Integer, String> visitCounts = new TreeMap<Integer, String>(Collections.reverseOrder());

        for(Map.Entry<String, Integer> entry : suggestions.entrySet()){
            String select = "SELECT count(v.tid) AS count\n" +
                    "FROM 5530db58.th t, 5530db58.visit v\n" +
                    "WHERE t.tid = v.tid AND t.tid = "+entry.getValue()+";";

            ResultSet resultSet;
            try {
                resultSet = Connector.getInstance().statement.executeQuery(select);

                if(resultSet.next()){
                    int count = resultSet.getInt("count");
                    visitCounts.put(count, entry.getKey());
                }

            }catch (SQLException e){
                System.out.println("Failed to get visit count");
            }
        }

        return new ArrayList<String>(visitCounts.values());
    }


    /**
     * Returns the most popular THs
     * @param resultCount
     */
    public ArrayList<String> getMostPopular(String category, int resultCount) {

        ArrayList<String>  results = new ArrayList<>();
        String query = "SELECT t.name\n" +
                "FROM 5530db58.th t, 5530db58.visit v\n" +
                "WHERE t.tid = v.tid AND t.category = '"+category+"'\n" +
                "GROUP BY (t.tid)\n" +
                "ORDER BY (COUNT(v.tid)) DESC\n" +
                "LIMIT "+resultCount+";";

        ResultSet resultSet;
        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);

            while(resultSet.next()){
                String name = resultSet.getString("name");
                results.add(name);
            }

        }catch (SQLException e){
            System.out.println("Failed to get most popular TH");
        }
        return results;
    }

    /**
     * Returns the most expensive THs
     * Adds the average cost of each TH into the
     * averages parameter to be returned to the main as well
     * @param resultCount
     */
    public ArrayList<String> getMostExpensive(String category, int resultCount, ArrayList<Float> averages) {
        averages.clear(); // Ensure averages is empty to conserve order
        ArrayList<String>  results = new ArrayList<>();
        String query = "SELECT t.name, avg(r.cost) AS average\n" +
                "FROM 5530db58.th t, 5530db58.visit v, 5530db58.reservation r \n" +
                "WHERE t.tid = v.tid AND v.pid = r.pid AND t.category = '"+category+"'\n" +
                "GROUP BY (t.tid)\n" +
                "ORDER BY (avg(r.cost)) DESC\n" +
                "LIMIT "+resultCount+";";

        ResultSet resultSet;
        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);

            while(resultSet.next()){
                String name = resultSet.getString("name");
                Float avg = resultSet.getFloat("average");
                results.add(name);
                averages.add(avg);
            }

        }catch (SQLException e){
            System.out.println("Failed to get most expensive TH");
        }

        return results;
    }

    /**
     * Returns the most highly reated THs
     * Adds the average cost of each TH into the
     * averages parameter to be returned to the main as well
     * @param resultCount
     */
    public ArrayList<String> getHighRated(String category, int resultCount, ArrayList<Float> averages) {
        averages.clear(); // Ensure averages is empty to maintain ordering
        ArrayList<String>  results = new ArrayList<>();
        String query = "SELECT t.name, avg(f.score) AS average\n" +
                "FROM 5530db58.th t, 5530db58.feedback f\n" +
                "WHERE t.tid = f.tid AND t.category = '"+category+"'\n" +
                "GROUP BY (t.tid)\n" +
                "ORDER BY (avg(f.score)) DESC\n" +
                "LIMIT "+resultCount+";";

        ResultSet resultSet;
        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);

            while(resultSet.next()){
                String name = resultSet.getString("name");
                Float avg = resultSet.getFloat("average");
                results.add(name);
                averages.add(avg);
            }

        }catch (SQLException e){
            System.out.println("Failed to get highly rated THs");
        }

        return results;
    }
}
