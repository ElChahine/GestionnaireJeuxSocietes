package fr.fges.ui;

import java.util.Scanner;

/**
 * Handles user input using a single Scanner instance.
 * Provides simple methods to ask for String and int values.
 */
public class    InputHandler {
    private static final Scanner SCANNER = new Scanner(System.in);

    public String askString(String prompt) {
        System.out.printf("%s: ", prompt);
        return SCANNER.nextLine();
    }

    public int askInt(String prompt) {
        while (true) {
            System.out.printf("%s: ", prompt);
            String line = SCANNER.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}
