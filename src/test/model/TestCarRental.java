package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCarRental {
    private CarRental testCarRental;

    @BeforeEach
    void setUp() {
        testCarRental = new CarRental();
    }

    @Test
    void testConstructor() {
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(5, testAllCars.size());
        assertEquals("Ford", testAllCars.get(0).getMake());
        assertEquals("Toyota", testAllCars.get(1).getMake());
        assertEquals("Honda", testAllCars.get(2).getMake());
        assertEquals("Nissan", testAllCars.get(3).getMake());
        assertEquals("Kia", testAllCars.get(4).getMake());
        assertEquals(0, testCarRental.getTotalRevenue(), 0);
    }

    @Test
    void testAddOneCar() {
        Car testCar = new Car("234567", "TESTCAR", "Sedan", 2024);
        testCarRental.addCar(testCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertEquals("TESTCAR", testAllCars.get(5).getMake());
        assertTrue(testAllCars.contains(testCar));

        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testAddMultipleCars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Sedan", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Sedan", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Sedan", 2024);
        testCarRental.addCar(testCar1);
        testCarRental.addCar(testCar2);
        testCarRental.addCar(testCar3);

        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(8, testAllCars.size());
        assertEquals("TESTCAR1", testAllCars.get(5).getMake());
        assertEquals("TESTCAR2", testAllCars.get(6).getMake());
        assertEquals("TESTCAR3", testAllCars.get(7).getMake());
        assertTrue(testAllCars.contains(testCar1));
        assertTrue(testAllCars.contains(testCar2));
        assertTrue(testAllCars.contains(testCar3));

        assertTrue(testCarRental.getAllAvailableCars().contains(testCar1));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar2));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar3));
    }

    @Test
    void testRemoveOneCar() {
        Car testCar = new Car("234567", "TESTCAR", "Sedan", 2024);
        testCarRental.addCar(testCar);

        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertEquals("TESTCAR", testAllCars.get(5).getMake());

        testCarRental.removeCar(testCar);
        testAllCars = testCarRental.getAllCars();
        assertEquals(5, testAllCars.size());
        assertFalse(testAllCars.contains(testCar));
    }

    @Test
    void testRemoveMultipleCars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Sedan", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Sedan", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Sedan", 2024);
        testCarRental.addCar(testCar1);
        testCarRental.addCar(testCar2);
        testCarRental.addCar(testCar3);

        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(8, testAllCars.size());
        assertEquals("TESTCAR1", testAllCars.get(5).getMake());
        assertEquals("TESTCAR2", testAllCars.get(6).getMake());
        assertEquals("TESTCAR3", testAllCars.get(7).getMake());

        testCarRental.removeCar(testCar3);
        testCarRental.removeCar(testCar2);
        testCarRental.removeCar(testCar1);
        assertEquals(5, testAllCars.size());
        assertFalse(testAllCars.contains(testCar1));
        assertFalse(testAllCars.contains(testCar2));
        assertFalse(testAllCars.contains(testCar3));
    }

    @Test
    void testCalculateTotalRevenue() {
        testCarRental.calculateTotalRevenue(435.5);
        assertEquals(435.5, testCarRental.getTotalRevenue(), 0.05);

        testCarRental.calculateTotalRevenue(500);
        assertEquals(935.5, testCarRental.getTotalRevenue(), 0.05);
    }

    @Test
    void testRentACar() {
        Car testCar = new Car("234567", "TESTCAR", "Sedan", 2024);
        testCarRental.addCar(testCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));

        testCarRental.rentACar("234567");
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
        assertTrue(testCarRental.getAllRentedCars().contains(testCar));
    }

    @Test
    void testRentMultipleCars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Sedan", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Sedan", 2024);

        testCarRental.addCar(testCar1);
        testCarRental.addCar(testCar2);

        assertTrue(testCarRental.getAllAvailableCars().contains(testCar1));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar2));

        testCarRental.rentACar("234876");
        testCarRental.rentACar("232834");

        assertFalse(testCarRental.getAllAvailableCars().contains(testCar1));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar2));
        assertTrue(testCarRental.getAllRentedCars().contains(testCar1));
        assertTrue(testCarRental.getAllRentedCars().contains(testCar2));
    }

    @Test
    void testReturnACar() {
        Car testCar = new Car("234567", "TESTCAR", "Sedan", 2024);
        testCarRental.addCar(testCar);
        testCarRental.rentACar("234567");
        testCarRental.returnACar("234567");
        assertFalse(testCarRental.getAllRentedCars().contains(testCar));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));

        assertEquals(100.0, testCarRental.getTotalRevenue(), 0);
    }

    @Test
    void testReturnMultiplecars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Sedan", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Sedan", 2024);
        testCarRental.addCar(testCar1);
        testCarRental.addCar(testCar2);
        testCarRental.rentACar("234876");
        testCarRental.rentACar("232834");

        testCarRental.returnACar("234876");
        testCarRental.returnACar("232834");

        assertFalse(testCarRental.getAllRentedCars().contains(testCar1));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar1));

        assertFalse(testCarRental.getAllRentedCars().contains(testCar2));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar2));

        assertEquals(200.0, testCarRental.getTotalRevenue(), 0);
    }
}
