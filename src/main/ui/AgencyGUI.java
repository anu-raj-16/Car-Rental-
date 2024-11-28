// REFERENCE: Java Swing Tutorial for Beginners by Java Code Junkie on YouTube
// REFERENCE: https://www.youtube.com/watch?v=4PfDdJ8GFHI&list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&index=3
// REFERENCE: https://www.youtube.com/watch?v=impJtkTcQ94&list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&index=6

// REFERENCE: AlarmSystem Project 
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// Reference: B02-SpaceInvadersBase Project
// https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// Reference: SimpleDrawingPlayer-Starter Project
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter

// Reference: https://www.javatpoint.com/java-string-to-int

// Referenced from the JsonSerialization Demo - 
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// REFERENCE: RobustTrafficLights Project or the C3-LectureLabStarter - 
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// REFERENCE: for the rgb code for colors - https://www.rapidtables.com/web/color/RGB_Color.html

// REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html#windowlistener

package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import model.Car;
import model.CarRental;
import model.Event;
import model.EventLog;
import persistance.JsonReader;
import persistance.JsonWriter;

public class AgencyGUI extends JFrame implements WindowListener {
    private static final String JSON_STORE = "./data/carRental.json";
    private CarRental agency;
    private CarsPanel allCarsPanel;
    private RentedCarsPanel allRentedCarsPanel;
    private AvailableCarsPanel allAvailableCarsPanel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the main window and initializes the Car Rental system
    public AgencyGUI() {
        super("Car Rental Agency");
        initialize();
    }

