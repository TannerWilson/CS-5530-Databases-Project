package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Object used to pull and manage all users from the database.
 */
public class UserManager {
    protected ArrayList<User> users;

    public UserManager(){
        users = new ArrayList<>();
    }

    public  ArrayList<User> getAllUsers(){
        String selectQuery = "SELECT * FROM `5530db58`.`user`";
        ResultSet resultSet;
        ArrayList<User> result = new ArrayList<>();

        try {
            resultSet = Connector.getInstance().statement.executeQuery(selectQuery);
            int index = 0;
            while (resultSet.next()) {
                index++;
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String middleName = resultSet.getString("middle_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                float isTrusted = resultSet.getFloat("is_trusted");
                String favorite = resultSet.getString("favorite");
                String address = resultSet.getString("address");


                User newUser = new User(login, password, firstName, middleName, lastName,
                        gender, isTrusted, favorite, address);
                result.add(newUser);

            }
        } catch (SQLException e) {
            System.out.println("An error occurred while attempting to retrieve the listing of users.");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Retuens the top n most trusted users
     * @param n
     * @return
     */
    public ArrayList<String> getMostTrustedUsers(int n){

        ArrayList<User> allUsers = getAllUsers();
        TreeMap<Integer, String> sortedResults = new TreeMap<>(Collections.reverseOrder());

        // Grab and sum the trusted values for each user
        for(User user : allUsers){
            String getTrusts = "SELECT is_trusted FROM 5530db58.trust" +
                                "WHERE trustee ='"+user.login+"';";

            ResultSet resultSet;
            try {
                resultSet = Connector.getInstance().statement.executeQuery(getTrusts);
                int trustScore = 0;
                while (resultSet.next()) {
                    int score = resultSet.getInt("is_trusted");
                    if(score == 0)
                        score = -1;
                    trustScore += score;
                }
                sortedResults.put(trustScore, user.login);
            } catch (SQLException e) {
                System.out.println("An error occurred while attempting to get the trust values for " + user.login);
                e.printStackTrace();
            }
        }

        ArrayList<String> values  = new ArrayList<>(sortedResults.values());
        ArrayList<String> result = new ArrayList<>();

        if(values.size() < n)
        {
           for(String login : values)
               result.add(login);
        }else {
            for (int i = 0; i < n; i++) {
                result.add(values.get(i));
            }
        }

        return result;
    }
}
