import java.util.ArrayList;

/**
 * Represents a display shelf, fridge, table, or counter that holds products.
 */
public class Display {
    // ===== Fields =====
    private String id;
    private DispType type;
    private int capacity;
    private ArrayList<Product> products;
    private int row = -1;
    private int col = -1;
    private java.util.List<java.util.List<Product>> tiers;

    // ===== Constructor =====
    public Display(String id, DispType type, int capacity) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.products = new ArrayList<>();
        this.tiers = new ArrayList<>();
        // Assume 1 tier for simplicity, or based on type
        int numTiers = 1; // TODO: define based on DispType
        for (int i = 0; i < numTiers; i++) {
            tiers.add(new ArrayList<>());
        }
    }

    // ===== Position Handling =====
    public void setLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String getLocationString() {
        return "(" + row + "," + col + ")";
    }

    // ===== Product Management =====
    public boolean addProduct(Product p) {
        if (products.size() >= capacity) {
            return false;
        }
        if (!isCompatible(p.getType())) {
            return false;
        }
        products.add(p);
        return true;
    }

    public Product removeProduct(String serial) {
        for (Product p : new ArrayList<>(products)) { // avoid concurrent modification
            if (p.getSerial().equalsIgnoreCase(serial)) {
                products.remove(p);
                return p;
            }
        }
        return null;
    }

    public boolean isCompatible(ProductType type) {
        if (this.type == DispType.SHELF) {
            return true;
        }
        return type.getCompatibleDisplay() == this.type;
    }

    // ===== Getters =====
    public String getId() { return id; }
    public DispType getType() { return type; }
    public int getCapacity() { return capacity; }
    public ArrayList<Product> getProducts() { return products; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getAddress() { return id; }
    public DispType getDisplayType() { return type; }
    public java.util.List<java.util.List<Product>> getTiers() { return tiers; }
    public boolean containsProduct(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean addProduct(Product p, int tier) {
        if (tier < 0 || tier >= tiers.size()) return false;
        java.util.List<Product> tierList = tiers.get(tier);
        if (tierList.size() >= capacity / tiers.size()) return false; // simple capacity per tier
        if (!isCompatible(p.getType())) return false;
        tierList.add(p);
        products.add(p); // also add to flat list
        return true;
    }
    public Product removeProduct(String serial, int tier) {
        if (tier < 0 || tier >= tiers.size()) return null;
        java.util.List<Product> tierList = tiers.get(tier);
        for (Product p : new ArrayList<>(tierList)) {
            if (p.getSerial().equalsIgnoreCase(serial)) {
                tierList.remove(p);
                products.remove(p);
                return p;
            }
        }
        return null;
    }

    // ===== Display Info =====
    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("This display is empty.");
            return;
        }

        System.out.println("Available Products on Display " + id + " (Serial: Name - Price):");
        for (Product p : products) {
            System.out.println("Serial: " + p.getSerial() + " | Name: " + p.getName() + " | Price: ₱" + String.format("%.2f", p.getPrice()));
        }

        System.out.println("Enter the full serial number (e.g., FRU00001) to pick up, or press Enter to cancel:");
        String choice = new java.util.Scanner(System.in).nextLine().trim();
        if (!choice.isEmpty()) {
            Product chosen = removeProduct(choice);
            if (chosen != null) {
                System.out.println("You picked up " + chosen.getName() + "!");
            } else {
                System.out.println("No product found with serial '" + choice + "'. Ensure it's 7 characters (e.g., FRU00001).");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Display[%s] (%s) - %d/%d products", id, type, products.size(), capacity);
    }
}
