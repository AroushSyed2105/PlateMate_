package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GroceryView extends JPanel {
    private final String viewName = "Grocery";
    private final Map<String, JPanel> groceryCategoryPanels;

    private Image backgroundImage; //

    public GroceryView() {
        try {
            backgroundImage = ImageIO.read(new File("images/BG6.png")); // Replace with the path to your image
            if (backgroundImage == null) {
                System.out.println("Error: Image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if image is not found
        }

        // Set layout and make the background transparent
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Ensure the panel is transparent
        this.setOpaque(false);


        groceryCategoryPanels = new HashMap<>();

        // Initialize categories
        addCategory("🍎 Fruits & Vegetables");
        addCategory("🌾 Grains");
        addCategory("🍖 Proteins");
        addCategory("🥛 Dairy");
        addCategory(" \uD83C\uDF36\uFE0F Spices");
        addCategory(" Condiments");

        // Set layout and make the background transparent
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(100));

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


        // Category Panel
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 3, 15, 15));
        categoryPanel.setBackground(customBackgroundColor);


        for (String category : groceryCategoryPanels.keySet()) {
            categoryPanel.add(groceryCategoryPanels.get(category));
        }

        // Save & Exit Button
        JButton saveButton = new JButton("Save & Exit");
        saveButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        saveButton.setBackground(new Color(192, 192, 192));
        saveButton.setBorder(new LineBorder(Color.GRAY, 1, true));
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grocery list saved!"));

        //this.add(Box.createVerticalGlue());

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(categoryPanel, BorderLayout.CENTER);
        this.add(saveButton, BorderLayout.SOUTH);


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image to cover the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }


    private void addCategory(String categoryName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(categoryName),
                new EmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(new Color(255, 255, 240));

        // List of checkboxes for items
        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemListPanel);
        scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input field and button
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBackground(new Color(255, 255, 240));
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
                itemCheckbox.setBackground(new Color(255, 255, 240));
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
