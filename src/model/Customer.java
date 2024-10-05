package model;

import java.util.regex.Pattern;
public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format! see example 'name@domain.extension'.");
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Override the toString() method for better description
    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName + ", Email: " + email;
    }

}
