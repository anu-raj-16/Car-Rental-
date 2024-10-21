// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

import model.Car;
import model.CarRental;

// represents a reader that reads the car rental stored in the json file
public class JsonReader {
    private String source;

    // constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads car rental from file and returns it
    // throws IOException if an error occurs reading data from it
    public CarRental read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCarRental(jsonObject); // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString(); // code copied from the JsonSerializationDemo repository
    }

    // EFFECTS: parses car rental from JSON object and returns it
    private CarRental parseCarRental(JSONObject jsonObject) {
        CarRental carRental = new CarRental();
        List<Car> allCars = carRental.getAllCars();
        int i = 0;
        while (allCars.size() > 0) {
            Car car = allCars.get(i);
            carRental.removeCar(car.getNumber());
        }

        addAllCars(carRental, jsonObject);
        addRentedCars(carRental, jsonObject);
        totalRevenue(carRental, jsonObject);
        return carRental;
    }

    // MODIFIES: cr
    // EFFECTS: parses allCars from JSON object and adds them to car rental
    private void addAllCars(CarRental cr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allCars");
        for (Object json : jsonArray) {
            JSONObject nextCar = (JSONObject) json;
            addCar(cr, nextCar);
        }
    }

    // MODIFIES: cr
    // EFFECTS: parses car from JSON object and adds them to car rental
    private void addCar(CarRental cr, JSONObject jsonObject) {
        String number = jsonObject.getString("number");
        String make = jsonObject.getString("make");
        String model = jsonObject.getString("model");
        String bodystyle = jsonObject.getString("bodystyle");
        String color = jsonObject.getString("color");
        int year = jsonObject.getInt("year");
        Car car = new Car(number, make, model, bodystyle, color, year);
        cr.addCar(car);
    }

    // MODIFIES: cr
    // EFFECTS: parses rented cars from JSON object, rents out the cars
    // in the car rental and sets the rented out date for the cars
    private void addRentedCars(CarRental cr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allRentedCars");
        for (Object json : jsonArray) {
            JSONObject nextCar = (JSONObject) json;
            rentCar(cr, nextCar);
        }
    }

    // MODIFIES: cr
    // EFFECTS: parses rented car from JSON object, rents out the cars
    // in the car rental and sets the rented out date for the cars
    private void rentCar(CarRental cr, JSONObject jsonObject) {
        String number = jsonObject.getString("number");
        int dayRented = jsonObject.getInt("dateRentedDay");
        int monthRented = jsonObject.getInt("dateRentedMonth");
        int yearRented = jsonObject.getInt("dateRentedYear");
        cr.rentACar(number);
        List<Car> rentedCars = cr.getAllRentedCars();
        for (Car c : rentedCars) {
            if (number.equals(c.getNumber())) {
                c.setDateRented(dayRented, monthRented, yearRented);
            }
        }
    }

    // MODIFIES: cr
    // EFFECTS: parses total revenue from JSON object and sets total
    // revenue for car rental
    private void totalRevenue(CarRental cr, JSONObject jsonObject) {
        double revenue = jsonObject.getDouble("totalRevenue");
        cr.setTotalRevenue(revenue);
    }
}
