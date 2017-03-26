package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Gradey Cullins on 3/25/17.
 */
public class FeedbackRating {
    protected String login;
    protected int fid;
    protected int rating;

    public FeedbackRating(String login, int fid, int rating) {
        this.login = login;
        this.fid = fid;
        this.rating = rating;
    }

    public static void insertFeedbackRating(String login, int fid, int rating) {
        try {
            Statement queryStatement = Connector.getInstance().connection.createStatement();
            String queryString = "" +
                    "SELECT * " +
                    "FROM feedback_rating fr " +
                    "WHERE fr.login='" + login + "' AND fr.fid=" + fid;

            ResultSet resultSet = queryStatement.executeQuery(queryString);

            if (resultSet.next()) { // update, not insert
                Statement updateStatement = Connector.getInstance().connection.createStatement();
                String updateString = "" +
                        "UPDATE feedback_rating fr " +
                        "SET fr.rating=" + rating + " " +
                        "WHERE fr.login='" + login + "' AND fr.fid=" + fid;

                updateStatement.executeUpdate(updateString);
                System.out.print("Updated your feedback rating to " + rating + "\n");
            } else {
                Statement insertStatement = Connector.getInstance().connection.createStatement();
                String insertString = "" +
                        "INSERT INTO feedback_rating VALUES ('" + login + "', " + fid + ", " + rating + ")";

                insertStatement.executeUpdate(insertString);
                System.out.print("Added your feedback rating\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
