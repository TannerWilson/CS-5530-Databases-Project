package com.gradeycullins;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        /* application db state */
        User user = new User();
        ThManager thManager = new ThManager();
        ArrayList<Reservation> pendingReservations = new ArrayList<>();
        ArrayList<Visit> pendingVisits = new ArrayList<>();

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
                System.out.println("1) Search properties\n2) Add property\n3) Show my listed properties\n4) List users");
                Object input = scanner.next();

                try {
                    input = Integer.parseInt((String) input);
                } catch (NumberFormatException e) {
                    // input was not a number - ignore
                }

                if (input.equals(1)) { // filter selection of th
                    System.out.println("Enter filtering values. Skip the filter by entering a blank line.");
                    System.out.println("minimum price");
                    int minPrice = Integer.parseInt(scanner.next());
                    System.out.println("maximum price");
                    int maxPrice = Integer.parseInt(scanner.next());
                    System.out.println("owner");
                    String owner = scanner.next();
                    System.out.println("name");
                    String name = scanner.next();
                    System.out.println("city");
                    String city = scanner.next();
                    System.out.println("state");
                    String state = scanner.next();
                    System.out.println("enter keywords separated by a space");
                    List<String> keywords = new LinkedList<>();
                    String words = scanner.next();
                    keywords.addAll(Arrays.asList(words.split(" ")));
                    System.out.println("category");
                    String category = scanner.next();

                    thManager.getTh(minPrice, maxPrice, owner, name, city, state, keywords, category);

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
                    thManager.getUserProperties(user.login);
                    System.out.println("Enter the number of the property you wish to access.");
                    System.out.println("Properties you own:");

                    for(Th th : thManager.properties.values()) {
                        System.out.println(th.tid + "\t" + th.name);
                    }
                    int input1 = loopForIntInput();
                    Th selected = thManager.properties.get(input1);
                    System.out.println("You selected: " + selected.name);
                    System.out.println("1) Edit property info\n2) Add available period\n3) View reservations or stays");
                    int input2 = loopForIntInput();

                    if(input2 == 1){// Edit property info
                        System.out.println("1) Edit name\n 2) Edit property type\n3) Edit address");
                        int input3 = loopForIntInput();
                        if(input3 == 1) {
                            System.out.println("Enter new property name.");
                            String newName = scanner.next();
                            selected.updateField("name", newName);
                        } else if(input3 == 2){
                            System.out.println("Enter new property type.");
                            String newType = scanner.next();
                            selected.updateField("type", newType);
                        }
                        else if(input3 == 3){
                            System.out.println("Enter new address.");
                            String newAddress = scanner.next();
                            selected.updateField("name", newAddress);
                        }
                    } else if(input2 == 2){ // Add period
                        System.out.println("Enter start date. Format: YYYY-MM-DD HH:MM:SS");
                        SimpleDateFormat from = new SimpleDateFormat(scanner.next());
                        System.out.println("Enter end date. Format: YYYY-MM-DD HH:MM:SS");
                        SimpleDateFormat to = new SimpleDateFormat(scanner.next());
                        System.out.println("Enter price per-night");
                        int price = scanner.nextInt();

                        Period newPeriod = new Period(selected.tid, from, to, price);
                    } else if(input2 == 3){ // View reservations and stays
                        System.out.println("1) Reservations\n 2) Visits");
                        int input3 = loopForIntInput();
                        if(input3 == 1){ // Pull reservations to this TH TODO

                        }else if(input3 == 2){ // Pull the visits on TH TODO

                        }
                    }



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
                                selected.updateTrustValue(false);
                        } else {
                            System.out.print("Sorry, that's not a valid entry.");
                        }
                    }

                } else {
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
