package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    // Constructor with email validation
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;

        // Validate the email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format. See example --> 'name@domain.extension'.");
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        final String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Override the toString() method for better description
    @Override
    public String toString() {
        return firstName + " " + lastName + ", Email: " + email;
    }

}
