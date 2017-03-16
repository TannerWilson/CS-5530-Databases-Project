package com.gradeycullins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
    ArrayList<Reservation> pendingReservations = new ArrayList<>();
    ArrayList<Visit> pendingVisits = new ArrayList<>();
    ArrayList<Reservation> currentReservations = new ArrayList<>();

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

    /**
     * Populate a user's fields as a newly auth'd profile
     * @param resultSet
     */
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

        String getTrustValue = "SELECT is_trusted FROM 5530db58.user WHERE login='"+login+"';";

        ResultSet results;

        // Get this user's trust value
        int trustVal = 0;
        try {
            results = Connector.getInstance().statement.executeQuery(getTrustValue);

            if (results.next()) { // Step to result
                trustVal = results.getInt("is_trusted");
            }

            if(increment)
                trustVal++;
            else
                trustVal--;

        }catch (SQLException e){
            System.err.println("Unable to execute query:" + getTrustValue + "\n");
            System.err.println(e.getMessage());
        }


        String update = "UPDATE `5530db58`.`user` SET `is_trusted`='"+trustVal+"' WHERE `login`='"+login+"';";

        // Insert new value back to database
        try{
            int result = Connector.getInstance().statement.executeUpdate(update);
            return true;
        }catch (SQLException e){
            System.err.println("Unable to execute query:" + getTrustValue + "\n");
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Prints out the users current pending reservations
     * and allows them to confirm or deny the reservations
     * before committing them to the database.
     */
    public void commitReservations() {
        Scanner scan = new Scanner(System.in);

        // User has no reservations. Return
        if(pendingReservations.size() == 0){
            System.out.println("You have no reservations to confirm.");
            return;
        }

        System.out.println("Enter the number next to a reservation to remove it from your cart.\n" +
                "0) to confirm\n");

        while(true) {
            // Print pending reservations
            for (int i = 0; i < pendingReservations.size(); i++) {
                Reservation r = pendingReservations.get(i);
                System.out.println((i + 1) + "\tProperty: " + r.houseName + ", Check in: " + r.from.toString() +
                        ", Check out: " + r.to.toString() + ", Total cost: " + r.cost);
            }

            int choice = loopForIntInput();
            if (choice == 0) { // Insert all reservations
                for(Reservation res : pendingReservations)
                    res.insert();
                System.out.println("Your reservations have been confirmed.");
                pendingReservations.clear();
                // TODO: remove all available periods that coinside with these reservations
                break;
            }
            else { // Remove selected reservation and continue loop
                pendingReservations.remove(choice - 1);
                System.out.println("Reservation removed. Current reservations:");
            }
        }
    }

    /**
     * Prints out the users current pending visits and allows them to
     * confirm or deny them before committing to the database.
     */
    public void commitVisits() {
        Scanner scan = new Scanner(System.in);

        // User has no reservations. Return
        if(pendingVisits.size() == 0){
            System.out.println("You have no visits to confirm.");
            return;
        }

        System.out.println("Enter the number next to a visit to remove it from your cart.\n" +
                "0) to confirm\n");

        while(true) {
            // Print pending visits
            for (int i = 0; i < pendingVisits.size(); i++) {
                Visit v = pendingVisits.get(i);
                System.out.println((i + 1) + "\tCheck in: " + v.from.toString() +
                        ", Check out: " + v.to.toString());
            }

            int choice = loopForIntInput();
            if (choice == 0) { // Insert all reservations
                for(Visit visit : pendingVisits)
                    visit.insert();
                System.out.println("Your visits have been confirmed.");
                pendingVisits.clear();
                break;
            }
            else { // Remove selected reservation and continue loop
                pendingVisits.remove(choice - 1);
                System.out.println("Visit removed. Current visits:");
            }
        }
    }

    /**
     * Loops to ensure the user entered a number.
     * @return number entered
     */
    public static int loopForIntInput(){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        int choice;
        while (true) {
            Object in = scanner.next();
            try {
                choice = Integer.parseInt((String) in);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Sorry your choice must be a number.");
            }
        }
        return choice;
    }

    /**
     * Get all the periods associated with this th
     * @return
     */
    public void getReservations() {
        String query = "SELECT * FROM `5530db58`.`reservation` WHERE login='"+login+"';";

        ResultSet resultSet;

        try {
            resultSet = Connector.getInstance().statement.executeQuery(query);

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                int tid = resultSet.getInt("tid");
                int pid = resultSet.getInt("pid");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                Date fromDate = Period.sdf.parse(from);
                Date toDate = Period.sdf.parse(to);
                float cost = resultSet.getFloat("cost");

                Reservation r = new Reservation(login, pid, tid, fromDate, toDate, cost);
                currentReservations.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Could not retrieve reservations.");
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println("Could not parse date.");
        }

        // Populate house names in reservations we just grabbed for UI convience
        for(Reservation r : currentReservations) {
            String nameQuery = "SELECT name FROM `5530db58`.`th` WHERE tid="+r.tid+";";

            try{
                resultSet = Connector.getInstance().statement.executeQuery(nameQuery);
                if(resultSet.next()){
                    r.houseName = resultSet.getString("name");
                }
            }catch(SQLException e){}
        }
    }

    /**
     * Update this user's favorite
     */
    public void setFavorite(Th th){
        String update = "UPDATE `5530db58`.`user` SET `favorite`='"+th.name+"' WHERE `login`='"+login+"';";

        try{
            int result = Connector.getInstance().statement.executeUpdate(update);

        }catch (SQLException e){
            System.err.println("Unable to execute update");
            System.err.println(e.getMessage());
        }

    }
}