    // REFERENCE: Java Swing Tutorial for Beginners by Java Code Junkie on YouTube
    // https://www.youtube.com/watch?v=4PfDdJ8GFHI&list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&index=3
    // REFERENCE:
    // https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
    // EFFECTS: initializes the Car Rental system
    private void initialize() {
        agency = new CarRental();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        addWindowListener(this);

        setLayout(new GridLayout(0, 1));
        setSize(800, 500);
        setLocationRelativeTo(null);
        // setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel allButtons = addbuttonsPanel();
        allButtons.setBackground(new Color(255, 204, 204));
        add(allButtons, BorderLayout.NORTH);

        // Reference: B02-SpaceInvadersBase Project
        // https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
        allCarsPanel = new CarsPanel(agency);
        add(allCarsPanel, BorderLayout.CENTER);

        allRentedCarsPanel = new RentedCarsPanel(agency);
        add(allRentedCarsPanel);

        allAvailableCarsPanel = new AvailableCarsPanel(agency);
        add(allAvailableCarsPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: adds a car to the system if it does not already exist
    private void addCar() {
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
            allRentedCarsPanel.update(agency);
            allAvailableCarsPanel.update(agency);
            JOptionPane.showMessageDialog(null, "Car has been added!");
        } else {
            JOptionPane.showMessageDialog(null, "Sorry, car with given number is already in system.");
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: removes a car if it not rented out and present in the system
    private void removeCar() {
        String number = JOptionPane.showInputDialog(null,
                "Car number?", "Enter car number", JOptionPane.QUESTION_MESSAGE);
        if (agency.removeCar(number)) {
            JOptionPane.showMessageDialog(null, "Car has been removed!");
            allCarsPanel.update(agency);
            allRentedCarsPanel.update(agency);
            allAvailableCarsPanel.update(agency);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Sorry, car with given number has been rented out or is not in the system.");
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: rents out the car if it is available and person is eligible.
    private void rentCar() {
        if (eligibility()) {
            String number = JOptionPane.showInputDialog(null,
                    "Car number?", "Enter car number", JOptionPane.QUESTION_MESSAGE);
            if (agency.rentACar(number)) {
                JOptionPane.showMessageDialog(null, "Car has been rented out!");
                allCarsPanel.update(agency);
                allRentedCarsPanel.update(agency);
                allAvailableCarsPanel.update(agency);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Sorry, car with given number has been rented out or is not in the system.");
            }
        } else {
            JOptionPane.showMessageDialog(null,
                        "Sorry, you are not eligible to rent a car.");
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: checks if a person is eligible to rent a car.
    private boolean eligibility() {
        String age = JOptionPane.showInputDialog(null,
                "age", "Enter age", JOptionPane.QUESTION_MESSAGE);
        String expirationDate = JOptionPane.showInputDialog(null,
                "expiration date - yyyy-mm-dd", "Enter expiration date", JOptionPane.QUESTION_MESSAGE);

        LocalDate today = LocalDate.now();
        LocalDate expDateLocalDate = LocalDate.parse(expirationDate);

        int ageNum = Integer.parseInt(age);
        return ageNum >= 21 && expDateLocalDate.compareTo(today) > 0;
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: returns a car if it has been rented out.
    private void returnCar() {
        String number = JOptionPane.showInputDialog(null,
                "Car number?", "Enter car number", JOptionPane.QUESTION_MESSAGE);
        if (agency.returnACar(number)) {
            JOptionPane.showMessageDialog(null, "Car has been returned!");
            allCarsPanel.update(agency);
            allRentedCarsPanel.update(agency);
            allAvailableCarsPanel.update(agency);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Sorry, car with given number was not returned.");
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves car rental to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(agency);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Car Rental stored to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: loads car rental from file
    private void load() {
        try {
            agency = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Car Rental loaded from " + JSON_STORE);
            allCarsPanel.update(agency);
            allRentedCarsPanel.update(agency);
            allAvailableCarsPanel.update(agency);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
    }

    // REFERENCE: for image icons specifically, RobustTrafficLights Project or the C3-LectureLabStarter - 
    // https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter
    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: adds the buttons to the JFrame
    private JPanel addbuttonsPanel() {
        // images generated by Gemini AI
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton(new AddCar());
        addButton.setIcon(new ImageIcon("./data/images/add_icon.jpeg"));
        buttonsPanel.add(addButton);

        JButton removeButton = new JButton(new RemoveCar());
        removeButton.setIcon(new ImageIcon("./data/images/remove_icon.jpeg"));
        buttonsPanel.add(removeButton);

        JButton rentButton = new JButton(new RentCar());
        rentButton.setIcon(new ImageIcon("./data/images/rent_icon.jpeg"));
        JButton returnButton = new JButton(new ReturnCar());
        returnButton.setIcon(new ImageIcon("./data/images/Return_icon.jpeg"));
        buttonsPanel.add(rentButton);
        buttonsPanel.add(returnButton);
        JButton saveButton = new JButton(new SaveCarRental());
        saveButton.setIcon(new ImageIcon("./data/images/save_image.jpeg"));
        buttonsPanel.add(saveButton);
        JButton loadButton = new JButton(new LoadCarRental());
        loadButton.setIcon(new ImageIcon("./data/images/LoadButton.jpeg"));
        buttonsPanel.add(loadButton);
        JButton visualButton = new JButton(new VisualComponent());
        visualButton.setIcon(new ImageIcon("./data/images/visual.jpeg"));
        buttonsPanel.add(visualButton);
        return buttonsPanel;
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when user wants to add a car to the agency.
    private class AddCar extends AbstractAction {

        AddCar() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addCar();
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when user wants to remove a car from the agency.

    private class RemoveCar extends AbstractAction {
        RemoveCar() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removeCar();
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when user wants to rent a car from the agency.
    private class RentCar extends AbstractAction {
        RentCar() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rentCar();
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when user wants to return a car to the agency.
    private class ReturnCar extends AbstractAction {
        ReturnCar() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            returnCar();
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when user wants to save the car rental.
    private class SaveCarRental extends AbstractAction {
        SaveCarRental() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when user wants to load the car rental.
    private class LoadCarRental extends AbstractAction {
        LoadCarRental() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            load();
        }
    }

    // REFERENCE: AlarmSystem Project - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // represents action to be taken when one wants to see the visual component image.
    private class VisualComponent extends AbstractAction {

        VisualComponent() {
            super("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Splash splash = new Splash();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // REFERENCE: AlarmSystem Project 
        // https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
        
        EventLog el = EventLog.getInstance();
        for (Event event : el) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
}
