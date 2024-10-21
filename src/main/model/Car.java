// REFERENCE: referred to Java Documentation provided by Oracle to understand how LocalDate.now()
// and ChronoUnit.DAYS.between(start, end) functions work
// https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/time/temporal/ChronoUnit.html#between(java.time.temporal.Temporal,java.time.temporal.Temporal)
// https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/time/LocalDate.html#now()
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;

import persistance.Writable;

// represents a Car in the Car Rental System
public class Car implements Writable {
    private String number;
    private String make;
    private String model;
    private String bodystyle;
    private String color;
    private int year;
    private boolean rented;
    private LocalDate dateRented;
    private LocalDate dateReturned;
    private double amount;
    private long daysRented;

    private static final double AMOUNT_CHARGED_PER_DAY = 100.0;

    // EFFECTS: contructs a Car with given number, make, model, bodystyle, color, year
    // and which has not been rented out to yet
    public Car(String number, String make, String model, String bodystyle, String color, int year) {
        this.number = number;
        this.make = make;
        this.model = model;
        this.bodystyle = bodystyle;
        this.color = color;
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
    // the date it was returned, updates number of days it was rented out for,
    // updates amount made
    public void returnCar() {
        this.rented = false;
        this.dateReturned = LocalDate.now();
        calculateDaysRented();
        calculateAmount((int) daysRented);
    }

    // REQUIRES: Car has been returned after being rented out
    // MODIFIES: this
    // EFFECTS: calculates amount paid for renting Car
    public void calculateAmount(int days) {
        this.amount = days * AMOUNT_CHARGED_PER_DAY;
    }

    // REQUIRES: car must have been rented and then returned and dateRented <= dateReturned
    // MODIFIES: this
    // EFFECTS: calculates the number of days for which the car had been rented out
    // dateRented and dateReturned is inclusive
    public void calculateDaysRented() {
        this.daysRented = ChronoUnit.DAYS.between(this.dateRented, this.dateReturned) + 1;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        return null;
    }

    //setters
    public void setDateRented(int day, int month, int year) {
        dateRented = LocalDate.of(year, month, day);
    }

    public void setDateReturned(int day, int month, int year) {
        dateReturned = LocalDate.of(year, month, day);
    }

    // getters
    public boolean isRented() {
        return this.rented; // stub
    }

    public String getNumber() {
        return this.number; // stub
    }

    public String getMake() {
        return this.make; // stub
    }

    public String getModel() {
        return this.model; // stub
    }

    public String getBodystyle() {
        return this.bodystyle; // stub
    }

    public String getCarColor() {
        return this.color; // stub
    }

    public int getYear() {
        return this.year; // stub
    }

    public double getAmountChargedPerDay() {
        return this.amount; // stub
    }

    public LocalDate getRentedDate() {
        return this.dateRented; // stub
    }

    public LocalDate getReturnedDate() {
        return this.dateReturned; // stub
    }

    public long getDaysRented() {
        return this.daysRented; // stub
    }
}
