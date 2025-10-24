import java.util.concurrent.atomic.AtomicInteger;

public class Product {
    // ===== Fields =====
    private static final AtomicInteger serialCounter = new AtomicInteger(1000); // for auto serials

    private String name;
    private ProductType type;
    private String serial;
    private double price;
    private int stock;

    // ===== Constructor =====
    public Product(String name, ProductType type, double price, int stock) {
        this.name = name;
        this.type = type;
        this.serial = generateSerial(type);
        this.price = price;
        this.stock = stock;
    }

    // ===== Serial Generation =====
    /**
     * Automatically generates a unique serial number per product type.
     * Example: ALCOHOL → "ALC-1001", GROCERY → "GRC-1002"
     */
    private String generateSerial(ProductType type) {
        String prefix = type.name().substring(0, Math.min(3, type.name().length())).toUpperCase();
        return prefix + "-" + serialCounter.getAndIncrement();
    }

    // ===== Getters =====
    public String getName() { return name; }
    public String getProductName() { return name; } // alias for older code
    public ProductType getType() { return type; }
    public String getSerial() { return serial; }
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
