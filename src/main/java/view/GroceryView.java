package view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;

public class GroceryView extends JPanel {
    private final String viewName = "Grocery";
    private final Map<String, JPanel> groceryCategoryPanels;
    private GroceryController groceryController;

    public GroceryView() {
        groceryCategoryPanels = new HashMap<>();

        // Initialize categories
        addCategory("🍎 Fruits & Vegetables");
        addCategory("🌾 Grains");
        addCategory("🍖 Proteins");
        addCategory("🥛 Dairy");
        addCategory(" Spices & Condiments");

        // Set up the main layout and background
        Color customBackgroundColor = new Color(219, 232, 215);
        this.setLayout(new BorderLayout());
        this.setBackground(customBackgroundColor);
        this.setPreferredSize(new Dimension(600, 400));

        // Title
        JLabel titleLabel = new JLabel("Your Grocery List", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(customBackgroundColor);
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        // Category Panel
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 3, 15, 15));
        categoryPanel.setBackground(customBackgroundColor);
        this.add(categoryPanel, BorderLayout.CENTER);

        for (String category : groceryCategoryPanels.keySet()) {
            categoryPanel.add(groceryCategoryPanels.get(category));
        }

        // Save & Exit Button
        JButton saveButton = new JButton("Save & Exit");
        saveButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        saveButton.setBackground(new Color(173, 216, 230));
        saveButton.setBorder(new LineBorder(Color.GRAY, 1, true));
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grocery list saved!"));
        this.add(saveButton, BorderLayout.SOUTH);
    }

    private void addCategory(String categoryName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(categoryName),
                new EmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(new Color(219, 232, 215));

        // List of checkboxes for items
        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemListPanel);
        scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input field and button
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBackground(new Color(219, 232, 215));
        JTextField inputField = new JTextField("Enter item...");
        inputField.setForeground(Color.GRAY);
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Enter item...")) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText("Enter item...");
                    inputField.setForeground(Color.GRAY);
                }
            }
        });

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        addButton.setBackground(new Color(192, 192, 192));
        addButton.setBorder(new LineBorder(Color.GRAY, 1, true));
        addButton.addActionListener((ActionEvent e) -> {
            String newItem = inputField.getText().trim();
            if (!newItem.isEmpty() && !newItem.equals("Enter item...")) {
                JCheckBox itemCheckbox = new JCheckBox(newItem);
                itemCheckbox.setBackground(new Color(219, 232, 215));
                itemListPanel.add(itemCheckbox);
                itemListPanel.revalidate();
                itemListPanel.repaint();
                inputField.setText("");
            }
        });

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);

        groceryCategoryPanels.put(categoryName, panel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setGroceryController(GroceryController groceryController) {
        this.groceryController = groceryController;
    }

    // For testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grocery View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GroceryView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
