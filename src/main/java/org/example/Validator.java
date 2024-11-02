package org.example;

public class Validator {

    public static boolean isValidFullName(String fullName) {
        if (fullName.length() < 6 || fullName.length() > 50) {
            System.out.println("Full Name must be between 6 and 50 characters.");
            return false;
        }

        if (fullName.trim().isEmpty()) {
            System.out.println("Full Name cannot contain only spaces.");
            return false;
        }

        if (fullName.matches("[^\\w\\s]+")) {
            System.out.println("Full Name cannot contain only special characters.");
            return false;
        }

        if (fullName.matches("[0-9]+")) {
            System.out.println("Full Name cannot contain only numeric characters.");
            return false;
        }

        if (!fullName.matches(".*[a-zA-Z].*") || !fullName.matches(".*\\d.*")) {
            System.out.println("Full Name must contain both letters and numeric characters.");
            return false;
        }

        if (!fullName.matches(".*[a-zA-Z].*") || !fullName.matches(".*[!@#$%^&*()_+].*")) {
            System.out.println("Full Name must contain both letters and special characters.");
            return false;
        }

        return true;
    }
}
