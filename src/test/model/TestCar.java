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
        testCar = new Car("T1234", "Test Make", "Test Bodystyle", 2024);
    }

    @Test
    void testConstructor() {
        assertEquals("T1234", testCar.getNumber());
        assertEquals("Test Make", testCar.getMake());
        assertEquals("Test Bodystyle", testCar.getBodystyle());
        assertEquals(2024, testCar.getYear());
        assertFalse(testCar.isRented());
    }

    @Test
    void testRentOutCar() {
        testCar.rentOutCar();
        LocalDate dateRented = LocalDate.now();
        assertTrue(testCar.isRented());
        assertEquals(dateRented.toString(), testCar.getRentedDate());
    }

    @Test
    void testReturnCar() {
        testCar.rentOutCar();
        assertTrue(testCar.isRented());
        testCar.returnCar();
        assertFalse(testCar.isRented());
        LocalDate dateReturned = LocalDate.now();
        assertEquals(dateReturned.toString(), testCar.getReturnedDate());
    }

    @Test
    void testCalculateAmount() {
        testCar.calculateAmount(1);
        assertEquals(100, testCar.getAmount());
        testCar.calculateAmount(4);
        assertEquals(400, testCar.getAmount());
    }
}
