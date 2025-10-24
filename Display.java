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

    // ===== Constructor =====
    public Display(String id, DispType type, int capacity) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.products = new ArrayList<>();
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
            System.out.println("Display " + id + " is full.");
            return false;
        }
        if (!isCompatible(p.getType())) {
            System.out.println("Product type not compatible with this display.");
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
        return type.getCompatibleDisplay() == this.type;
    }

    // ===== Getters =====
    public String getId() { return id; }
    public DispType getType() { return type; }
    public int getCapacity() { return capacity; }
    public ArrayList<Product> getProducts() { return products; }
    public int getRow() { return row; }
    public int getCol() { return col; }

    // ===== Display Info =====
    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("This display is empty.");
            return;
        }

        System.out.println("Products on Display " + id + ":");
        for (Product p : products) {
            System.out.println("- " + p.getName() + " (₱" + p.getPrice() + ")");
        }

        System.out.println("Type the product serial number to pick up, or press Enter to cancel:");
        String choice = new java.util.Scanner(System.in).nextLine().trim();
        if (!choice.isEmpty()) {
            Product chosen = removeProduct(choice);
            if (chosen != null) {
                System.out.println("You picked up " + chosen.getName() + "!");
            } else {
                System.out.println("No product found with that serial.");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Display[%s] (%s) - %d/%d products", id, type, products.size(), capacity);
    }
}
