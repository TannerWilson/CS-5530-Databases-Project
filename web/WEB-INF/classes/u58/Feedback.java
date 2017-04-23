package u58;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Feedback {
    public int fid;
    public String login;
    public int score;
    public String description;
    public float usefulness;
    public int tid;

    public float averageUsefulness;

    public Feedback(int fid, String login, int score, String description, float usefulness, int tid) {
        this.fid = fid;
        this.login = login;
        this.score = score;
        this.description = description;
        this.usefulness = usefulness;
        this.tid = tid;
    }

    public Feedback(String login, int score, String description, float usefulness, int tid) {
        this.login = login;
        this.score = score;
        this.description = description;
        this.usefulness = usefulness;
        this.tid = tid;
    }

    public Feedback(String userLogin, int score, String description, float usefulness) {
        this.login = userLogin;
        this.score = score;
        this.description = description;
        this.usefulness = usefulness;
    }

    /**
     * Inserts this feedback into the database
     * @return success of insert
     */
    public boolean insert() {
        String queryString = "" +
                "SELECT * " +
                "FROM feedback f " +
                "WHERE f.tid=" + this.tid + " AND f.login='" + this.login + "'";

        String insertString = "" +
                "INSERT INTO " + Connector.DATABASE + ".feedback " + "(login, score, description, tid) " +
                "VALUES ('" + this.login + "', " + this.score + ", '" + this.description + "', " + this.tid + ");";

        try {
            Statement queryStatement = Connector.getInstance().connection.createStatement();
            ResultSet queryResult = queryStatement.executeQuery(queryString);

            if (queryResult.next()) {
                System.out.print("You have already left feedback on this TH.\n");
                return false;
            } else {
                Statement insertStatement = Connector.getInstance().connection.createStatement();
                insertStatement.executeUpdate(insertString);
                System.out.print("Thank you for the feedback.\n");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Insert failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve a list of all feedbacks for th
     * @param th the feedback to query
     * @return list of feedback
     */
    public static Map<Integer, Feedback> getThFeedback(Th th) {
        try {
            Map<Integer, Feedback> feedbacks = new HashMap<>();

            Statement queryStatement = Connector.getInstance().connection.createStatement();
            String queryString = "" +
                    "SELECT f.fid, f.login, f.score, f.description, f.usefulness, f.tid " +
                    "FROM th t, feedback f " +
                    "WHERE t.tid=f.tid AND t.tid=" + th.tid;

            ResultSet resultSet = queryStatement.executeQuery(queryString);

            while (resultSet.next()) {
                int fid = resultSet.getInt("fid");
                String login = resultSet.getString("login");
                int score = resultSet.getInt("score");
                String description = resultSet.getString("description");
                float usefulness = resultSet.getFloat("usefulness");
                int tid = resultSet.getInt("tid");

                Feedback feedback = new Feedback(fid, login, score, description, usefulness, tid);
                feedbacks.put(fid, feedback);
            }

            return feedbacks;

        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Retreive a mapping of the n most useful feedbacks with feedback id's as keys
     * @param n upper bound on number of useful feedbacks
     * @return
     */
    public static Map<Integer, Feedback> getNMostUsefulFeedbacks(int chosenTh, int n) {
        Map<Integer, Feedback> feedbacks = new LinkedHashMap<>();

        try {
            Statement feedbackStatement = Connector.getInstance().connection.createStatement();
            String feedbackString = "" +
                    "SELECT f.fid, f.login as author, f.score, f.description, f.tid, AVG(fr.rating) avg_usefulness " +
                    "FROM feedback f " +
                    "LEFT OUTER JOIN feedback_rating fr " +
                    "ON f.fid=fr.fid " +
                    "WHERE f.tid=" + chosenTh + " " +
                    "GROUP BY f.fid " +
                    "ORDER BY avg_usefulness " +
                    "LIMIT " + n;

            ResultSet resultSet = feedbackStatement.executeQuery(feedbackString);

            while (resultSet.next()) {
                int fid = resultSet.getInt("fid");
                String author = resultSet.getString("author");
                int score = resultSet.getInt("score");
                String description = resultSet.getString("description");
                int tid = resultSet.getInt("tid");
                float avg_usefulness = resultSet.getFloat("avg_usefulness");

                Feedback feedback = new Feedback(fid, author, score, description, 0, tid);
                feedback.averageUsefulness = avg_usefulness;
                feedbacks.put(fid, feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return feedbacks;

    }
}
