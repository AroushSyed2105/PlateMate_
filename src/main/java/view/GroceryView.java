package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class GroceryView extends JPanel {
    private final String viewName = "Grocery";
    private final Map<String, DefaultListModel<String>> groceryCategories; // To store category data dynamically

    public GroceryView() {
        // Initialize the map to hold categories and their lists
        groceryCategories = new HashMap<>();
        groceryCategories.put("Fruits & Vegetables", new DefaultListModel<>());
        groceryCategories.put("Grains", new DefaultListModel<>());
        groceryCategories.put("Proteins", new DefaultListModel<>());
        groceryCategories.put("Dairy", new DefaultListModel<>());
        groceryCategories.put("Spices & Condiments", new DefaultListModel<>());

        // Set the main layout and background color
        Color customBackgroundColor = new Color(219, 232, 215); // Desired background color
        this.setLayout(new BorderLayout());
        this.setBackground(customBackgroundColor);
        this.setPreferredSize(new Dimension(600, 400));

        // Create a title with custom background
        JLabel titleLabel = new JLabel("Grocery List", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(customBackgroundColor);
        this.add(titleLabel, BorderLayout.NORTH);

        // Create the main panel for categories
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 3, 10, 10)); // Adjust rows/cols as needed
        categoryPanel.setBackground(customBackgroundColor); // Apply background color
        this.add(categoryPanel, BorderLayout.CENTER);

        // Populate the panel with categories
        for (String category : groceryCategories.keySet()) {
            categoryPanel.add(createCategoryPanel(category, customBackgroundColor));
        }

        // Add a "Save & Exit" button
        JButton saveButton = new JButton("Save & Exit");
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grocery list saved!"));
        this.add(saveButton, BorderLayout.SOUTH);
    }

    // Helper method to create individual category panels
    private JPanel createCategoryPanel(String categoryName, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(categoryName)); // Category name as the title
        panel.setBackground(backgroundColor); // Apply background color

        // List for displaying items
        JList<String> itemList = new JList<>(groceryCategories.get(categoryName));
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.getViewport().setBackground(backgroundColor); // Set background for the scroll pane viewport
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input field and button to add items
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(backgroundColor); // Apply background color
        JTextField inputField = new JTextField();
        JButton addButton = new JButton("Add");

        addButton.addActionListener((ActionEvent e) -> {
            String newItem = inputField.getText().trim();
            if (!newItem.isEmpty()) {
                groceryCategories.get(categoryName).addElement(newItem);
                inputField.setText("");
            }
        });

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    public String getViewName() {
        return viewName;
    }
}