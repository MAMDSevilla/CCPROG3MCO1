import java.util.HashMap;
import java.util.Map;

public class Product {
    private static Map<ProductType, Integer> counters = new HashMap<>();

    private String serialCode;
    private String name;
    private double price;

    public Product(ProductType type, String name, double price) {
        this.name = name;
        this.price = price;
        int id = counters.getOrDefault(type, 0) + 1;
        counters.put(type, id);
        serialCode = type.getPrefix() + String.format("%05d", id);
    }

    public String getSerialCode() { return serialCode; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public ProductType getType() { return ProductType.fromPrefix(serialCode); }
}