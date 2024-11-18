// Reference: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

import model.Car;
import model.CarRental;

public class CarsPanel extends JPanel {
    private CarRental carRental;
    private JList<String> carsJList;

    public static final String SANS_SERIF = "SansSerif";
    private static final int LBL_HEIGHT = 30;
    private static final int LBL_WIDTH = 700;

    // EFFECTS: contructs a panel with all cars in the car rental
    public CarsPanel(CarRental cr) {
        carRental = cr;
        createCarsPanel();

        // JLabel allCarsLabel = new JLabel("All cars in the agency:");
        // allCarsLabel.setForeground(new Color(255,255,102));
        // add(allCarsLabel);

        // for (Car car : cars) {
        // JTextField carLabel = new JTextField("Number: " + car.getNumber() + " Make: "
        // + car.getMake() + " Model: " + car.getModel()
        // + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + "
        // Year: " + car.getYear());
        // carLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        // carLabel.setForeground(new Color(255,255,153));
        // carLabel.setBackground((new Color(153, 76, 0)));
        // carLabel.setEditable(false);
        // add(carLabel);
        // add(new JLabel("\n"));
        // }

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
        // JLabel allCarsLabel = new JLabel("All cars in the agency:");
        // allCarsLabel.setPreferredSize(new Dimension(300, 30));
        // allCarsLabel.setForeground(new Color(255,255,102));
        // add(allCarsLabel);

        // List<Car> cars = carRental.getAllCars();
        // for (Car car : cars) {
        // JLabel carLabel = new JLabel("Number: " + car.getNumber() + " Make: " +
        // car.getMake() + " Model: " + car.getModel()
        // + " Bodystyle: " + car.getBodystyle() + " Color: " + car.getCarColor() + "
        // Year: " + car.getYear());
        // carLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        // carLabel.setForeground(new Color(255,255,153));
        // add(carLabel);
        // }
        revalidate();
        repaint();
    }
}
