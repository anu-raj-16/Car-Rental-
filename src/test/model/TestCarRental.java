package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
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

        List<Car> testAllAvailableCars = testCarRental.getAllAvailableCars();
        assertEquals(5, testAllAvailableCars.size());
        assertEquals("Ford", testAllAvailableCars.get(0).getMake());
        assertEquals("Toyota", testAllAvailableCars.get(1).getMake());
        assertEquals("Honda", testAllAvailableCars.get(2).getMake());
        assertEquals("Nissan", testAllAvailableCars.get(3).getMake());
        assertEquals("Kia", testAllAvailableCars.get(4).getMake());

        assertEquals(0, testCarRental.getTotalRevenue(), 0);
    }

    @Test
    void testAddOneCar() {
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "blue", 2024);
        boolean addCar = testCarRental.addCar(testCar);
        assertTrue(addCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertEquals("TESTCAR", testAllCars.get(5).getMake());
        assertTrue(testAllCars.contains(testCar));

        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testAddCarFails() {
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Black", 2024);
        boolean addCar1 = testCarRental.addCar(testCar);
        boolean addCar2 = testCarRental.addCar(testCar);

        assertTrue(addCar1);
        assertFalse(addCar2);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertEquals("TESTCAR", testAllCars.get(5).getMake());
        assertTrue(testAllCars.contains(testCar));

        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));

    }

    @Test
    void testAddMultipleCars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Black", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Black", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        boolean addCar1 = testCarRental.addCar(testCar1);
        boolean addCar2 = testCarRental.addCar(testCar2);
        boolean addCarFail = testCarRental.addCar(testCar2);
        boolean addCar3 = testCarRental.addCar(testCar3);

        assertTrue(addCar1);
        assertTrue(addCar2);
        assertTrue(addCar3);
        assertFalse(addCarFail);

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
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Green", 2024);
        boolean addCar = testCarRental.addCar(testCar);
        assertTrue(addCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertEquals("TESTCAR", testAllCars.get(5).getMake());

        boolean removeCar = testCarRental.removeCar("234567");
        assertTrue(removeCar);
        testAllCars = testCarRental.getAllCars();
        assertEquals(5, testAllCars.size());
        assertFalse(testAllCars.contains(testCar));

        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testRemoveOneCarUnsuccessful() {
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Black", 2024);
        boolean removeCar = testCarRental.removeCar("234567");
        assertFalse(removeCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        testAllCars = testCarRental.getAllCars();
        assertEquals(5, testAllCars.size());
        assertEquals(5, testAllCars.size());
        assertFalse(testAllCars.contains(testCar));

        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testRemoveOneCarTwiceUnsuccessful() {
        Car testCar = new Car("234567", "TESTCAR", "Test model A", "Sedan", "Blue", 2024);
        boolean addCar = testCarRental.addCar(testCar);
        assertTrue(addCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertEquals("TESTCAR", testAllCars.get(5).getMake());

        boolean removeCarOnce = testCarRental.removeCar("234567");
        boolean removeCarTwice = testCarRental.removeCar("234567");

        assertTrue(removeCarOnce);
        assertFalse(removeCarTwice);
        testAllCars = testCarRental.getAllCars();
        assertEquals(5, testAllCars.size());
        assertEquals(5, testAllCars.size());
        assertFalse(testAllCars.contains(testCar));

        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testRemoveCarWhenCarRentedOut() {
        Car testCar = new Car("234567", "TESTCAR", "Test model B", "Sedan", "Black", 2024);
        boolean addCar = testCarRental.addCar(testCar);
        assertTrue(addCar);
        boolean rentCar = testCarRental.rentACar("234567");
        assertTrue(rentCar);
        boolean removeCar = testCarRental.removeCar("234567");
        assertFalse(removeCar);
        assertTrue(testCarRental.getAllRentedCars().contains(testCar));
        assertTrue(testCarRental.getAllCars().contains(testCar));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testremoveCarAfterReturned() {
        Car testCar = new Car("234567", "TESTCAR", "Test model B", "Sedan", "Black", 2024);
        boolean addCar = testCarRental.addCar(testCar);
        assertTrue(addCar);
        boolean rentCar = testCarRental.rentACar("234567");
        assertTrue(rentCar);
        boolean returnCar = testCarRental.returnACar("234567");
        assertTrue(returnCar);
        boolean removeCar = testCarRental.removeCar("234567");
        assertTrue(removeCar);
        assertFalse(testCarRental.getAllRentedCars().contains(testCar));
        assertFalse(testCarRental.getAllCars().contains(testCar));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
    }

    @Test
    void testRemoveMultipleCars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model A", "Sedan", "Black", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model C", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model C", "Sedan", "Yellow", 2024);
        boolean addCar1 = testCarRental.addCar(testCar1);
        boolean addCar2 = testCarRental.addCar(testCar2);
        boolean addCar3 = testCarRental.addCar(testCar3);
        assertTrue(addCar1);
        assertTrue(addCar2);
        assertTrue(addCar3);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(8, testAllCars.size());
        assertEquals("TESTCAR1", testAllCars.get(5).getMake());
        assertEquals("TESTCAR2", testAllCars.get(6).getMake());
        assertEquals("TESTCAR3", testAllCars.get(7).getMake());

        boolean returnCar3 = testCarRental.removeCar("234876");
        boolean returnCar2 = testCarRental.removeCar("232834");
        boolean returnCar1 = testCarRental.removeCar("236734");
        assertTrue(returnCar3);
        assertTrue(returnCar2);
        assertTrue(returnCar1);

        assertEquals(5, testAllCars.size());
        assertFalse(testAllCars.contains(testCar1));
        assertFalse(testAllCars.contains(testCar2));
        assertFalse(testAllCars.contains(testCar3));

        assertFalse(testCarRental.getAllAvailableCars().contains(testCar1));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar2));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar3));
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
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Green", 2024);
        testCarRental.addCar(testCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));

        boolean rent = testCarRental.rentACar("234567");
        assertTrue(rent);
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));
        assertTrue(testCarRental.getAllRentedCars().contains(testCar));

        assertTrue(testCar.isRented());
    }

    @Test
    void testRentACarUnsucessful() {
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Green", 2024);
        testCarRental.addCar(testCar);
        List<Car> testAllCars = testCarRental.getAllCars();
        assertEquals(6, testAllCars.size());
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));

        boolean rent = testCarRental.rentACar("235567");
        assertFalse(rent);
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));
        assertFalse(testCarRental.getAllRentedCars().contains(testCar));

        assertFalse(testCar.isRented());
    }

    @Test
    void testRentACarWhenNoneAvailable() {
        List<Car> cars = testCarRental.getAllCars();
        int i = 0;
        while (cars.size() > 0) {
            boolean removeCar1 = testCarRental.removeCar(cars.get(i).getNumber());
        }
        assertEquals(0, testCarRental.getAllCars().size());
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Green", 2024);
        boolean rent = testCarRental.rentACar(testCar.getNumber());
        assertFalse(rent);
    }

    @Test
    void testRentMultipleCars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);

        testCarRental.addCar(testCar1);
        testCarRental.addCar(testCar2);
        testCarRental.addCar(testCar3);

        assertTrue(testCarRental.getAllAvailableCars().contains(testCar1));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar2));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar3));

        boolean carRent1 = testCarRental.rentACar("234876");
        boolean carRent2 = testCarRental.rentACar("232834");
        boolean carRent3 = testCarRental.rentACar("235834");

        assertTrue(carRent1);
        assertTrue(carRent2);
        assertFalse(carRent3);

        assertFalse(testCarRental.getAllAvailableCars().contains(testCar1));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar2));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar3));
        assertTrue(testCarRental.getAllRentedCars().contains(testCar1));
        assertTrue(testCarRental.getAllRentedCars().contains(testCar2));
        assertFalse(testCarRental.getAllRentedCars().contains(testCar3));

        assertTrue(testCar1.isRented());
        assertTrue(testCar2.isRented());
        assertFalse(testCar3.isRented());
    }

    @Test
    void testReturnACar() {
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Green", 2024);
        testCarRental.addCar(testCar);
        boolean rentCar = testCarRental.rentACar("234567");
        assertTrue(rentCar);
        boolean returnCar = testCarRental.returnACar("234567");
        assertTrue(returnCar);
        assertFalse(testCarRental.getAllRentedCars().contains(testCar));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar));

        assertEquals(100.0, testCarRental.getTotalRevenue(), 0);
        assertFalse(testCar.isRented());
    }

    @Test
    void testReturnACarUnsucessful() {
        Car testCar = new Car("234567", "TESTCAR", "Test model", "Sedan", "Green", 2024);
        testCarRental.addCar(testCar);
        boolean rentCar = testCarRental.rentACar("234567");
        assertTrue(rentCar);
        boolean returnCar = testCarRental.returnACar("134567");
        assertFalse(returnCar);
        assertTrue(testCarRental.getAllRentedCars().contains(testCar));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar));

        assertEquals(0, testCarRental.getTotalRevenue(), 0);
        assertTrue(testCar.isRented());
    }

    @Test
    void testReturnACarWhenNoneRentedOut() {
        boolean returnCar = testCarRental.returnACar("TestNumber");
        assertFalse(returnCar);
    }

    @Test
    void testReturnMultiplecars() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        testCarRental.addCar(testCar1);
        testCarRental.addCar(testCar2);
        testCarRental.addCar(testCar3);
        boolean rentCar1 = testCarRental.rentACar("234876");
        boolean rentCar2 = testCarRental.rentACar("232834");
        boolean rentCar3 = testCarRental.rentACar("236734");

        assertTrue(rentCar3);
        assertTrue(rentCar2);
        assertTrue(rentCar1);

        assertTrue(testCar1.isRented());
        assertTrue(testCar2.isRented());
        assertTrue(testCar3.isRented());

        boolean returnCar1 = testCarRental.returnACar("234876");
        boolean returnCar2 = testCarRental.returnACar("232834");
        boolean returnCar3 = testCarRental.returnACar("232833");

        assertTrue(returnCar1);
        assertTrue(returnCar2);
        assertFalse(returnCar3);

        assertFalse(testCarRental.getAllRentedCars().contains(testCar1));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar1));

        assertFalse(testCarRental.getAllRentedCars().contains(testCar2));
        assertTrue(testCarRental.getAllAvailableCars().contains(testCar2));

        assertTrue(testCarRental.getAllRentedCars().contains(testCar3));
        assertFalse(testCarRental.getAllAvailableCars().contains(testCar3));

        assertEquals(200.0, testCarRental.getTotalRevenue(), 0);

        assertFalse(testCar1.isRented());
        assertFalse(testCar2.isRented());
        assertTrue(testCar3.isRented());
    }

    @Test
    void testCheckCarInListFailsListNotEmpty() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar2);
        cars.add(testCar3);
        boolean inSystem = testCarRental.checkCarInList(testCar1.getNumber(), cars);
        assertFalse(inSystem);
    }

    @Test
    void testCheckCarInListFailsListEmpty() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        boolean inSystem = testCarRental.checkCarInList(testCar1.getNumber(), cars);
        assertFalse(inSystem);
    }

    @Test
    void testCheckCarInListSuccesful() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar2);
        cars.add(testCar3);
        cars.add(testCar1);
        boolean inSystem = testCarRental.checkCarInList(testCar1.getNumber(), cars);
        assertTrue(inSystem);
    }

    @Test
    void testReturnIndexFirstIndex() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar2);
        cars.add(testCar3);
        cars.add(testCar1);
        int index = testCarRental.returnIndex("232834", cars);
        assertEquals(0, index);
    }

    @Test
    void testReturnIndexMiddleIndex() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar2);
        cars.add(testCar3);
        cars.add(testCar1);
        int index = testCarRental.returnIndex("236734", cars);
        assertEquals(1, index);
    }

    @Test
    void testReturnIndexLastIndex() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar2);
        cars.add(testCar3);
        cars.add(testCar1);
        int index = testCarRental.returnIndex("234876", cars);
        assertEquals(2, index);
    }

    @Test
    void testReturnIndexMultiple() {
        Car testCar1 = new Car("234876", "TESTCAR1", "Test model", "Sedan", "Green", 2024);
        Car testCar2 = new Car("232834", "TESTCAR2", "Test model", "Sedan", "Green", 2024);
        Car testCar3 = new Car("236734", "TESTCAR3", "Test model", "Sedan", "Green", 2024);
        List<Car> cars = new ArrayList<>();
        cars.add(testCar2);
        cars.add(testCar3);
        cars.add(testCar1);
        int index1 = testCarRental.returnIndex("234876", cars);
        int index2 = testCarRental.returnIndex("232834", cars);
        assertEquals(2, index1);
        assertEquals(0, index2);
    }

    // REFERENCE: https://stleary.github.io/JSON-java/index.html
    @Test
    void testToJson() {
        testCarRental.setTotalRevenue(400.0);
        testCarRental.rentACar("V4538H");
        testCarRental.getAllCars().get(0).setDateRented(10, 10, 2024);
        JSONObject json = testCarRental.toJson();
        JSONArray allCarsAdded = json.getJSONArray("allCars");
        JSONArray allRentedCarsAdded = json.getJSONArray("allRentedCars");
        assertEquals(400.0, json.get("totalRevenue"));
        assertEquals(5, allCarsAdded.length());
        assertEquals(1, allRentedCarsAdded.length());
        int i = 0;
        while (i < allCarsAdded.length()) {
            JSONObject car = allCarsAdded.getJSONObject(i);
            assertEquals(testCarRental.getAllCars().get(i).getNumber(), car.get("number"));
            assertEquals(testCarRental.getAllCars().get(i).getMake(), car.get("make"));
            assertEquals(testCarRental.getAllCars().get(i).getModel(), car.get("model"));
            assertEquals(testCarRental.getAllCars().get(i).getBodystyle(), car.get("bodystyle"));
            assertEquals(testCarRental.getAllCars().get(i).getCarColor(), car.get("color"));
            assertEquals(testCarRental.getAllCars().get(i).getYear(), car.get("year"));
            i++;
        }
        i = 0;
        while (i < allRentedCarsAdded.length()) {
            JSONObject car = allRentedCarsAdded.getJSONObject(i);
            assertEquals(testCarRental.getAllCars().get(i).getNumber(), car.get("number"));
            assertEquals(testCarRental.getAllCars().get(i).getMake(), car.get("make"));
            assertEquals(testCarRental.getAllCars().get(i).getModel(), car.get("model"));
            assertEquals(testCarRental.getAllCars().get(i).getBodystyle(), car.get("bodystyle"));
            assertEquals(testCarRental.getAllCars().get(i).getCarColor(), car.get("color"));
            assertEquals(testCarRental.getAllCars().get(i).getYear(), car.get("year"));
            assertEquals(testCarRental.getAllCars().get(i).getRentedDate().getDayOfMonth(), car.get("dateRentedDay"));
            assertEquals(testCarRental.getAllCars().get(i).getRentedDate().getMonthValue(), car.get("dateRentedMonth"));
            assertEquals(testCarRental.getAllCars().get(i).getRentedDate().getYear(), car.get("dateRentedYear"));
            i++;
        }
    }
}
