package model;

public class Tester {
    public static void main(String[] args) {

        Customer customer = new Customer("first", "second", "c@domain.com");
        System.out.println(customer);

        Customer customer1 = new Customer("first", "second", "j@domain.com");
        System.out.println(customer1);

        Customer customer2 = new Customer("first", "second", "a@domain.com");
        System.out.println(customer2);
    }
}
