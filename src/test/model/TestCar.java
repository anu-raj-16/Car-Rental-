package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCar {
    private Car testCar;

    @BeforeEach
    void runBefore() {
        testCar = new Car("T1234", "Test Make", "Test Model", "Test Bodystyle", "Yellow", 2024);
    }

    @Test
    void testConstructor() {
        assertEquals("T1234", testCar.getNumber());
        assertEquals("Test Make", testCar.getMake());
        assertEquals("Test Model", testCar.getModel());
        assertEquals("Test Bodystyle", testCar.getBodystyle());
        assertEquals("Yellow", testCar.getCarColor());
        assertEquals(2024, testCar.getYear());
        assertFalse(testCar.isRented());
    }

    @Test
    void testRentOutCar() {
        testCar.rentOutCar();
        LocalDate dateRented = LocalDate.now();
        assertTrue(testCar.isRented());
        assertEquals(dateRented.getDayOfMonth(), testCar.getRentedDate().getDayOfMonth());
        assertEquals(dateRented.getMonth(), testCar.getRentedDate().getMonth());
        assertEquals(dateRented.getYear(), testCar.getRentedDate().getYear());
    }

    @Test
    void testReturnCar() {
        testCar.rentOutCar();
        assertTrue(testCar.isRented());
        LocalDate dateRented = LocalDate.now();
        assertEquals(dateRented.getDayOfMonth(), testCar.getRentedDate().getDayOfMonth());
        assertEquals(dateRented.getMonth(), testCar.getRentedDate().getMonth());
        assertEquals(dateRented.getYear(), testCar.getRentedDate().getYear());
        testCar.returnCar();
        assertFalse(testCar.isRented());
        LocalDate dateReturned = LocalDate.now();
        assertEquals(dateReturned.getDayOfMonth(), testCar.getReturnedDate().getDayOfMonth());
        assertEquals(dateReturned.getMonth(), testCar.getReturnedDate().getMonth());
        assertEquals(dateReturned.getYear(), testCar.getReturnedDate().getYear());
        assertEquals(1, testCar.getDaysRented());
    }

    @Test
    void testCalculateAmount() {
        testCar.calculateAmount(1);
        assertEquals(100, testCar.getAmountChargedPerDay());
        testCar.calculateAmount(4);
        assertEquals(400, testCar.getAmountChargedPerDay());
    }

    @Test
    void testCalculateDaysTentedOneDay() {
        testCar.setDateRented(5, 5, 2024);
        testCar.setDateReturned(5, 5, 2024);
        testCar.calculateDaysRented();
        assertEquals(1, testCar.getDaysRented());
    }

    @Test
    void testCalculateDaysTentedMultipleDay() {
        testCar.setDateRented(5, 9, 2024);
        testCar.setDateReturned(15, 9, 2024);
        testCar.calculateDaysRented();
        assertEquals(11, testCar.getDaysRented());
    }
}
