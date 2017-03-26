package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Gradey Cullins on 3/25/17.
 */
public class Favorite {
    protected String login;
    protected int tid;

    public Favorite(String login, int tid) {
        this.login = login;
        this.tid = tid;
    }

    public static void insertFavorite(String login, int tid) {
        try {
            String queryString = "" +
                    "SELECT * " +
                    "FROM favorite f " +
                    "WHERE f.login='" + login + "' AND f.tid=" + tid;

            ResultSet resultSet = Connector.getInstance().statement.executeQuery(queryString);

            if (resultSet.next()) {
                System.out.print("You have already marked this TH as a favorite!\n");
                return;
            }

            String insertString = "" +
                    "INSERT INTO favorite " +
                    "VALUES ('" + login + "', " + tid + ")";

            Connector.getInstance().statement.executeUpdate(insertString);
            System.out.print("Added to favorites!\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
