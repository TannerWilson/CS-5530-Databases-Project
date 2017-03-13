package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Tanner on 3/11/2017.
 */
public class User {
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String address;

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

    /**
     * Register the User POJO into the database
     * @return success of the register attempt
     */
    public boolean register(String firstName, String lastName, String middleName, String gender,
                            String address, String login, String password) {

//        Connector connector = Connector.getConnector();
//        if (connector == null)
//            return false;

        String insert = "INSERT INTO `5530db58`.`user` (`login`, `password`, `first_name`, `middle_name`, `last_name`, `gender`, `address`) " +
                "VALUES ('"+login+"', '"+password+"', '"+firstName+"', '"+middleName+"', '"+lastName+"', '"+gender+"', '" +address+"');";

        try {
            Connector.getInstance().statement.execute(insert);

            //connector.closeConnection(); Closed connection too early
            return true;
        } catch(SQLException e) {
            System.out.print("Insert failed");
            System.out.print(e.getMessage());
            return false;
        }finally {
//            Connector.getInstance().closeConnection();
        }
    }

    /**
     * Attempt to authenticate a returning user using given credentials
     * @return success of the login attempt
     */
    public boolean login(String login, String password) {
//        Connector connector = Connector.getConnector();
//
//        if (connector == null)
//            return false;

        //String query = "select password from User where login='"+login+"';";

        String query = "select password from 5530db58.user where login='"+login+"';";

        ResultSet results;
        try{
            results = Connector.getInstance().statement.executeQuery(query);

            // Step to result
             if(results.next())
             {
                 String foundPass = results.getString("password");
                 if(foundPass.equals(password))
                 {
//                     Connector.getInstance().closeConnection();
                     return true;
                 }
                 else return  false;
             }
             else return false;

        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
        }finally {
//            Connector.getInstance().closeConnection();
        }

        return false;
    }


}