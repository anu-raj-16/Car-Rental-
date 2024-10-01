package model;
// represents a Car in the Car Rental System
public class Car {
    // contructs a Car with given number, make, bodystyle, year 
    // and which has not been rented out to yet
    public Car() {
        
    }

    // REQUIRES: Car is not already rented out
    // MODIFIES: this
    // EFFECTS: rents out this Car
    public void rentOutCar() {
        //stub
    }

    // REQUIRES: Car is rented out
    // MODIFIES: this
    // EFFECTS: accepts Car back into the Car Rental System
    public void returnCar() {
        //stub
    }

    // REQUIRES: Car has been returned after being rented out
    // MODIFIES: this
    // EFFECTS: calculates amount paid for renting Car
    public void calculateAmount() {
         //stub
    }

    public boolean isRented() {
        return false; //stub
    }

    public int getNumber() {
        return 0; //stub
    }

    public String getMake() {
        return ""; //stub
    }

    public String getBodystyle() {
        return ""; //stub
    }

    public int getYear() {
        return 0; //stub
    }

    public double getAmount() {
        return 0.0; //stub
    }
}
