# Hotel Reservation Application

This is a **Java-based** Hotel Reservation System that allows users to search for available rooms, book reservations, and manage customer accounts. Administrators can manage rooms, customers, and reservations. The project is designed to showcase object-oriented programming principles, including inheritance, polymorphism, and encapsulation.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [Usage](#usage)
- [Admin Features](#admin-features)
- [Customer Features](#customer-features)

## Features

### Admin Features
- Manage hotel rooms (Add, view, and manage both free and paid rooms).
- View all customer accounts.
- View all reservations.

### Customer Features
- Search for available rooms by date.
- Filter rooms by paid or free options.
- Create a new account.
- Make reservations.
- View existing reservations.

### Project Design
- Implements Object-Oriented Programming (OOP) principles, such as inheritance, polymorphism, and encapsulation.
- Uses the **Singleton pattern** for managing the system's services (like `CustomerService`, `ReservationService`).
- Validates user input with Regular Expressions (e.g., valid email format).
- Handles date input using `SimpleDateFormat` and provides robust error handling with `try-catch` blocks.

## Technologies Used

- **Java**: The core programming language for building the application.
- **Java Collections**: Used to manage hotel rooms, customers, and reservations.
- **Date and Calendar APIs**: Used for managing check-in and check-out dates.
- **Object-Oriented Programming**: Core design principles are applied throughout the project.

## Setup

### Prerequisites
- **Java 8+**: You need a Java Development Kit (JDK) installed on your machine.
- **Git**: If you want to clone the repository from GitHub.

## Compile and Run
1. Open the project in your preferred IDE (such as IntelliJ IDEA or Eclipse).
2. Navigate to the HotelApplication class, which contains the main method.
3. Run the HotelApplication class to start the program.

Alternatively, if using the terminal:

```bash
javac -d bin src/*.java
java -cp bin HotelApplication
```

## Usage
Once the application is running, you'll be prompted with a main menu offering two user types: Admin and Customer.

### Customer Features
1. Find and Reserve a Room: Search for available rooms between two dates and filter rooms by paid or free options.
2. See My Reservations: Enter your email to view all of your current reservations.
3. Create an Account: Register with your email, first name, and last name to create a new customer account.

## Admin Features
1. See All Customers: View a list of all customer accounts.
2. See All Rooms: View a list of all available rooms, including paid and free rooms.
3. See All Reservations: View all the reservations made by customers. 
4. Add a Room: Add a new room to the hotel, specifying the room number, price, and type (single or double occupancy).
