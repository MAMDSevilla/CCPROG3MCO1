import java.util.ArrayList;
import java.util.List;

public abstract class Equipment {
    protected List<Product> products = new ArrayList<>();
    protected int capacity;

    public boolean addProduct(Product p) {
        if (products.size() >= capacity) return false;
        products.add(p);
        return true;
    }

    public Product removeProduct(String serial) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getSerialCode().equals(serial)) {
                return products.remove(i);
            }
        }
        return null;
    }

    public List<Product> getProducts() { return products; }
    public boolean isEmpty() { return products.isEmpty(); }
}