package com.u58;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gradey Cullins on 3/17/17.
 */
public class Trust {
    protected String truster;
    protected String trustee;
    protected int is_trusted;

    public static final int FALSE = 0;
    public static final int TRUE = 1;

    public Trust(String truster, String trustee, int is_trusted) {
        this.truster = truster;
        this.trustee = trustee;
        this.is_trusted = is_trusted;
    }

    public static void addTrustRelationship(User truster, User trustee, int is_trusted) {
        String trusterLogin = truster.login;
        String trusteeLogin = trustee.login;

        String queryTrust = "" +
                "SELECT * " +
                "FROM trust t " +
                "WHERE t.truster='" + trusterLogin + "' AND " +
                "t.trustee='" + trusteeLogin + "'";
        try {
           ResultSet resultSet = Connector.getInstance().statement.executeQuery(queryTrust);
           if (resultSet.next()) { // update, not insert
               String updateQuery = "" +
                       "UPDATE trust t " +
                       "SET t.is_trusted=" + is_trusted + " " +
                       "WHERE t.truster='" + trusterLogin + "' AND " + "t.trustee='" + trusteeLogin + "'";

               Connector.getInstance().statement.executeUpdate(updateQuery);
               System.out.print("You updated " + trustee.firstName);
               System.out.print(is_trusted == 1 ? " as a trusted user!" : " as an untrusted user!");
               System.out.println();

           } else { // no relationship exists, insert
               String insertTrust = "" +
                       "INSERT INTO " +
                       "trust(truster, trustee, is_trusted) " +
                       "values('" + trusterLogin + "', '" + trusteeLogin + "', " + is_trusted + ")";

               Connector.getInstance().statement.executeUpdate(insertTrust);
               System.out.print("You marked " + trustee.firstName);
               System.out.print(is_trusted == 1 ? " as a trusted user!" : " as an untrusted user!");
               System.out.println();
           }
        } catch (SQLException e) {
            System.out.print("Encountered an error when attempting to lookup trust relationship between " +
                    trusterLogin + " and " + trusteeLogin);
            e.printStackTrace();
        }
    }
}
