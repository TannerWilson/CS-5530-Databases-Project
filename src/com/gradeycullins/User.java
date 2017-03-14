package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Tanner on 3/11/2017.
 */
public class User {

    /* relational db column mappings */
    protected String login;
    protected String password;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String gender;
    protected float isTrusted = 0;
    protected String favorite = null;
    protected String address;
    /* end db column mappings */

    protected boolean isAuthenticated = false;

    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String firstName, String middleName,
                String lastName, String gender, String address) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
    }

    public User(String login, String password, String firstName, String middleName,
                String lastName, String gender, float isTrusted, String favorite, String address) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.isTrusted = isTrusted;
        this.favorite = favorite;
        this.address = address;
    }

    /**
     * Register the User POJO into the database
     * @return success of the register attempt
     */
    public boolean register() {
        String insert = "INSERT INTO `5530db58`.`user` (`login`, `password`, `first_name`, `middle_name`, `last_name`, `gender`, `address`) " +
                "VALUES ('" + login + "', '" + password + "', '" + firstName + "', '" + middleName + "', '" + lastName + "', '" + gender + "', '" + address + "');";

        try {
            Connector.getInstance().statement.execute(insert);
            this.isAuthenticated = true;
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Login \'" + this.login + "\' already exists. Please use a different login and try again.");
            } else {
                System.out.println("Insert failed");
                System.out.println(e.getMessage());
            }
            return false;
        }
    }

    /**
     * Attempt to authenticate a returning user using given credentials
     * @return success of the login attempt
     */
    public boolean login(String login, String password) {
        String query = "select * from 5530db58.user where login='" + login + "';";
        ResultSet results;

        try {
            results = Connector.getInstance().statement.executeQuery(query);

            // Step to result
            if (results.next()) {
                String foundPass = results.getString("password");
                if (foundPass.equals(password)) {
                    populateUser(results);
                    this.isAuthenticated = true;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                System.err.println("Unable to establish connection to database");
                return false;
            }
            System.err.println("Unable to execute query:" + query + "\n");
            System.err.println(e.getMessage());
        }
        return false;
    }

    private void populateUser(ResultSet resultSet) {
        try {
            this.firstName = resultSet.getString("first_name");
            this.middleName = resultSet.getString("middle_name");
            this.lastName = resultSet.getString("last_name");
            this.gender = resultSet.getString("gender");
            this.isTrusted = resultSet.getFloat("is_trusted");
            this.favorite = resultSet.getString("favorite");
            this.address = resultSet.getString("address");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates this user's trust score in the database
     * @param increment: whether to increment or drecrement trust score
     * @return success of the change
     */
    public boolean updateTrustValue(boolean increment){

        return false;
    }

}