// References: TellerApp from Phase 1 page of the EdX page

package ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Car;
import model.CarRental;

// Car rental ageny app
public class Agency {

    private CarRental agency;
    private Scanner input;

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

        init();

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

        System.out.println("Thank you and hope to see you soon!");
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
        } else {
            System.out.println("Invalid input. Please try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes car rental agency
    public void init() {
        agency = new CarRental();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays the options available to the user
    public void displayMenu() {
        System.out.println("Select from");
        System.out.println("a -> to rent a car");
        System.out.println("b -> to return a car");
        System.out.println("c -> to add a car to the agency");
        System.out.println("d -> to remove a car from the agency");
        System.out.println("e -> to view all cars");
        System.out.println("f -> to view all available cars");
        System.out.println("g -> to view all cars that have been rented out");
        System.out.println("h -> to check total revenue");
        System.out.println("i -> to check the number of cars in the agency");
        System.out.println("j -> to check the number of cars rented out");
        System.out.println("q -> to quit");
    }

    // MODIFIES: this
    // EFFECTS: rents out the car requested by user if user is eligible
    public void rentOutACar() {
        if (checkEligible()) {
            System.out.println("Here is a list of all available cars:");
            viewAllAvailableCars();
            System.out.println("Enter the number of the car you wish to rent out:");
            String number = input.next();
            System.out.println(number);
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
    // EFFECTS: returns the car rented out by user
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
    // EFFECTS: adds a car to collection owned by the agency 
    public void addACar() {
        System.out.println("Please enter the number of the car you want to add:");
        String number = input.next();
        List<Car> cars = agency.getAllCars();
        boolean carIn = false;
        if (cars.size() > 0) {
            for (Car car : cars) {
                if (car.getNumber().equals(number)) {
                    carIn = true;
                }
            }
        }
        if (!carIn) {
            getNewCar(number);
        } else {
            System.out.println("Car is already in the system.");
        }
    }

    // REQUIRES: agency does not already have a car with number
    // MODIFIES: this
    // EFFFECTS: adds a car with the given input to the agency
    private void getNewCar(String number) {
        System.out.println("Please enter the make of the car:");
        String make = input.next();
        System.out.println("Please enter the bodystyle of the car:");
        String bodystyle = input.next();
        System.out.println("Please enter the year/ model of the car:");
        int year = input.nextInt();
        Car newCar = new Car(number, make, bodystyle, year);
        agency.addCar(newCar);
        System.out.println("Car was added to the system!");
    }

    // MODIFIES: this
    // EFFECTS: remove a car from collection owned by the agency
    public void removeACar() {
        System.out.println("Please enter the number of the car you wish to remove:");
        String number = input.next();
        List<Car> carsAvailable = agency.getAllAvailableCars();
        Car removeCar = new Car("Demo", "Demo", "Demo", 2024);
        boolean carAvailable = false;
        if (carsAvailable.size() > 0) {
            for (Car car : carsAvailable) {
                if (car.getNumber().equals(number)) {
                    carAvailable = true;
                    removeCar = car;
                }
            }
        }
        if (carAvailable) {
            agency.removeCar(removeCar);
            System.out.println("Car was removed from system.");
        } else {
            System.out.println("Sorry, car is not in the system or has been rented out.");
        }
    }

    // EFFECTS: returns true if the person is eligible for renting a car; else
    // returns false.
    public boolean checkEligible() {
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
    // in the order - number, make, bodystyle, model
    public void viewAllCars() {
        List<Car> cars = agency.getAllCars();
        if (cars.size() > 0) {
            for (Car car : cars) {
                System.out.println("Number:" + car.getNumber() + " Make:" + car.getMake() + " Bodystyle:"
                        + car.getBodystyle() + " Year:" + car.getYear());
            }
        } else {
            System.out.println("No cars in the agency");
        }
    }

    // EFFECTS: displays information of all cars owned by the car rental that are
    // available
    // for renting in the order - number, make, bodystyle, model
    public void viewAllAvailableCars() {
        List<Car> cars = agency.getAllAvailableCars();
        if (cars.size() > 0) {
            for (Car car : cars) {
                System.out.println("Number:" + car.getNumber() + " Make:" + car.getMake() + " Bodystyle:"
                        + car.getBodystyle() + " Year:" + car.getYear());
            }
        } else {
            System.out.println("No cars available in the agency");
        }
    }

    // EFFECTS: displays information of all cars owned by the car rental that have
    // been
    // rented out in the order - number, make, bodystyle, model
    public void viewAllRentedCars() {
        List<Car> cars = agency.getAllRentedCars();
        if (cars.size() > 0) {
            for (Car car : cars) {
                System.out.println("Number:" + car.getNumber() + " Make:" + car.getMake() + " Bodystyle:"
                        + car.getBodystyle() + " Year:" + car.getYear());
            }
        } else {
            System.out.println("No cars rented out.");
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
