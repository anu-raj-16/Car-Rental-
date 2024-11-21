// Reference: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import java.awt.Color;
import java.util.List;

import javax.swing.*;

import model.Car;
import model.CarRental;

public class RentedCarsPanel extends JPanel {
    private CarRental carRental;
    private JList<String> rentedCarsJList;

    public RentedCarsPanel(CarRental cr) {
        carRental = cr;
        createCarsPanel();
    }

    private void createCarsPanel() {
        DefaultListModel<String> allCarsStrings = new DefaultListModel<>();
        JLabel allCarsLabel = new JLabel("All rented cars in the agency:");
        allCarsLabel.setForeground(new Color(102,0,0));
        add(allCarsLabel);
        setBackground(new Color(255, 204, 204));
        List<Car> cars = carRental.getAllRentedCars();

        for (Car car : cars) {
            String carString = "Number: " + car.getNumber() + " Make: " + car.getMake() + " Model: " + car.getModel() 
                    + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + " Year: " + car.getYear();
            allCarsStrings.addElement(carString);
        }

        rentedCarsJList = new JList<String>(allCarsStrings);
        rentedCarsJList.setBackground(new Color(255, 153, 153));
        add(rentedCarsJList);
    }

    public void update(CarRental cr) {
        carRental = cr;
        removeAll();
        createCarsPanel();
        revalidate();
        repaint();
    }

}
