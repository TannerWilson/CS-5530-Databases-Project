package u58;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Gradey Cullins on 3/25/17.
 */
public class Favorite {
    public String login;
    public int tid;

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
//            System.out.print("Added to favorites!\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determine whether two users are 1 or 2 degrees of separation
     * apart in regards to their commonly favorited Ths
     * @param u1
     * @param u2
     * @return degree of separation
     */
    public static int degreesOfSeparation(User u1, User u2) {
        try {
            /* are they 1 degree apart? */
            Statement degreeStatement = Connector.getInstance().connection.createStatement();
            String degreeQuery = "" +
                    "SELECT f1.login, f2.login " +
                    "FROM favorite f1 " +
                    "INNER JOIN favorite f2 " +
                    "ON f1.login!=f2.login AND f1.tid=f2.tid AND f1.login='" + u1.login + "' AND f2.login='" + u2.login + "'";

            ResultSet resultSet = degreeStatement.executeQuery(degreeQuery);
            if (resultSet.next()) {
                return 1;
            }

            /* are they 2 degree apart? */
            degreeStatement = Connector.getInstance().connection.createStatement();
            degreeQuery = "" +
                    "SELECT f2.login " +
                    "FROM favorite f2, favorite u1 " +
                    "WHERE f2.login!=u1.login AND u1.tid=f2.tid AND u1.login='" + u1.login + "' " +
                    "AND f2.login=ANY " +
                        "( " +
                            "SELECT f3.login " +
                            "FROM favorite f3, favorite u2 " +
                            "WHERE f3.login!=u2.login AND f3.tid=u2.tid AND u2.login='" + u2.login + "'" +
                        ")";

            resultSet = degreeStatement.executeQuery(degreeQuery);

            if (resultSet.next()) {
                return 2;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        return 0;
    }
}

















