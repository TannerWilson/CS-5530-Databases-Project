package com.gradeycullins;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean loggedIn = false;
        try {
            Connector conn = new Connector();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connection to db was successful.");
            System.out.println("Welcome to Uotel\n" +
                    "1) Login, 2) Register, exit) quit application");
            while (true) {
                if(loggedIn)
                {

                }else {
                    Object input = scanner.next();
                    try {
                        input = Integer.parseInt((String) input);
                    } catch (NumberFormatException e) {
                    }

                    // user object to run queries
                    user user = new user(conn);

                    if (input.equals(1)) {
                        System.out.println("Enter your login");
                        String login = scanner.next();
                        System.out.println("Enter your password");
                        String password = scanner.next();

                        if (!user.login(login, password))
                            System.out.print("Login failed. Incorrect username or password ");
                        else {
                            System.out.print("Welcome " + login);
                            loggedIn = true;
                        }


                    } else if (input.equals(2)) {
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
                        System.out.println("Choose a login");
                        String login = scanner.next();
                        System.out.println("Choose a password");
                        String password = scanner.next();
                        user.register(firstName, lastName, middleName, gender, address, login, password);
                    } else if (input.toString().equals("exit")) {
                        System.out.println("exiting . . .");
                        System.exit(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
