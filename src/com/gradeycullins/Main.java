package com.gradeycullins;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Used to store user's current un-committed changes
    ArrayList<Reservation> pendingReservations = new ArrayList<>();
    ArrayList<Visit> pendingVisits = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        /* application db state */
        User user = new User();
        ThManager thManager = new ThManager();

        while (true) {
            if (!user.isAuthenticated) {
                System.out.println("Welcome to Uotel\n0) Exit\n1) Login\n2) Register");
                Object input = scanner.next();

                try {
                    input = Integer.parseInt((String) input);
                } catch (NumberFormatException e) {
                    // input was not a number - ignore
                }

                if (input.equals(0) || input.toString().toLowerCase().contains("exit")) {
                    System.out.println("exiting . . .");
                    System.exit(0);
                } else if (input.equals(1)) {
                    System.out.println("Enter your login");
                    String login = scanner.next();
                    System.out.println("Enter your password");
                    String password = scanner.next();

                    User tempUser = new User(login, password);

                    if (!tempUser.login(login, password))
                        System.out.println("Login failed. Incorrect username or password");
                    else {
                        user = tempUser;
                        System.out.println("Welcome back, " + user.firstName);
                        user.isAuthenticated = true;
                    }
                } else if (input.equals(2)) {
                    System.out.println("Choose a login");
                    String login = scanner.next();
                    System.out.println("Enter your first name");
                    String firstName = scanner.next();
                    System.out.println("Enter your middle name");
                    String middleName = scanner.next();
                    System.out.println("Enter your last name");
                    String lastName = scanner.next();
                    System.out.println("Enter you gender");
                    String gender = scanner.next();
                    System.out.println("Enter your address");
                    String address = scanner.next();
                    System.out.println("Choose a password");
                    String password = scanner.next();

                    User newUser = new User(login, password, firstName, middleName, lastName, gender, address);

                    if (newUser.register()) {
                        System.out.println("You have successfully created an account and are now logged in!");
                        user = newUser;
                    }
                }
            } else { // user is authenticated
                System.out.println("1) List properties\n2) Add property\n3) Show my listed properties\n4) List users");
                Object input = scanner.next();


                try {
                    input = Integer.parseInt((String) input);
                } catch (NumberFormatException e) {
                    // input was not a number - ignore
                }

                if (input.equals(1)) { // list temporary housing
                    System.out.println("Enter the id of a property to learn more:");
                    for (Th th : thManager.properties.values()) {
                        System.out.println(th.tid + "\t" + th.name);
                    }

                    // query for the th the user wishes to view TODO
                    while (true) {}
                } else if (input.equals(2)) {
                    System.out.println("Lets register you a new property to manage.");
                    System.out.println("Give your house a name.");
                    String name = scanner.next();
                    System.out.println("What type of property is this?");
                    String type = scanner.next();
                    System.out.println("Enter your phone number so other users can contact you.");
                    String phoneNumber = scanner.next();
                    System.out.println("Enter the property's address.");
                    String address = scanner.next();

                    // Loop to account for user error
                    System.out.println("What year was it built?");
                    int year = loopForIntInput();

                    // Make new Th object for insertion
                    Th newTh = new Th(user.login, name, type, phoneNumber, address, year);

                    if (newTh.insert()) {
                        System.out.println("New property registered to your account.");
                    } else {
                        System.out.println("Something went wrong. Unable to register property.");
                    }
                } else if (input.equals(3)) { // Show user's properties

                }else if (input.equals(4)){ // List other users
                    System.out.println("Enter index to rate user.");
                    System.out.println("Index  |   Login    |          Name             |     Gender      |     Trust Rate      |       Favorite TH");
                    UserManager userMan = new UserManager();
                    int index = 0;
                    for (User use : userMan.users) {
                        System.out.println( index + "\t  " + use.login + "\t\t\t\t" + use.firstName + " " + use.middleName
                                + " " + use.lastName + "\t\t\t\t" + use.gender + "\t\t\t\t\t" + use.isTrusted
                                + "\t\t\t\t\t" + use.favorite);
                        index++;
                    }

                    // Loop for user input
                    while(true) {
                        int choice = loopForIntInput();

                        // Ensure the number entered is in the range
                        if (choice >= 0 && choice < index) {
                            // Get user chosen
                            User selected = userMan.users.get(choice);
                            System.out.println("User selected:");
                            System.out.println(selected.login + "\t\t" + selected.firstName + " " + selected.middleName +
                                    " " + selected.lastName + "\t\t" + selected.gender + "\t\t" + selected.isTrusted
                                    + "\t\t" + selected.favorite);
                            System.out.println("1) Mark as trusted, 2) Mark as un-trusted");

                            int choice2 = loopForIntInput(); // Get entry

                            // Update user as marked
                            if(choice2 == 1)
                                selected.updateTrustValue(true);
                            else
                                selected.updateTrustValue(true);
                        } else {
                            System.out.print("Sorry, that's not a valid entry.");
                        }
                    }

                }else {
                    System.out.print("Sorry, that's not a valid entry.");
                }
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

}
