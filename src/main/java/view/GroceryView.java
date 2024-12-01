package view;

import interface_adapter.groceries.GroceryController;
import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;

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
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroceryView extends JPanel {
    private final String viewName = "Grocery";
    private final Map<String, JPanel> groceryCategoryPanels;
    private GroceryController groceryController;
    private Image backgroundImage;

    public GroceryView() throws IOException {
        try {
            backgroundImage = ImageIO.read(new File("images/BG6.png")); // Replace with the path to your image
            if (backgroundImage == null) {
                System.out.println("Error: Image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if image is not found
        }

        // Set layout and make the background transparent
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        groceryCategoryPanels = new HashMap<>();

        // Title
        JLabel titleLabel = new JLabel("Your Grocery List", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(219, 232, 215));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Category Panel
        JPanel categoryPanel = createCategoryPanel();

        // Ingredient Panel
        JPanel ingredientPanel = createIngredientPanel();

        // Save & Exit Button
        JButton saveButton = new JButton("Save & Exit");
        saveButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        saveButton.setBackground(new Color(192, 192, 192));
        saveButton.setBorder(new LineBorder(Color.GRAY, 1, true));
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grocery list saved!"));

        // Add components to the view
        this.add(titleLabel, BorderLayout.NORTH);
        this.add(categoryPanel, BorderLayout.CENTER); // Categories in the center
        this.add(ingredientPanel, BorderLayout.SOUTH); // Ingredients below categories
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 3, 15, 15)); // Grid for categories
        categoryPanel.setBackground(new Color(219, 232, 215));

        // Initialize categories
        addCategory("🍎 Fruits & Vegetables");
        addCategory("🌾 Grains");
        addCategory("🍖 Proteins");
        addCategory("🥛 Dairy");
        addCategory("🌶 Spices");
        addCategory("🍯 Condiments");

        // Add each category to the grid
        for (String category : groceryCategoryPanels.keySet()) {
            categoryPanel.add(groceryCategoryPanels.get(category));
        }

        return categoryPanel;
    }

    private JPanel createIngredientPanel() throws IOException {
        // Initialize DAO and parse meal plan
        CommonUserFactory commonUserFactory = new CommonUserFactory();
        DBUserDataAccessObject dao = new DBUserDataAccessObject(commonUserFactory);
        String TempMeal = dao.generateMealPlan("Halal, avocado");

        // Parse the meal plan
        Map<String, String> unformattedPlan = dao.fullMealPlan(TempMeal);

        // Extract ingredients
        Map<String, List<String>> ingredientsMap = extractIngredients(unformattedPlan);

        // Panel for all ingredients
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        ingredientsPanel.setBackground(new Color(204, 229, 255)); // Light blue background
        ingredientsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel ingredientsTitle = new JLabel("Ingredients:");
        ingredientsTitle.setFont(new Font("Times New Roman", Font.BOLD, 16));
        ingredientsTitle.setForeground(new Color(0, 51, 102)); // Dark blue
        ingredientsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsPanel.add(ingredientsTitle);

        JTextArea allIngredientsText = new JTextArea();
        allIngredientsText.setEditable(false);
        allIngredientsText.setBackground(new Color(240, 248, 255)); // Slightly off-white for readability
        allIngredientsText.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        allIngredientsText.setForeground(new Color(0, 0, 0));
        allIngredientsText.setWrapStyleWord(true);
        allIngredientsText.setLineWrap(true);

        // Collect all ingredients into a single string
        StringBuilder allIngredients = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : ingredientsMap.entrySet()) {
            allIngredients.append(entry.getKey()).append(":\n");
            for (String ingredient : entry.getValue()) {
                allIngredients.append("- ").append(ingredient).append("\n");
            }
            allIngredients.append("\n");
        }

        allIngredientsText.setText(allIngredients.toString());
        ingredientsPanel.add(allIngredientsText);

        return ingredientsPanel;
    }

    private void addCategory(String categoryName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(categoryName),
                new EmptyBorder(6, 6, 6, 6)
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

    public void setGroceryController(GroceryController groceryController) {
        this.groceryController = groceryController;
    }

    private static Map<String, List<String>> extractIngredients(Map<String, String> mealPlan) {
        Map<String, List<String>> ingredientsMap = new LinkedHashMap<>();
        Pattern ingredientsPattern = Pattern.compile("Ingredients:\\s*(.*?)\\n\\n", Pattern.DOTALL);

        for (Map.Entry<String, String> entry : mealPlan.entrySet()) {
            String mealType = entry.getKey();
            String mealDetails = entry.getValue();
            Matcher matcher = ingredientsPattern.matcher(mealDetails);

            if (matcher.find()) {
                String ingredientsBlock = matcher.group(1).trim();
                String[] ingredients = ingredientsBlock.split("\\n- ");
                List<String> ingredientList = new ArrayList<>();
                for (String ingredient : ingredients) {
                    if (!ingredient.isEmpty()) {
                        ingredientList.add(ingredient.trim());
                    }
                }
                ingredientsMap.put(mealType, ingredientList);
            }
        }

        return ingredientsMap;
    }

    // For testing
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Grocery View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GroceryView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

//package view;
//
//import javax.imageio.ImageIO;
//import interface_adapter.groceries.GroceryController;
//
//import javax.swing.*;
//import javax.swing.border.CompoundBorder;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GroceryView extends JPanel {
//    private final String viewName = "Grocery";
//    private final Map<String, JPanel> groceryCategoryPanels;
//    private GroceryController groceryController;
//
//    private Image backgroundImage; //
//
//    public GroceryView() {
//        try {
//            backgroundImage = ImageIO.read(new File("images/BG6.png")); // Replace with the path to your image
//            if (backgroundImage == null) {
//                System.out.println("Error: Image not found.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle exception if image is not found
//        }
//
//        // Set layout and make the background transparent
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        // Ensure the panel is transparent
//        this.setOpaque(false);
//
//
//        groceryCategoryPanels = new HashMap<>();
//
//        // Initialize categories
//        addCategory("🍎 Fruits & Vegetables");
//        addCategory("🌾 Grains");
//        addCategory("🍖 Proteins");
//        addCategory("🥛 Dairy");
//        addCategory(" \uD83C\uDF36\uFE0F Spices");
//        addCategory(" Condiments");
//
//        // Set layout and make the background transparent
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.add(Box.createVerticalStrut(100));
//
//        // Set up the main layout and background
//        Color customBackgroundColor = new Color(219, 232, 215);
//        this.setLayout(new BorderLayout());
//        this.setBackground(customBackgroundColor);
//        this.setPreferredSize(new Dimension(600, 400));
//
//        // Title
//        JLabel titleLabel = new JLabel("Your Grocery List", JLabel.CENTER);
//        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
//        titleLabel.setOpaque(true);
//        titleLabel.setBackground(customBackgroundColor);
//        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
//
//
//        // Category Panel
//        JPanel categoryPanel = new JPanel();
//        categoryPanel.setLayout(new GridLayout(2, 3, 15, 15));
//        categoryPanel.setBackground(customBackgroundColor);
//
//
//        for (String category : groceryCategoryPanels.keySet()) {
//            categoryPanel.add(groceryCategoryPanels.get(category));
//        }
//
//        // Save & Exit Button
//        JButton saveButton = new JButton("Save & Exit");
//        saveButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//        saveButton.setBackground(new Color(192, 192, 192));
//        saveButton.setBorder(new LineBorder(Color.GRAY, 1, true));
//        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grocery list saved!"));
//        this.add(saveButton, BorderLayout.SOUTH);
//
//        //this.add(Box.createVerticalGlue());
//
//        this.add(titleLabel, BorderLayout.NORTH);
//        this.add(categoryPanel, BorderLayout.CENTER);
//
//
//    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (backgroundImage != null) {
//            // Draw the background image to cover the entire panel
//            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//        }
//    }
//
//
//    private void addCategory(String categoryName) {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(new CompoundBorder(
//                BorderFactory.createTitledBorder(categoryName),
//                new EmptyBorder(5, 5, 5, 5)
//        ));
//        panel.setBackground(new Color(255, 255, 240));
//
//        // List of checkboxes for items
//        JPanel itemListPanel = new JPanel();
//        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
//        JScrollPane scrollPane = new JScrollPane(itemListPanel);
//        scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
//        panel.add(scrollPane, BorderLayout.CENTER);
//
//        // Input field and button
//        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
//        inputPanel.setBackground(new Color(255, 255, 240));
//        JTextField inputField = new JTextField("Enter item...");
//        inputField.setForeground(Color.GRAY);
//        inputField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (inputField.getText().equals("Enter item...")) {
//                    inputField.setText("");
//                    inputField.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (inputField.getText().isEmpty()) {
//                    inputField.setText("Enter item...");
//                    inputField.setForeground(Color.GRAY);
//                }
//            }
//        });
//
//        JButton addButton = new JButton("Add");
//        addButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
//        addButton.setBackground(new Color(192, 192, 192));
//        addButton.setBorder(new LineBorder(Color.GRAY, 1, true));
//        addButton.addActionListener((ActionEvent e) -> {
//            String newItem = inputField.getText().trim();
//            if (!newItem.isEmpty() && !newItem.equals("Enter item...")) {
//                JCheckBox itemCheckbox = new JCheckBox(newItem);
//                itemCheckbox.setBackground(new Color(255, 255, 240));
//                itemListPanel.add(itemCheckbox);
//                itemListPanel.revalidate();
//                itemListPanel.repaint();
//                inputField.setText("");
//            }
//        });
//
//        inputPanel.add(inputField, BorderLayout.CENTER);
//        inputPanel.add(addButton, BorderLayout.EAST);
//        panel.add(inputPanel, BorderLayout.SOUTH);
//
//        groceryCategoryPanels.put(categoryName, panel);
//    }
//
//    public String getViewName() {
//        return viewName;
//    }
//
//    public void setGroceryController(GroceryController groceryController) {
//        this.groceryController = groceryController;
//    }
//
//    // For testing
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Grocery View");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(new GroceryView());
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//}
