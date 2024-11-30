package interface_adapter.groceries;

public class GroceryState {

        private String[] groceryList = {};
        private String[] categories = {}; // Optional: Categories for grouping items (e.g., fruits, vegetables, etc.)

        // Getter for groceryList
        public String[] getGroceryList() {
            return groceryList;
        }

        // Setter for groceryList
        public void setGroceryList(String[] groceryList) {
            this.groceryList = groceryList;
        }

        // Getter for categories
        public String[] getCategories() {
            return categories;
        }

        // Setter for categories
        public void setCategories(String[] categories) {
            this.categories = categories;
        }
}
