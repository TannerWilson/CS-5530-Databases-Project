package com.gradeycullins;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean isAuthenticated = false;
        boolean isMenuScreen = false;
        String currentUser = "";


        try {
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("\n");
            System.out.println("Welcome to Uotel\n1) Login, 2) Register, exit) quit application");
            while (true) {
                // User just logged in
                if(isAuthenticated && isMenuScreen) {
                    afterLoginMenu();

                    Object input = scanner.next();
                    try {
                        input = Integer.parseInt((String) input);
                    } catch (NumberFormatException e) {
                        // input was not a number - ignore
                    }

                    if (input.equals(1)) {

                    }else if(input.equals(2)) {
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
                        int year;
                        while(true)
                        {
                            System.out.println("What year was it built?");
                            Object in = scanner.next();
                            try {
                                year = Integer.parseInt((String) in);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Sorry the year must be a number");
                            }
                        }

                        // Make new TH object for insertion
                        TH newTH = new TH(currentUser, name,type,phoneNumber,address, year);
                        newTH.insert();

                    } else if(input.equals(3)) {

                    }else {
                        System.out.print("Sorry, that's not a valid entry");
                    }


                } else { // Log in or Register

                    Object input = scanner.next();
                    try {
                        input = Integer.parseInt((String) input);
                    } catch (NumberFormatException e) {
                        // input was not a number - ignore
                    }

                    if (input.equals(1)) {
                        System.out.println("Enter your login");
                        String login = scanner.next();
                        System.out.println("Enter your password");
                        String password = scanner.next();

                        User tempUser = new User(login, password);

                        if (!tempUser.login(login, password))
                            System.out.println("Login failed. Incorrect username or password");
                        else {
                            System.out.println("Welcome " + login);
                            isAuthenticated = true;
                            currentUser = login; // Record user that is currently logged in
                            isMenuScreen = true;
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

                        if (newUser.register(firstName, lastName, middleName, gender, address, login, password)) {
                            System.out.println("You have successfully created an account and are now logged in!");
                            isAuthenticated = true;
                            currentUser = login; // Record user that is currently logged in
                            isMenuScreen = true;
                        } else {
                            System.out.println("Something went wrong when trying to create your account. . .");
                        }

                    }
                }

                // User can exit at any time
                Object input = scanner.next();
                if (input.toString().toLowerCase().equals("exit")) {
                    System.out.println("exiting . . .");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void afterLoginMenu()
    {
        System.out.println("1) Browse properties, 2) Register new property, 3) My properties");
    }
}
