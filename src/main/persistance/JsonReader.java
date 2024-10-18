// Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.CarRental;

// represents a reader that reads the car rental stored in the json file
public class JsonReader {

    // constructs a reader to read from source file
    public JsonReader(String source) {
        
    }

    // EFFECTS: reads car rental from file and returns it
    // throws IOException if an error occurs reading data from it
    public CarRental read() throws IOException {
        return null; // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null; // stub
    }

    // EFFECTS: parses car rental from JSON object and returns it
    private CarRental parseCarRental(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: cr
    // EFFECTS: parses allCars from JSON object and adds them to car rental
    private void addAllCars(CarRental cr, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: cr
    // EFFECTS: parses car from JSON object and adds them to car rental
    private void addCar(CarRental cr, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: cr
    // EFFECTS: parses rented cars from JSON object, rents out the cars
    // in the car rental and sets the rented out date for the cars
    private void addRentedCars(CarRental cr, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: cr
    // EFFECTS: parses rented car from JSON object, rents out the cars
    // in the car rental and sets the rented out date for the cars
    private void rentCar(CarRental cr, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: cr
    // EFFECTS: parses total revenue from JSON object and sets total 
    // revenue for car rental
    private void totalRevenue(CarRental cr, JSONObject jsonObject) {
        // stub
    }
}

