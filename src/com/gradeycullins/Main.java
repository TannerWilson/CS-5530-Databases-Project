package com.gradeycullins;

import java.util.Scanner;

public class Main {

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
                    } else {
                        System.out.println("Something went wrong when trying to create your account. . .");
                    }
                }
            } else { // user is authenticated
                System.out.println("1) list properties\n2) add property\n3) show my listed properties");
                Object input = scanner.next();

                try {
                    input = Integer.parseInt((String) input);
                } catch (NumberFormatException e) {
                    // input was not a number - ignore
                }

                if (input.equals(1)) {

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
                    int year;
                    while (true) {
                        System.out.println("What year was it built?");
                        Object in = scanner.next();
                        try {
                            year = Integer.parseInt((String) in);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Sorry the year must be a number.");
                        }
                    }

                    // Make new Th object for insertion
                    Th newTh = new Th(user.login, name, type, phoneNumber, address, year);

                    if (newTh.insert()) {
                        System.out.println("New property registered to your account.");
                    } else {
                        System.out.println("Something went wrong register.");
                    }
                } else if (input.equals(3)) {

                } else {
                    System.out.print("Sorry, that's not a valid entry.\n If you're trying to exit please1 try again.");
                }
            }
        }
    }

}
