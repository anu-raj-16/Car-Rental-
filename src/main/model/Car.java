package model;
// represents a Car in the Car Rental System

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Car {
    String number;
    String make;
    String bodystyle;
    int year;
    boolean rented;
    LocalDate dateRented;
    LocalDate dateReturned;
    double amount;
    long daysRented;

    final double AMOUNT_CHARGED_PER_DAY = 100.0;

    // contructs a Car with given number, make, bodystyle, year 
    // and which has not been rented out to yet
    public Car(String number, String make, String bodystyle, int year) {
        this.number = number;
        this.make = make;
        this.bodystyle = bodystyle;
        this.year = year;
        this.rented = false;
    }

    // REQUIRES: Car is not already rented out
    // MODIFIES: this
    // EFFECTS: rents out this Car and tracks the day it was rented out
    public void rentOutCar() {
        this.rented = true;
        this.dateRented = LocalDate.now();
    }

    // REQUIRES: Car is rented out
    // MODIFIES: this
    // EFFECTS: accepts Car back into the Car Rental System tracks
    //          the date it was returned
    public void returnCar() {
        this.rented = false;
        this.dateReturned = LocalDate.now();
        this.daysRented = ChronoUnit.DAYS.between(dateRented, dateReturned) + 1;
        calculateAmount((int)daysRented);
    }

    // REQUIRES: Car has been returned after being rented out
    // MODIFIES: this
    // EFFECTS: calculates amount paid for renting Car
    public void calculateAmount(int days) {
         this.amount = days * AMOUNT_CHARGED_PER_DAY;
    }

    public boolean isRented() {
        return this.rented; //stub
    }

    public String getNumber() {
        return this.number; //stub
    }

    public String getMake() {
        return this.make; //stub
    }

    public String getBodystyle() {
        return this.bodystyle; //stub
    }

    public int getYear() {
        return this.year; //stub
    }

    public double getAmount() {
        return this.amount; //stub
    }

    public String getRentedDate() {
        return this.dateRented.toString(); //stub
    }

    public String getReturnedDate() {
        return this.dateReturned.toString(); //stub
    }

    public long getDaysRented() {
        return this.daysRented; //stub
    }
}
