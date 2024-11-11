package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MealPlanView {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Temporary Screen");

        // Add a label with the text
        JLabel label = new JLabel("Temporary Meal Plan", JLabel.CENTER);

        // Set up the frame
        frame.add(label); // Add label to the frame
        frame.setSize(300, 200); // Set the frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application on exit
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Display the frame
        frame.setVisible(true);
    }
}
