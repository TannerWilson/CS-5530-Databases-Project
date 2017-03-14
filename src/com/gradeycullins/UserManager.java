package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanner on 3/13/2017.
 */
public class UserManager {
    protected ArrayList<User> users;

    public UserManager(){
        String selectQuery = "SELECT * FROM `5530db58`.`user`";
        ResultSet resultSet;
        users = new ArrayList<>();

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
                users.add(newUser);

            }
        } catch (SQLException e) {
            System.out.println("An error occurred while attempting to retrieve the listing of users.");
            e.printStackTrace();
        }
    }
}
