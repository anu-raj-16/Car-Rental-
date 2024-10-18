package persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Car;

public class JsonTest {
    protected void checkCarInAllCars(String number, String make, String model, String bodystyle, String color, int year, Car c) {
        assertEquals(number, c.getNumber());
        assertEquals(make, c.getMake());
        assertEquals(model, c.getModel());
        assertEquals(bodystyle, c.getBodystyle());
        assertEquals(color, c.getCarColor());
        assertEquals(year, c.getYear());
    }

    protected void checkCarInRentedCars(String number, String make, String model, String bodystyle, String color, int year, int rentedYear, int rentedMonth, int day, Car c) {
        assertEquals(number, c.getNumber());
        assertEquals(make, c.getMake());
        assertEquals(model, c.getModel());
        assertEquals(bodystyle, c.getBodystyle());
        assertEquals(color, c.getCarColor());
        assertEquals(year, c.getYear());
        assertEquals(rentedYear, c.getRentedDate().getYear());
        assertEquals(rentedMonth, c.getRentedDate().getMonthValue());
        assertEquals(day, c.getRentedDate().getDayOfMonth());
    }
}
