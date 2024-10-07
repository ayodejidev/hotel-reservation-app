package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    // Static reference to ensure only one instance of CustomerService
    private static CustomerService customerService = null;

    // Map to store customers using their email as the key
    private Map<String, Customer> customerMap;

    // Private constructor to implement Singleton pattern
    private CustomerService() {
        this.customerMap = new HashMap<>();
    }

    // Static method to get the single instance of CustomerService
    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    // Method to add a customer
    public void addCustomer(String email, String firstName, String lastName) {
        // Create a new customer object and add it to the customer map
        Customer customer = new Customer(firstName, lastName, email);
        customerMap.put(email, customer);
    }

    // Method to get a customer by email
    public Customer getCustomer(String customerEmail) {
        return customerMap.get(customerEmail);
    }

    // Method to get all customers
    public Collection<Customer> getAllCustomers() {
        return customerMap.values();
    }

}
