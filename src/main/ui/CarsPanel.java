// Reference: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Car;
import model.CarRental;

public class CarsPanel extends JPanel {
    private JPanel carsPanel;
    private CarRental carRental;

    private static final int LBL_HEIGHT = 30;
    private static final int LBL_WIDTH = 700;

    // EFFECTS: contructs a panel with all cars in the car rental
    public CarsPanel(CarRental cr) {
        carRental = cr;
        setBackground(new Color(100, 180, 180));
        List<Car> cars = carRental.getAllCars();

        for (Car car : cars) {
            JLabel carLabel = new JLabel("Number: " + car.getNumber() + " Make: " + car.getMake() + " Model: " + car.getModel() 
            + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + " Year: " + car.getYear());
            carLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
            add(carLabel);
        }
        
    }

    // EFFECTS: updates the panel of all cars
    public void update(CarRental cr) {
        carRental = cr;
        removeAll();
        List<Car> cars = carRental.getAllCars();
        for (Car car : cars) {
            JLabel carLabel = new JLabel("Number: " + car.getNumber() + " Make: " + car.getMake() + " Model: " + car.getModel() 
            + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + " Year: " + car.getYear());
            carLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
            add(carLabel);
        }
        repaint();
    }
}
