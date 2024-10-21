// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Car;
import model.CarRental;

public class TestJsonReader extends JsonTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CarRental cr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCarRental() {
        JsonReader reader = new JsonReader("./data/testEmptyCarRental.json");
        try {
            CarRental cr = reader.read();
            assertEquals(0, cr.getAllCars().size());
            assertEquals(0, cr.getAllAvailableCars().size());
            assertEquals(0, cr.getAllRentedCars().size());
            assertEquals(0, cr.getTotalRevenue());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCarRental() {
        JsonReader reader = new JsonReader("./data/testGeneralCarRental.json");
        try {
            CarRental cr = reader.read();
            List<Car> allCars = cr.getAllCars();
            assertEquals(3, allCars.size());
            checkCarInAllCars("V55JJH", "Ford", "Fiesta", "Sedan", "white", 2013, allCars.get(0));
            checkCarInAllCars("V35HJH", "Hyundai", "Sonata", "Sedan", "blue", 2024, allCars.get(1));
            checkCarInAllCars("B67IJH", "Kia", "Seltos", "SUV", "gray", 2022, allCars.get(2));

            List<Car> rentedCars = cr.getAllRentedCars();
            assertEquals(1, rentedCars.size());
            checkCarInRentedCars("B67IJH", "Kia", "Seltos", "SUV", "gray", 2022, 2024, 5, 30, rentedCars.get(0));
            List<Car> availableCars = cr.getAllAvailableCars();
            assertEquals(2, availableCars.size());

            assertEquals(400.0, cr.getTotalRevenue());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
