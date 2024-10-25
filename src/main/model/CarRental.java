// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistance.Writable;

//represents a Car Rental system
public class CarRental implements Writable {
    private List<Car> allCars;
    private List<Car> allAvailableCars;
    private List<Car> allRentedCars;
    private double totalRevenue;

    private static final Car FORD = new Car("V4538H", "Ford", "Fiesta", "Sedan", "Black", 2020);
    private static final Car TOYOTA = new Car("V3487J", "Toyota", "Corolla", "Sedan", "Blue", 2021);
    private static final Car HONDA = new Car("SE48HG", "Honda", "Civic", "SUV", "Gray", 2021);
    private static final Car NISSAN = new Car("YO46GH", "Nissan", "Rogue", "SUV", "Red", 2023);
    private static final Car KIA = new Car("BR2XF6", "Kia", "Seltos", "Sedan", "Blue", 2022);

    // EFFECTS: constructs a Car Rental with five default cars, no cars rented out,
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

    // MODIFIES: this
    // EFFECTS: adds the given car to the list of cars if given car with its car.getNumber() is
    // not already in the system and sets the car as available for renting and
    // returns true, else returns false
    public boolean addCar(Car c) {
        if (checkCarInList(c.getNumber(), allCars)) {
            return false;
        } else {
            allCars.add(c);
            allAvailableCars.add(c);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the car with the given number from the list of cars in this 
    // if car is in the system, not rented out (is available) and returns true,
    // else returns false
    public boolean removeCar(String number) {
        if (checkCarInList(number, allAvailableCars)) {
            allCars.remove(returnIndex(number, allCars));
            allAvailableCars.remove(returnIndex(number, allAvailableCars));
            return true;
        } else {
            return false;
        }
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

    // REQUIRES: person the car being rented out to is old enough and
    // has a valid driver's license
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
        if (allAvailableCars.size() > 0) {
            if (allRentedCars.contains(allAvailableCars.get(count))) {
                allAvailableCars.remove(count);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the car to the agency with the given name
    // and updates the total revenue made and returns true if successful
    // else returns false
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
        if (allRentedCars.size() > 0) {
            if (allAvailableCars.contains(allRentedCars.get(count))) {
                allRentedCars.remove(count);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if a car given the given number is in given list,
    //          else returns false, also return false if list is empty
    public boolean checkCarInList(String number, List<Car> cars) {
        if (cars.size() > 0) {
            for (Car car : cars) {
                if (car.getNumber().equals(number)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    // REQUIRES: car with given number must be in list
    // EFFECTS: returns the index which contains the car with given number
    public int returnIndex(String number, List<Car> cars) {
        int index = 0;
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getNumber().equals(number)) {
                index = i;
            }
        }
        return index;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("allCars", addCarsToFile());
        json.put("allRentedCars", addRentedCarsToFile());
        json.put("totalRevenue", this.totalRevenue);
        return json;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: adds all cars in the car rental to file
    private JSONArray addCarsToFile() {
        JSONArray jsonArray = new JSONArray();

        for (Car c : this.allCars) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: adds all rented cars in the car rental to file
    private JSONArray addRentedCarsToFile() {
        JSONArray jsonArray = new JSONArray();

        for (Car c : this.allRentedCars) {
            jsonArray.put(c.toJsonForRentedCars());
        }

        return jsonArray;
    }

    // setter

    public void setTotalRevenue(double revenue) {
        this.totalRevenue = revenue;
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
