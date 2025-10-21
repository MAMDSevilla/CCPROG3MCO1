import java.util.ArrayList;
import java.util.List;

public abstract class Display {
    private final String address;
    private final List<List<Product>> tiers = new ArrayList<>();

    public Display(String address) {
        this.address = address;
        int n = getDisplayType().getNumTiers();
        for (int i = 0; i < n; i++) {
            tiers.add(new ArrayList<>(getDisplayType().getCapacityPerTier()));
        }
    }

    public abstract DisplayType getDisplayType();

    public boolean isFull() {
        return tiers.stream().allMatch(t -> t.size() >= getDisplayType().getCapacityPerTier());
    }

    public boolean addProduct(Product p, int tierIdx) {
        if (!p.getType().getDisplayType().equals(getDisplayType())) return false;
        if (tierIdx < 0 || tierIdx >= tiers.size()) return false;
        List<Product> tier = tiers.get(tierIdx);
        if (tier.size() >= getDisplayType().getCapacityPerTier()) return false;
        tier.add(p);
        return true;
    }

    public Product removeProduct(String serial, int tierIdx) {
        if (tierIdx < 0 || tierIdx >= tiers.size()) return null;
        List<Product> tier = tiers.get(tierIdx);
        return tier.removeIf(pr -> pr.getSerialCode().equals(serial)) ? tier.stream()
                .filter(pr -> pr.getSerialCode().equals(serial)).findFirst().orElse(null) : null;
    }

    public List<Product> getAllProducts() {
        return tiers.stream().flatMap(List::stream).toList();
    }

    public boolean containsProduct(String name) {
        return getAllProducts().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }

    public String getAddress() { return address; }
    public List<List<Product>> getTiers() { return tiers; }
}