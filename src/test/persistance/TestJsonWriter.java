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

public class TestJsonWriter extends JsonTest {
    @Test
    void testReadInvalidFile() {
        try {
            CarRental cr = new CarRental();
            JsonWriter writer = new JsonWriter("./data/illegalFile.json");
            writer.open();
        } catch (IOException e) {
            // expected
        }
        fail("IOException was expected");
    }

    @Test
    void testWriterEmptyCarRental() {
        try {
            CarRental cr = new CarRental();
            while (cr.getAllCars().size() > 0) {
                cr.removeCar(cr.getAllCars().get(0).getNumber());
            }
            JsonWriter writer = new JsonWriter("./data/testEmptyCarRental.json");
            writer.open();
            writer.write(cr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyCarRental.json");
            cr = reader.read();
            assertEquals(0, cr.getAllCars().size());
            assertEquals(0, cr.getAllAvailableCars().size());
            assertEquals(0, cr.getAllRentedCars().size());
            assertEquals(0, cr.getTotalRevenue());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCarRental() {
        try {
            CarRental cr = new CarRental();
            while (cr.getAllCars().size() > 0) {
                cr.removeCar(cr.getAllCars().get(0).getNumber());
            }
            Car car1 = new Car("V55JJH", "Ford", "Fiesta", "Sedan", "white", 2013);
            Car car2 = new Car("V35HJH", "Hyundai", "Sonata", "Sedan", "blue", 2024);
            Car car3 = new Car("B67IJH", "Kia", "Seltos", "SUV", "gray", 2022);
            cr.addCar(car1);
            cr.addCar(car2);
            cr.addCar(car3);
            cr.rentACar("B67IJH");
            car3.setDateRented(30, 5, 2024);
            cr.setTotalRevenue(400);

            JsonWriter writer = new JsonWriter("./data/testGeneralCarRental.json");
            writer.open();
            writer.write(cr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyCarRental.json");
            cr = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
