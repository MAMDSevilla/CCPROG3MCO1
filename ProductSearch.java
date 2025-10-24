import java.util.Scanner;

/**
 * Allows shoppers to search for a product by name and view its display location in the store.
 */
public class ProductSearch {
    private StoreMap map;
    private Scanner scanner;

    // ===== Constructor =====
    public ProductSearch(StoreMap map) {
        this.map = map;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Interacts with the shopper — prompts for a product name and shows its location.
     */
    public void interact(Shopper s) {
        System.out.println("\nWelcome to the Product Search Station!");
        System.out.print("Enter product name to search: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Please enter a valid product name.");
            return;
        }

        String result = search(query);
        if (result != null) {
            System.out.println("Product found at: " + result);
        } else {
            System.out.println("Sorry, no display currently holds \"" + query + "\".");
        }
    }

    /**
     * Searches all displays in the map for a product by name.
     * Returns a formatted string with the display’s ID, type, and location.
     */
    public String search(String name) {
        for (Display d : map.getDisplays()) {
            for (Product p : d.getProducts()) {
                if (p.getProductName().equalsIgnoreCase(name)) {
                    return String.format(
                        "%s (Type: %s | Location: %s)",
                        d.getId(), d.getType(), d.getLocationString()
                    );
                }
            }
        }
        return null;
    }
}
