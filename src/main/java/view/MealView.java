package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class MealView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "MealPlan";

    public MealView() {
        // Set layout for the panel
        this.setLayout(new BorderLayout());

        // Add a label to the panel to display a test message
        JLabel testLabel = new JLabel("MealView is working!", JLabel.CENTER);
        testLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Optional: styling the text
        this.add(testLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("MealView Test Screen");

        // Create an instance of MealView
        MealView mealView = new MealView();

        // Add MealView to the frame
        frame.add(mealView);

        // Set up the frame
        frame.setSize(400, 300); // Slightly larger for better visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application on exit
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Display the frame
        frame.setVisible(true);
    }

    public Object getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Placeholder for handling actions
        System.out.println("Action performed: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Placeholder for handling property changes
        System.out.println("Property changed: " + evt.getPropertyName());
    }
}
