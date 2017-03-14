package com.gradeycullins;

import java.sql.SQLException;

/**
 * Created by Tanner on 3/13/2017.
 */
public class Feedback {
    String userLogin;
    int score;
    String description;
    float usefulness;

    public Feedback(String userLogin, int score, String description, float usefulness) {
        this.userLogin = userLogin;
        this.score = score;
        this.description = description;
        this.usefulness = usefulness;
    }

    public boolean insert() {
        String insert = "INSERT INTO `5530db58`.`feedback` (`login`, `score`, `description`, `usefulness`) " +
                "VALUES ('" + userLogin + "', '" + score + "', '" + description + "', '" + usefulness + "');";

        try {
            Connector.getInstance().statement.execute(insert);
            return true;
        } catch (SQLException e) {
            System.out.println("Insert failed");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
