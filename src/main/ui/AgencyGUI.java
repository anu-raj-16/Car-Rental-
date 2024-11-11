// REFERENCE: https://www.youtube.com/watch?v=4PfDdJ8GFHI&list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&index=3
// REFERENCE: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Car;
import model.CarRental;
import persistance.JsonReader;
import persistance.JsonWriter;

public class AgencyGUI extends JFrame {
    private static final String JSON_STORE = "./data/carRental.json";
    private CarRental agency;
    private CarsPanel allCarsPanel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the main window and initializes the Car Rental system
    public AgencyGUI() {
        super("Car Rental Agency");
        initialize();
    }

    // EFFECTS: initializes the Car Rental system
    public void initialize() {
        agency = new CarRental();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setLayout(new BorderLayout());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(addbuttonsPanel(), BorderLayout.NORTH);

        allCarsPanel = new CarsPanel(agency);
        add(allCarsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // EFFECTS: adds a car to the system if it does not already exist
    public void addCar() {
        String number = JOptionPane.showInputDialog(null,
                "Car number?", "Enter car number", JOptionPane.QUESTION_MESSAGE);
        String make = JOptionPane.showInputDialog(null,
                "Car make?", "Enter car make", JOptionPane.QUESTION_MESSAGE);
        String model = JOptionPane.showInputDialog(null,
                "Car model?", "Enter car model", JOptionPane.QUESTION_MESSAGE);
        String bodystyle = JOptionPane.showInputDialog(null,
                "Car bodystyle?", "Enter car bodystyle", JOptionPane.QUESTION_MESSAGE);
        String color = JOptionPane.showInputDialog(null,
                "Car color?", "Enter car color", JOptionPane.QUESTION_MESSAGE);
        int year = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Car year?", "Enter car year", JOptionPane.QUESTION_MESSAGE));
        Car newCar = new Car(number, make, model, bodystyle, color, year);
        if (agency.addCar(newCar)) {
            allCarsPanel.update(agency);
            JOptionPane.showMessageDialog(null, "Car has been added!");
        } else {
            JOptionPane.showMessageDialog(null, "Sorry, car with given number is already in system.");
        }
    }

    // EFFECTS: removes a car if it not rented out and present in the system
    public void removeCar() {
        String number = JOptionPane.showInputDialog(null,
                "Car number?", "Enter car number", JOptionPane.QUESTION_MESSAGE);
        if (agency.removeCar(number)) {
            JOptionPane.showMessageDialog(null, "Car has been removed!");
            allCarsPanel.update(agency);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Sorry, car with given number has been rented out or is not in the system.");
        }
    }

    public void rentCar() {
        String number = JOptionPane.showInputDialog(null,
                "Car number?", "Enter car number", JOptionPane.QUESTION_MESSAGE);
        if (agency.rentACar(number)) {
            JOptionPane.showMessageDialog(null, "Car has been rented out!");
            allCarsPanel.update(agency);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Sorry, car with given number has been rented out or is not in the system.");
        }
    }

    public void viewAvailableCars() {
        JOptionPane.showMessageDialog(rootPane, accessibleContext);
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves car rental to file
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(agency);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Car Rental stored to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Unable to write to file: " + JSON_STORE);
        }
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: loads car rental from file
    public void load() {
        try {
            agency = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Car Rental loaded from " + JSON_STORE);
            allCarsPanel.update(agency);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
    }

    public JPanel addbuttonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(new JButton(new AddCar()));
        buttonsPanel.add(new JButton(new RemoveCar()));
        buttonsPanel.add(new JButton(new RentCar()));
        buttonsPanel.add(new JButton(new ViewAvailableCars()));
        buttonsPanel.add(new JButton(new SaveCarRental()));
        buttonsPanel.add(new JButton(new LoadCarRental()));
        return buttonsPanel;
    }

    private class AddCar extends AbstractAction {

        AddCar() {
            super("Add a car");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addCar();
        }
    }

    private class RemoveCar extends AbstractAction {
        RemoveCar() {
            super("Remove a car");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removeCar();
        }
    }

    private class RentCar extends AbstractAction {
        RentCar() {
            super("Rent a car");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rentCar();
        }
    }

    private class ViewAvailableCars extends AbstractAction {
        ViewAvailableCars() {
            super("View all available cars");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            viewAvailableCars();
        }
    }

    private class SaveCarRental extends AbstractAction {
        SaveCarRental() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }
    }

    private class LoadCarRental extends AbstractAction {
        LoadCarRental() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            load();
        }
    }
}
