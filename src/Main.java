import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Bike class represents the concept of a bike with its attributes and methods
class Bike {
    private String bikeId;          // Encapsulation: Private attribute for bike ID
    private String brand;          // Encapsulation: Private attribute for bike brand
    private String model;          // Encapsulation: Private attribute for bike model
    private String bikeColor;      // Encapsulation: Private attribute for bike color
    private double basePricePerDay; // Encapsulation: Private attribute for base rental price per day (in dollars)
    private boolean isAvailable;    // Encapsulation: Private attribute to check if the bike is available for rent

    // Constructor initializes the Bike object with its attributes
    public Bike(String bikeId, String brand, String model, String bikeColor, double basePricePerDay) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.bikeColor = bikeColor;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true; // New bikes are available by default
    }

    // Getter for bikeId
    public String getBikeId() {
        return bikeId;
    }

    // Getter for brand
    public String getBrand() {
        return brand;
    }

    // Getter for model
    public String getModel() {
        return model;
    }

    // Getter for bikeColor
    public String getBikeColor() {
        return bikeColor;
    }

    // Method to calculate the rental price based on the number of rental days
    // Calculation: basePricePerDay * rentalDays
    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    // Getter to check if the bike is available for rent
    public boolean isAvailable() {
        return isAvailable;
    }

    // Method to mark the bike as rented (not available)
    public void rent() {
        isAvailable = false;
    }

    // Method to mark the bike as returned (available)
    public void returnBike() {
        isAvailable = true;
    }
}

// Customer class represents the concept of a customer with its attributes and methods
class Customer {
    private String customerId;  // Encapsulation: Private attribute for customer ID
    private String name;        // Encapsulation: Private attribute for customer name

    // Constructor initializes the Customer object with its attributes
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    // Getter for customerId
    public String getCustomerId() {
        return customerId;
    }

    // Getter for name
    public String getName() {
        return name;
    }
}

// Rental class represents the concept of a rental transaction
class Rental {
    private Bike bike;          // Association: Rental has a reference to Bike
    private Customer customer; // Association: Rental has a reference to Customer
    private int days;          // Encapsulation: Private attribute for rental duration in days

    // Constructor initializes the Rental object with its attributes
    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    // Getter for bike
    public Bike getBike() {
        return bike;
    }

    // Getter for customer
    public Customer getCustomer() {
        return customer;
    }

    // Getter for days
    public int getDays() {
        return days;
    }
}

// BikeRentalSystem class manages the overall bike rental operations
class BikeRentalSystem {
    private List<Bike> bikes;       // Encapsulation: Private list to store bikes
    private List<Customer> customers; // Encapsulation: Private list to store customers
    private List<Rental> rentals;   // Encapsulation: Private list to store rental transactions

    // Constructor initializes the BikeRentalSystem object and its lists
    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    // Method to add a bike to the system
    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    // Method to add a customer to the system
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Method to rent a bike to a customer
    public void rentBike(Bike bike, Customer customer, int days) {
        if (bike.isAvailable()) { // Check if the bike is available
            bike.rent(); // Mark the bike as rented
            rentals.add(new Rental(bike, customer, days)); // Add the rental record to the list
            System.out.println("Bike rented successfully.");
        } else {
            System.out.println("Bike is not available for rent.");
        }
    }

    // Method to return a bike to the system
    public void returnBike(Bike bike) {
        Rental rentalToRemove = null;
        // Find the rental record associated with the bike to be returned
        for (Rental rental : rentals) {
            if (rental.getBike() == bike) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) { // If rental record is found
            rentals.remove(rentalToRemove); // Remove the rental record from the list
            bike.returnBike(); // Mark the bike as returned
            System.out.println("Bike returned successfully.");
        } else {
            System.out.println("Bike was not rented.");
        }
    }

    // Method to display the menu and handle user interactions
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Bike Rental System =====");
            System.out.println("1. Rent a Bike");
            System.out.println("2. Return a Bike");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) { // Option to rent a bike
                System.out.println("\n== Rent a Bike ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Bikes:");
                // List available bikes for the user to choose
                for (Bike bike : bikes) {
                    if (bike.isAvailable()) {
                        System.out.println(bike.getBikeId() + " - " + bike.getBrand() + " " + bike.getModel() + " ("
                                + bike.getBikeColor() + ")");
                    }
                }

                System.out.print("\nEnter the bike ID you want to rent: ");
                String bikeId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Create a new customer object and add it to the system
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Bike selectedBike = null;
                // Find the selected bike based on ID and availability
                for (Bike bike : bikes) {
                    if (bike.getBikeId().equals(bikeId) && bike.isAvailable()) {
                        selectedBike = bike;
                        break;
                    }
                }

                if (selectedBike != null) {
                    // Calculate the total rental price based on rental days
                    double totalPrice = selectedBike.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Bike: " + selectedBike.getBrand() + " " + selectedBike.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    // Print the total rental price in dollars
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentBike(selectedBike, newCustomer, rentalDays);
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid bike selection or bike not available for rent.");
                }
            } else if (choice == 2) { // Option to return a bike
                System.out.println("\n== Return a Bike ==\n");
                System.out.print("Enter the bike ID you want to return: ");
                String bikeId = scanner.nextLine();

                Bike bikeToReturn = null;
                // Find the bike to be returned based on ID and availability
                for (Bike bike : bikes) {
                    if (bike.getBikeId().equals(bikeId) && !bike.isAvailable()) {
                        bikeToReturn = bike;
                        break;
                    }
                }

                if (bikeToReturn != null) { // If bike to return is found
                    returnBike(bikeToReturn);
                } else {
                    System.out.println("Invalid bike ID or bike is not rented.");
                }
            } else if (choice == 3) { // Exit option
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Bike Rental System!");
    }
}

// Main class to start the application and initialize the system
public class Main {
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();

        // Create and add bikes to the rental system
        Bike bike1 = new Bike("B001", "Yamaha", "YZF-R3", "Blue", 50.0);
        Bike bike2 = new Bike("B002", "Kawasaki", "Ninja 300", "Green", 55.0);
        Bike bike3 = new Bike("B003", "Ducati", "Monster 821", "Red", 80.0);
       

        rentalSystem.addBike(bike1);
        rentalSystem.addBike(bike2);
        rentalSystem.addBike(bike3);

        // Start the menu for user interaction
        rentalSystem.menu();
    }
}
