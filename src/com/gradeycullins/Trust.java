package com.gradeycullins;

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

        String insertTrust = "" +
                "INSERT INTO " +
                "trust(truster, trustee, is_trusted) " +
                "values('" + trusterLogin + "', '" + trusteeLogin + "', " + is_trusted + ")";

        try {
            Connector.getInstance().statement.executeUpdate(insertTrust);
            System.out.print("You marked " + trustee.firstName);
            System.out.print(is_trusted == 1 ? " as a trusted user!" : " as an untrusted user!");
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Could not record trust relationship. Exiting . . .");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
