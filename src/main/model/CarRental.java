package model;

import java.util.ArrayList;
import java.util.List;

//represents a Car Rental agency
public class CarRental {
    private List<Car> allCars;
    private List<Car> allAvailableCars;
    private List<Car> allRentedCars;
    private double totalRevenue;

    private static final Car FORD = new Car("V45 38H", "Ford", "Sedan", 2020);
    private static final Car TOYOTA = new Car("V34 87J", "Toyota", "Sedan", 2021);
    private static final Car HONDA = new Car("SE4 8HG", "Honda", "SUV", 2021);
    private static final Car NISSAN = new Car("YO4 6GH", "Nissan", "SUV", 2023);
    private static final Car KIA = new Car("BR2 XF6", "Kia", "Sedan", 2022);

    // EFFECTS: constructs a Car Rental with five default cars
    // and sets total revenue to zero.
    public CarRental() {
        allCars = new ArrayList<>();
        allCars.add(FORD);
        allCars.add(TOYOTA);
        allCars.add(HONDA);
        allCars.add(NISSAN);
        allCars.add(KIA);

        allAvailableCars = new ArrayList<>();
        allAvailableCars.add(FORD);
        allAvailableCars.add(TOYOTA);
        allAvailableCars.add(HONDA);
        allAvailableCars.add(NISSAN);
        allAvailableCars.add(KIA);

        allRentedCars = new ArrayList<>();

        totalRevenue = 0;
    }

    // REQUIRES: car must not already be owned by this Car Rental
    // MODIFIES: this
    // EFFECTS: adds the given car to the list of cars in this
    // and sets the car as available for renting
    public void addCar(Car c) {
        allCars.add(c);
        allAvailableCars.add(c);
    }

    // REQUIRES: car has to be owned by this Car Rental
    // MODIFIES: this
    // EFFECTS: remove the given car from the list of cars in this
    public void removeCar(Car c) {
        allCars.remove(c);
        allAvailableCars.remove(c);
    }

    // REQUIRES: a car has been returned just before calling this method and method
    // should not be called again until another car has been returned or
    // the same car was rented out and returned again and amount >= 0
    // MODIFIES: this
    // EFFECTS: calculates the total revenue made by adding
    // the amount made by each car in the rental
    public void calculateTotalRevenue(double amount) {
        totalRevenue += amount;
    }

    // MODIFIES: this
    // EFFECTS: rents out the car with the given name and returns true if successful
    // else returns false
    public boolean rentACar(String number) {
        int count = 0;
        for (int i = 0; i < allAvailableCars.size(); i++) {
            Car car = allAvailableCars.get(i);
            if (car.getNumber().equals(number)) {
                car.rentOutCar();
                allRentedCars.add(car);
                count = i;
            }
        }
        if (allRentedCars.contains(allAvailableCars.get(count))) {
            allAvailableCars.remove(count);
            return true;
        } else {
            return false;
        }

    }

    // MODIFIES: this
    // EFFECTS: returns the car with the given name
    // and updates the total revenue made
    public boolean returnACar(String number) {
        int count = 0;
        for (int i = 0; i < allRentedCars.size(); i++) {
            Car car = allRentedCars.get(i);
            if (car.getNumber().equals(number)) {
                car.returnCar();
                allAvailableCars.add(car);
                count = i;
                calculateTotalRevenue(car.getAmountChargedPerDay());
            }
        }
        if (allAvailableCars.contains(allRentedCars.get(count))) {
            allRentedCars.remove(count);
            return true;
        } else {
            return false;
        }
    }

    // getters

    public List<Car> getAllCars() {
        return allCars;
    }

    public List<Car> getAllAvailableCars() {
        return allAvailableCars;
    }

    public List<Car> getAllRentedCars() {
        return allRentedCars;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
