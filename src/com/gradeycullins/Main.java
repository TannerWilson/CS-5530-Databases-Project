package com.gradeycullins;

import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
                        thManager.user = user;
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
                        thManager.user = user;
                    }
                }
            } else { // user is authenticated
                System.out.println("1) Search properties\n2) Add property\n3) Show my listed properties\n4) List users" +
                        "\n5) Show my reservations");
                if(user.login.equals("admin"))
                    System.out.println("6) Administrative activities");
                Object input = scanner.next();

                try {
                    input = Integer.parseInt((String) input);
                } catch (NumberFormatException e) {
                    // input was not a number - ignore
                }

                if (input.equals(1)) { // filter selection of th

                    /* Loop for the reservation/feedback/favorite menu.
                       Users can add multiple reservations at once.
                       Break loop and commit data changes after user confirmation
                     */

                    while (true) {
                        System.out.println("Property Search:");
                        System.out.println("Enter a blank line to search for a property or enter \"check out\" to finish and confirm your reservations.");
                        String userIn = scanner.next();

                        if (!userIn.equals("check out")) { // User wants to search
                            System.out.println("Enter a blank line for no filters. Enter \"filter\" to apply filters.");
                            String filterChoice = scanner.next();
                            int minPrice, maxPrice;
                            String owner, name, city, state, category;
                            List<String> keywords;
                            if (filterChoice.toLowerCase().equals("filter")) {
                                System.out.println("Enter filtering values. Skip the filter by entering a blank line.");
                                System.out.println("minimum price");
                                String minP = scanner.next();
                                minPrice = ((minP.isEmpty()) ? -1 : Integer.parseInt(minP));
                                System.out.println("maximum price");
                                String maxP = scanner.next();
                                maxPrice = ((maxP.isEmpty()) ? -1 : Integer.parseInt(maxP));
                                System.out.println("owner");
                                owner = scanner.next();
                                System.out.println("name");
                                name = scanner.next();
                                System.out.println("city");
                                city = scanner.next();
                                System.out.println("state");
                                state = scanner.next();
                                System.out.println("enter keywords separated by a space");
                                keywords = new LinkedList<>();
                                String words = scanner.next();
                                if (!words.isEmpty())
                                    keywords.addAll(Arrays.asList(words.split(" ")));
                                System.out.println("category");
                                category = scanner.next();

                                System.out.println("choose an order:");
                                System.out.println("" +
                                        "1) descending price\n" +
                                        "2) ascending price\n" +
                                        "3) descending average feedback rating\n" +
                                        "4) ascending average feedback rating\n" +
                                        "5) descending average feedback rating by trusted users\n" +
                                        "6) ascending average feedback rating by trusted users");
                                input = scanner.next();
                            } else {
                                minPrice = maxPrice = -1;
                                owner = name = city = state = category = "";
                                keywords = new LinkedList<>();
                                input = "1";
                            }

                            thManager.getTh(minPrice, maxPrice, owner, name, city, state, keywords, category, Integer.valueOf((String) input));

                            System.out.format("%s\t|%20s\t|%20s\t|%20s\t|%50s\t|%20s %n", "tid", "name", "owner",
                                    "lowest price", "address", "average score");

                            if (thManager.properties.isEmpty()) {
                                System.out.println("No housing exists that matches your query");
                            } else {
                                for (Integer i : thManager.order) {
                                    Th currentTh = thManager.properties.get(i);
                                    int lowestPrice = currentTh.lowestPrice;

                                    System.out.format("%d\t|%20s\t|%20s\t|%20d\t|%50s\t|%20f\n", currentTh.tid, currentTh.name, currentTh.owner,
                                            lowestPrice, currentTh.address, currentTh.averageScore);
                                }
                            }

                            int thChosen = loopForIntInput();
                            Th selected = thManager.properties.get(thChosen);

                            System.out.println("You Selected: " + selected.name);
                            System.out.println("1) Make reservation\n2) Leave Feedback\n3) Mark property as favorite\n4) View feedback for this property");
                            int in = loopForIntInput();
                            if (in == 1) { // Reservation menu
                                System.out.println("Enter the number next to the period you wish to check availability.");
                                System.out.println("Enter 0 to go back to property search\n");
                                System.out.println("Here are the available periods for this property:");
                                selected.getAvailPeriods();

                                for (int i = 0; i < selected.periods.size(); i++) {
                                    Period p = selected.periods.get(i);
                                    System.out.println((i + 1) + " Arrive: " + p.formatFrom() + ", Depart: " + p.formatTo() +
                                            ", Price per night: $" + p.price);
                                }
                                int periodChoice = loopForIntInput();

                                if (periodChoice == 0) // Go back to search menu
                                    break;

                                // Make reservation from selected period
                                Period selectedPeriod = selected.periods.get(periodChoice - 1);

                                System.out.println("Select available period: " + selectedPeriod.sdf.format(selectedPeriod.from)+
                                        " and "+ selectedPeriod.sdf.format(selectedPeriod.to) +"\nEnter dates within this range.");
                                System.out.println("Enter your desired checkin date. Format: YYYY-MM-DD-HH");
                                Date from = getInputDate(scanner.next());
                                System.out.println("Enter your desired check out date. Format: YYYY-MM-DD-HH");
                                Date to = getInputDate(scanner.next());

                                Reservation res = new Reservation(user.login, selected.tid, selectedPeriod.pid,
                                        from, to, selectedPeriod.price, selected.name);

                                if(selectedPeriod.checkReservations(res)){
                                    user.pendingReservations.add(res); // Add to cart
                                    System.out.println("A reservation for this available period has been created and added to you cart.\n" +
                                            "Continue browsing if you wish to record more reservations or visits.\n");
                                }else{
                                    System.out.println("Sorry there is a reservation on this period that collides with your" +
                                            " check in or check out date. Please try again.");
                                }


                            } else if (in == 2) { // Record Feedback
                                System.out.print("score [0-10]\n");
                                int score = loopForIntInput();
                                System.out.print("description\n");
                                String description = scanner.next();

                                Feedback newFeedback = new Feedback(user.login, score, description, 0, thChosen);
                                newFeedback.insert();
                            } else if (in == 3) { // Make property favorite
                                user.setFavorite(selected);
                                System.out.println(selected.name + " is now your favorite!");
                                System.out.println("You will be taken back to the property search screen.");
                            } else if (in == 4) {
                                Map<Integer, Feedback> feedbacks = new LinkedHashMap<>();
                                System.out.print("Enter a number n for the top n most useful feedbacks or enter a blank line to see all feedbacks\n");
                                String feedbackChoice = scanner.next();
                                try {
                                    int n = Integer.parseInt(feedbackChoice);
                                    System.out.format("%s\t|%20s\t|%20s\t|%50s\t|%20s %n",
                                            "fid", "author", "score", "description", "average usefulness");
                                    feedbacks = Feedback.getNMostUsefulFeedbacks(thChosen, n);
                                    for (Feedback f : feedbacks.values()) {
                                        System.out.format("%d\t|%20s\t|%20d\t|%50s\t|%20f %n",
                                                f.fid, f.login, f.score, f.description, f.averageUsefulness);
                                    }

                                } catch (NumberFormatException e) { // user wants all feedbacks
                                    feedbacks = Feedback.getThFeedback(thManager.properties.get(thChosen));
                                    System.out.format("%s\t|%20s\t|%20s\t|%50s\t %n",
                                            "fid", "author", "score", "description");
                                    for (Feedback f : feedbacks.values()) {
                                        System.out.format("%d\t|%20s\t|%20d\t|%50s\t %n",
                                                f.fid, f.login, f.score, f.description);
                                    }

                                } finally {
                                    int selectedFeedback = loopForIntInput();
                                    Feedback f = feedbacks.get(selectedFeedback);
                                    System.out.print("You selected:\n");
                                    System.out.format("%d\t|%20s\t|%20d\t|%50s\t %n",
                                            f.fid, f.login, f.score, f.description);
                                    System.out.print("Mark feedback as:\n0) useless\n1) useful\n2) very useful\n");
                                    int usefulRating = loopForIntInput();
                                    FeedbackRating.insertFeedbackRating(user.login, selectedFeedback, usefulRating);
                                }
                            }
                        } else { // User is finished making reservations
                            user.commitReservations();
                        }
                    }
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
                    System.out.println("Enter the property's URL.");
                    String url = scanner.next();

                    // Loop to account for user error
                    System.out.println("What year was it built?");
                    int year = loopForIntInput();

                    // Make new Th object for insertion
                    Th newTh = new Th(user.login, name, type, phoneNumber, address, url, year);

                    if (newTh.insert()) {
                        System.out.println("New property registered to your account.");
                    } else {
                        System.out.println("Something went wrong. Unable to register property.");
                    }
                } else if (input.equals(3)) { // Show user's properties
                    thManager.getUserProperties(user.login);
                    System.out.println("Enter the number next to the property you wish to access.");
                    System.out.println("Properties you own:");

                    for (Th th : thManager.properties.values()) {
                        System.out.println(th.tid + "\t" + th.name);
                    }

                    int input1 = loopForIntInput();
                    Th selected = thManager.properties.get(input1);
                    System.out.println("You selected: " + selected.name);
                    System.out.println("1) Edit property info\n2) Add available period\n3) View reservations\n4) View visits");
                    int input2 = loopForIntInput();

                    if (input2 == 1) {// Edit property info
                        System.out.println("1) Edit name\n2) Edit property category\n3) Edit address\n Edit Phone Number");
                        int input3 = loopForIntInput();
                        if (input3 == 1) {
                            System.out.println("Enter new property name.");
                            String newName = scanner.next();
                            selected.updateField("name", newName, selected.tid);
                        } else if (input3 == 2) {
                            System.out.println("Enter new property category.");
                            String newType = scanner.next();
                            selected.updateField("category", newType, selected.tid);
                        } else if (input3 == 3) {
                            System.out.println("Enter new address.");
                            String newAddress = scanner.next();
                            selected.updateField("address", newAddress, selected.tid);
                        } else if (input3 == 4) {
                            System.out.println("Enter new phone number.");
                            String newNum = scanner.next();
                            selected.updateField("phone_num", newNum, selected.tid);
                        }
                    } else if (input2 == 2) { // Add new period to selected property
                        System.out.println("Enter start date. Format: YYYY-MM-DD-HH");
                        Date from = getInputDate(scanner.next());
                        System.out.println("Enter end date. Format: YYYY-MM-DD-HH");
                        Date to = getInputDate(scanner.next());
                        System.out.println("Enter price per-night.");
                        int price = scanner.nextInt();

                        Period newPeriod = new Period(selected.tid, from, to, price);
                        newPeriod.insert(); // Insert new period into database

                    } else if (input2 == 3) { // View reservations of selected TH
                        // TODO Pull reservations to this TH

                    } else if (input2 == 3) { // View Stays of selected TH
                        // TODO Pull the visits on TH
                    }

                } else if (input.equals(4)) { // List other users
                    System.out.println("Enter index to rate user.");
                    System.out.format("%s\t|%20s\t|%20s\t|%20s\t|%20s\t|%20s\t|%20s %n",
                            "uid", "login", "first name", "middle name", "last name", "gender", "favorite th");

                    UserManager userMan = new UserManager();
                    ArrayList<User> users = userMan.getAllUsers();

                    int index = 0;
                    for (User use : users) {
                        System.out.format("%d\t|%20s\t|%20s\t|%20s\t|%20s\t|%20s\t|%20s %n",
                                index, use.login, use.firstName, use.middleName, use.lastName, use.gender, use.favorite);
                        index++;
                    }
                    int choice = loopForIntInput();

                    // Ensure the number entered is in the range
                    if (choice >= 0 && choice < index) {
                        // Get user chosen
                        User selected = userMan.users.get(choice);
                        System.out.println("User selected:");
                        System.out.format("%d\t|%20s\t|%20s\t|%20s\t|%20s\t|%20s\t|%20s %n",
                                index, selected.login, selected.firstName, selected.middleName, selected.lastName, selected.gender, selected.favorite);
                        System.out.println("\n0) Mark as un-trusted\n1) Mark as trusted");

                        int choice2 = loopForIntInput(); // Get entry

                        // insert a new trust relationship
                        Trust.addTrustRelationship(user, selected, choice2);
                    } else {
                        System.out.print("Sorry, that's not a valid entry.");
                    }

                } else if (input.equals(5)) {
                    System.out.println("Enter the number of the reservation to record a visit for that property.\n" +
                            "When you're finished press 0 to confirm your visits.");
                    System.out.println("Your current and past reservations:");

                    user.getReservations(); // Pull all reservations this user has made

                    for (int i = 0; i < user.currentReservations.size(); i++) {
                        Reservation r = user.currentReservations.get(i);
                        System.out.println((i + 1) + "\tProperty: " + r.houseName + ", Check in: " + r.from.toString() +
                                ", Check out: " + r.to.toString() + ", Total cost: " + r.cost);
                    }

                    // Loop to allow user to enter multiple visits
                    while (true) {
                        int resChoice = loopForIntInput();

                        if (resChoice == 0) { // Confirm visits
                            user.commitVisits();
                            break;
                        }
                        Reservation selected = user.currentReservations.get(resChoice - 1);
                        Visit visit = new Visit(user.login, selected.tid, selected.pid, selected.from, selected.to);
                        user.pendingVisits.add(visit);
                        System.out.println("Visit at " + selected.houseName + " during the selected reservation has been\n" +
                                "added to your cart. Select another reservation to record a visit or enter 0 to confirm and exit.");
                    }
                }
                else if (input.equals(6)) {
                    UserManager manager = new UserManager();
                    System.out.println("1) Find most trusted users\n2) Find most useful users");
                    int choice = loopForIntInput();

                    if(choice == 1){
                        System.out.println("How many users do you want to limit the search to?");
                        int n = loopForIntInput();

                        ArrayList<String> topTrusted =  manager.getMostTrustedUsers(n);

                        System.out.println("Enter the number next to the user to award them");
                        for(String login : topTrusted)
                            System.out.println(login);


                    }
                    else if(choice == 2){
                        System.out.println("How many users do you want to limit the search to?");
                        int n = loopForIntInput();


                    }
                }
            }
        }
    }

    /**
     * Loops to ensure the user entered a number.
     *
     * @return number entered
     */
    public static int loopForIntInput() {
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
     * Formats the user string into desired format and returns a date
     */
    public static Date getInputDate(String input) {
        String[] entries = input.split("-");
        int year = Integer.parseInt(entries[0]);
        int month = Integer.parseInt(entries[1]);
        int day = Integer.parseInt(entries[2]);
        int hour = Integer.parseInt(entries[3]);
        Date date = new Date(year, month, day, hour, 0, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(date);
        return date;
    }

}
