package ui;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import model.Car;
import model.CarRental;

public class AvailableCarsPanel extends JPanel {
    private CarRental carRental;
    private JList<String> availableCarsJList;

    public AvailableCarsPanel(CarRental cr) {
        carRental = cr;
        createCarsPanel();
    }

    private void createCarsPanel() {
        DefaultListModel<String> allCarsStrings = new DefaultListModel<>();
        JLabel allCarsLabel = new JLabel("All available cars in the agency:");
        allCarsLabel.setForeground(new Color(102, 51, 0));
        add(allCarsLabel);
        setBackground(new Color(255, 229, 204));
        List<Car> cars = carRental.getAllAvailableCars();

        for (Car car : cars) {
            String carString = "Number: " + car.getNumber() + " Make: " + car.getMake() + " Model: " + car.getModel()
                    + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + " Year: " + car.getYear();
            allCarsStrings.addElement(carString);
        }

        availableCarsJList = new JList<String>(allCarsStrings);
        availableCarsJList.setBackground(new Color(255, 204, 153));
        add(availableCarsJList);
    }

    public void update(CarRental cr) {
        carRental = cr;
        removeAll();
        createCarsPanel();
        revalidate();
        repaint();
    }
}
