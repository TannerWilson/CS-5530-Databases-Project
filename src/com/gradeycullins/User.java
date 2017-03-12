package com.gradeycullins;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

/**
 * Created by Tanner on 3/11/2017.
 */
public class User {
    Connector connection;

    public User(Connector con)
    {
        connection = con;
    }

    public boolean register(String firstName, String lastName, String middleName, String gender,
                         String address, String login, String password)
    {
        String insert = "INSERT INTO `5530db58`.`user` (`login`, `password`, `first_name`, `middle_name`, `last_name`, `gender`, `address`) " +
                "VALUES ('"+login+"', '"+password+"', '"+firstName+"', '"+middleName+"', '"+lastName+"', '"+gender+"', '" +address+"');";
        try{
            connection.stmt.execute(insert);
            return true;
        } catch(SQLException e) {
            System.out.print("Insert failed");
            e.printStackTrace();
            return false;
        }

    }

    public boolean login(String login, String password)
    {
        //String query = "select password from User where login='"+login+"';";

        String query = "select password from 5530db58.user where login='"+login+"';";

        ResultSet results;
        try{
            results = connection.stmt.executeQuery(query);
            while (results.next())
            {
                String foundPass = results.getString("password");
                if(foundPass.equals(password))
                    return true;
                else return  false;
            }

        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
        }



        return false;
    }


}
