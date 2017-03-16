package com.gradeycullins;

import java.sql.SQLException;

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
        String insert = "INSERT INTO `5530db58`.`feedback` (`login`, `score`, `description`, `usefulness`) " +
                "VALUES ('" + login + "', '" + score + "', '" + description + "', '" + usefulness + "');";

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
