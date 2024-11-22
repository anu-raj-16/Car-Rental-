// Reference: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import model.Car;
import model.CarRental;

// represents the panel with all the cars in the agency.

public class CarsPanel extends JPanel {
    private CarRental carRental;
    private JList<String> carsJList;

    // EFFECTS: contructs a panel with all cars in the car rental
    public CarsPanel(CarRental cr) {
        carRental = cr;
        createCarsPanel();
    }

    private void createCarsPanel() {
        DefaultListModel<String> allCarsStrings = new DefaultListModel<>();
        JLabel allCarsLabel = new JLabel("All cars in the agency:");
        allCarsLabel.setForeground(new Color(102, 51, 0));
        add(allCarsLabel);
        setBackground(new Color(255, 229, 204));
        List<Car> cars = carRental.getAllCars();

        for (Car car : cars) {
            String carString = "Number: " + car.getNumber() + " Make: " + car.getMake() + " Model: " + car.getModel()
                    + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + " Year: " + car.getYear();
            allCarsStrings.addElement(carString);
        }

        carsJList = new JList<String>(allCarsStrings);
        carsJList.setBackground(new Color(255, 204, 153));
        add(carsJList);
    }

    // EFFECTS: updates the panel of all cars
    public void update(CarRental cr) {
        carRental = cr;
        removeAll();

        createCarsPanel();
        revalidate();
        repaint();
    }
}
