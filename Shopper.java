import java.util.ArrayList;
import java.util.List;

public class Shopper {
    private String name;
    private int age;
    private Equipment equipment;
    private List<Product> handCarried = new ArrayList<>();
    private int row, col;
    private Direction facing = Direction.SOUTH;
    private boolean checkedOut = false;

    public Shopper(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public List<Product> getChosenProducts() {
        return equipment != null ? equipment.getProducts() : handCarried;
    }

    public boolean addProduct(Product p) {
        if (equipment != null) {
            return equipment.addProduct(p);
        } else {
            if (handCarried.size() >= 2) return false;
            handCarried.add(p);
            return true;
        }
    }

    public Product removeProduct(String serial) {
        List<Product> prods = getChosenProducts();
        for (int i = 0; i < prods.size(); i++) {
            if (prods.get(i).getSerialCode().equals(serial)) {
                return prods.remove(i);
            }
        }
        return null;
    }

    public boolean canTakeProduct(Product p) {
        return !(age < 18 && p.getType().isRestrictedForMinors());
    }

    public boolean hasEquipment() {
        return equipment != null;
    }

    public void setEquipment(Equipment eq) {
        this.equipment = eq;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void checkOut() {
        checkedOut = true;
        equipment = null;
        handCarried.clear();
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product p : getChosenProducts()) {
            total += p.getPrice();
        }
        return total;
    }

    public double getDiscountedPrice() {
        if (age < 60) return getTotalPrice();
        double foodTotal = 0, bevTotal = 0;
        for (Product p : getChosenProducts()) {
            ProductType type = p.getType();
            if (type.isNoSeniorDiscount()) continue;
            double pr = p.getPrice();
            if (type.isFood()) foodTotal += pr;
            else if (type.isBeverage()) bevTotal += pr;
        }
        return getTotalPrice() - 0.2 * foodTotal - 0.1 * bevTotal;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setPosition(int row, int col) { this.row = row; this.col = col; }
    public Direction getFacing() { return facing; }
    public void setFacing(Direction facing) { this.facing = facing; }
    public String getName() { return name; }
    public int getAge() { return age; }
}