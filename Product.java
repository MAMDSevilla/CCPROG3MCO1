public class Product {
    // ===== Fields =====
    private String name;
    private ProductType type;
    private String serial;
    private double price;
    private int stock;

    // ===== Constructor =====
    public Product(String name, ProductType type, double price, int stock) {
        this.name = name;
        this.type = type;
        this.serial = type.generateSerial();
        this.price = price;
        this.stock = stock;
    }

    // ===== Getters =====
    public String getName() { return name; }
    public String getProductName() { return name; } // alias for older code
    public ProductType getType() { return type; }
    public String getSerial() { return serial; }
    public String getSerialCode() { return serial; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    // ===== Setters / Updates =====
    public void updateStock(int quantity) { this.stock += quantity; }

    // ===== Logic Helpers =====
    public boolean isAvailable() { return stock > 0; }

    // ===== Display Helpers =====
    public String displayInfo() {
        return String.format("%s (%s): ₱%.2f | Stock: %d", name, serial, price, stock);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - ₱%.2f (Stock: %d)", type, name, price, stock);
    }
}
