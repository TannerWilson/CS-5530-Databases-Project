package com.gradeycullins;

import java.util.IntSummaryStatistics;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Connector conn = new Connector();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connection to db was successful.");
            while (true) {
                Object input = scanner.next();
                try {
                    input = Integer.parseInt((String) input);
                } catch (NumberFormatException e) {}

                if (input.equals(0)) {
                    System.out.print(0);
                } else if (input.equals(1)) {
                    System.out.println(1);
                } else if (input.equals(2)) {
                    System.out.println(2);
                } else if (input.toString().equals("exit")) {
                    System.out.println("exiting . . .");
                    System.exit(0);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
