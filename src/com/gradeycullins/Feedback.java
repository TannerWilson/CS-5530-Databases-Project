package com.gradeycullins;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Feedback {
    int fid;
    String login;
    int score;
    String description;
    float usefulness;
    int tid;

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
}
