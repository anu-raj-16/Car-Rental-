// References: TellerApp class in the ui package
// of TellerApp from Phase 1 page on the EdX page
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Car;
import model.CarRental;
import persistance.JsonReader;
import persistance.JsonWriter;

// Car rental ageny app
public class Agency {
    private static final String JSON_STORE = "./data/carRental.json";
    private CarRental agency;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Car rental agency app
    public Agency() {
        runAgency();
    }

    // MODIFIES: this
    // EFFECTS: starts the app, processes user information and
    // checks if app should keep running
    public void runAgency() {
        boolean keepRunning = true;
        String command = null;

        try {
            init();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            keepRunning = false;
        }

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processInput(command);
            }
        }

        System.out.println("\nThank you and hope to see you soon!");
    }

    // EFFECTS: processes user input and performs the corresponding action
    public void processInput(String command) {
        if (command.equals("a")) {
            rentOutACar();
        } else if (command.equals("b")) {
            returnRentedCar();
        } else if (command.equals("c")) {
            addACar();
        } else if (command.equals("d")) {
            removeACar();
        } else if (command.equals("e")) {
            viewAllCars();
        } else if (command.equals("f")) {
            viewAllAvailableCars();
        } else if (command.equals("g")) {
            viewAllRentedCars();
        } else if (command.equals("h")) {
            checkTotalRenvenue();
        } else if (command.equals("i")) {
            numberOfCars();
        } else if (command.equals("j")) {
            numberOfRentedCars();
        } else if (command.equals("s")) {
            saveCarRental();
        } else if (command.equals("l")) {
            loadCarRental();
        } else {
            System.out.println("Invalid input. Please try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes car rental agency
    public void init() throws FileNotFoundException {
        agency = new CarRental();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays the options available to the user
    public void displayMenu() {
        System.out.println("\nSelect from");
        System.out.println("\ta -> to rent a car");
        System.out.println("\tb -> to return a car");
        System.out.println("\tc -> to add a car to the agency");
        System.out.println("\td -> to remove a car from the agency");
        System.out.println("\te -> to view all cars");
        System.out.println("\tf -> to view all available cars");
        System.out.println("\tg -> to view all cars that have been rented out");
        System.out.println("\th -> to check total revenue");
        System.out.println("\ti -> to check the number of cars in the agency");
        System.out.println("\tj -> to check the number of cars rented out");
        System.out.println("\ts -> to save the car rental to file");
        System.out.println("\tl -> to load the car rental from file");
        System.out.println("\tq -> to quit");
    }

    // MODIFIES: this
    // EFFECTS: rents out the car requested by user if user is eligible and the car
    // is available
    public void rentOutACar() {
        if (checkEligible()) {
            System.out.println("Here is a list of all available cars:");
            viewAllAvailableCars();
            System.out.println("Enter the number of the car you wish to rent out:");
            String number = input.next();
            boolean rent = agency.rentACar(number);
            if (rent) {
                System.out.println("You have successfully rented out the car!");
            } else {
                System.out.println("Sorry, the car was not rented out.");
            }
        } else {
            System.out.println("Sorry, you are not eligible to rent a car.");
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the car rented out by user if the car number
    // provided corresponds to a car rented out
    public void returnRentedCar() {
        System.out.println("Please enter the number of the car that you wish to return:");
        System.out.println("Note: please enter a valid number");
        String number = input.next();
        boolean returnCar = agency.returnACar(number);
        if (returnCar) {
            System.out.println("You have successfully returned the car. Thank you for using our services!");
        } else {
            System.out.println("Sorry, the car was not returned. Please ensure you entered the correct car number");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a car to collection owned by the agency if not already owned by
    // agency
    public void addACar() {
        System.out.println("Please enter the number of the car you want to add:");
        String number = input.next();
        System.out.println("Please enter the make of the car:");
        String make = input.next();
        System.out.println("Please enter the model of the car");
        String model = input.next();
        System.out.println("Please enter the bodystyle of the car:");
        String bodystyle = input.next();
        System.out.println("Please enter the color of the car:");
        String color = input.next();
        System.out.println("Please enter the year/ model of the car:");
        int year = input.nextInt();
        Car newCar = new Car(number, make, model, bodystyle, color, year);
        boolean addCar = agency.addCar(newCar);
        if (addCar) {
            System.out.println("Car was added to the system!");
        } else {
            System.out.println("Car is already in the system and hence, was not added.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the car with the given from collection owned by the agency
    // if such a car is owned and not rented out.
    public void removeACar() {
        System.out.println("Please enter the number of the car you wish to remove:");
        String number = input.next();
        boolean removeCar = agency.removeCar(number);
        if (removeCar) {
            System.out.println("The car has been removed from the system!");
        } else {
            System.out.print("Sorry, the car is either not in the system ");
            System.out.println("or has been rented out and cannot be removed.");
        }
    }

    // EFFECTS: returns true if the person is eligible for renting a car; else
    // returns false.
    public boolean checkEligible() {
        System.out.println("Please enter the name of the person:");
        String name = input.next();
        System.out.println("Please enter your age:");
        int age = input.nextInt();
        System.out.println("Please enter your driver's license number:");
        String driversLicense = input.next();
        System.out.println("Please enter your driver's license expiration date in the form yyyy-mm-dd");
        String expirationDate = input.next();
        LocalDate today = LocalDate.now();
        LocalDate expires = LocalDate.parse(expirationDate);

        return (age >= 21 && expires.compareTo(today) > 0);
    }

    // EFFECTS: displays information of all cars owned by the car rental
    // in the order - number, make, model, bodystyle, color, model
    public void viewAllCars() {
        List<Car> cars = agency.getAllCars();
        if (cars.size() > 0) {
            for (Car car : cars) {
                System.out.println("Number:" + car.getNumber() + " Make:" + car.getMake() + " Model:" + car.getModel()
                        + " Bodystyle:" + car.getBodystyle() + " Color:" + car.getCarColor() + " Year:"
                        + car.getYear());
            }
        } else {
            System.out.println("No cars in the agency");
        }
    }

    // EFFECTS: displays information of all cars owned by the car rental that are
    // available for renting in the order - number, make, bodystyle, color, model
    public void viewAllAvailableCars() {
        List<Car> cars = agency.getAllAvailableCars();
        if (cars.size() > 0) {
            for (Car car : cars) {
                System.out.println("Number:" + car.getNumber() + " Make:" + car.getMake() + " Model:" + car.getModel()
                        + " Bodystyle:" + car.getBodystyle() + " Color:" + car.getCarColor() + " Year:"
                        + car.getYear());
            }
        } else {
            System.out.println("No cars available in the agency");
        }
    }

    // EFFECTS: displays information of all cars owned by the car rental that have
    // been rented out in the order - number, make, bodystyle, color, model
    public void viewAllRentedCars() {
        List<Car> cars = agency.getAllRentedCars();
        if (cars.size() > 0) {
            for (Car car : cars) {
                System.out.println("Number:" + car.getNumber() + " Make:" + car.getMake() + " Model:" + car.getModel()
                        + " Bodystyle:" + car.getBodystyle() + " Color:" + car.getCarColor() + " Year:"
                        + car.getYear());
            }
        } else {
            System.out.println("No cars rented out.");
        }
    }

    // EFFECTS: saves the car rental to file 
    private void saveCarRental() {
        try {
            jsonWriter.open();
            jsonWriter.write(agency);
            jsonWriter.close();
            System.out.println("Saved car rental to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads car rental from file
    private void loadCarRental() {
        try {
            agency = jsonReader.read();
            System.out.println("Loaded car rental from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays total revenue made by the agency
    public void checkTotalRenvenue() {
        System.out.println("Total Revenue:" + agency.getTotalRevenue());
    }

    // EFFECTS: displays the number of cars in the agancy
    public void numberOfCars() {
        System.out.println("Number of cars:" + agency.getAllCars().size());
    }

    // EFFECTS: displays the number of cars rented out in the agancy
    public void numberOfRentedCars() {
        System.out.println("Number of cars rented out:" + agency.getAllRentedCars().size());
    }
}
