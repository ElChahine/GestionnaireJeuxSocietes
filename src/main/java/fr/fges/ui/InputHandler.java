package fr.fges.ui;

import java.util.Scanner;

/**
 * Gère les saisies clavier.
 * Utilise un seul Scanner pour toute l'application.
 */
public class InputHandler {
    private static final Scanner SCANNER = new Scanner(System.in);

    public String askString(String prompt) {
        System.out.printf("%s: ", prompt);
        return SCANNER.nextLine();
    }

    public int askInt(String prompt) {
        // Boucle tant que l'utilisateur ne rentre pas un entier valide
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