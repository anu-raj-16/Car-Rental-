package model;

import java.util.List;

//represents a Car Rental agency
public class CarRental {

    // EFFECTS: constructs a Car Rental with five default cars 
    // and sets total revenue to zero.
    public CarRental() {

    }

    // REQUIRES: car must not already be owned by this Car Rental
    // MODIFIES: this
    // EFFECTS: adds the given car to the list of cars in this 
    // and sets the car as available for renting
    public void addCar(Car c) {

    }

    // REQUIRES: car has to be owned by this Car Rental
    // MODIFIES: this
    // EFFECTS: remove the given car from the list of cars in this
    public void removeCar(Car c) {

    }

    // REQUIRES: a car has been returned just before calling this method and method
    // should not be called again until another car has been returned or
    // the same car was rented out and returned again and amount >= 0
    // MODIFIES: this
    // EFFECTS: calculates the total revenue made by adding
    // the amount made by each car in the rental
    public void calculateTotalRevenue(double amount) {

    }

    // REQUIRES: a valid car number that belongs to the car rental agency
    // MODIFIES: this
    // EFFECTS: rents out the car with the given name
    public void rentACar(String number) {

    }

    // REQUIRES: a valid car number that belongs to the car rental agency
    // MODIFIES: this
    // EFFECTS: returns the car with the given name
    // and updates the total revenue made
    public void returnACar(String number) {

    }

    // EFFECTS: lists all the cars in car rental agency
    public void listAllCars() {

    }

    // EFFECTS: lists all the cars in the car rental agency 
    // available for renting
    public void listAllCarsAvailable() {

    }

    // EFFECTS: lists all the cars in the car rental agency 
    // that have been rented out
    public void listAllCarsRentedOut() {

    }

    //getters

    public List<Car> getAllCars() {
        return null;
    }

    public List<Car> getAllAvailableCars() {
        return null;
    }

    public List<Car> getAllRentedCars() {
        return null;
    }

    public double getTotalRevenue() {
        return 0.0;
    }
}
