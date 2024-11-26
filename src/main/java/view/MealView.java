package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.profile.ProfileController;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.profile.ProfileViewModel;

public class MealView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "MealPlan";

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

    public Object getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}