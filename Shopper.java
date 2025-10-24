import java.util.ArrayList;

public class Shopper {
    // ===== Fields =====
    private String name;
    private int age;
    private int row, col;
    private boolean checkedOut;
    private Direction facing;

    private Equipment currentEquipment;       // basket or cart
    private ArrayList<Product> heldProducts;  // hand-held items
    private Money wallet;

    // ===== Constructor =====
    public Shopper(String name, int age, double initialMoney) {
        this.name = name;
        this.age = age;
        this.wallet = new Money(initialMoney);
        this.heldProducts = new ArrayList<>();
        this.checkedOut = false;
        this.facing = Direction.NORTH; 
    }

    // ===== Getters =====
    public String getName() { return name; }
    public int getAge() { return age; }
    public Money getWallet() { return wallet; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public Equipment getEquipment() { return currentEquipment; }
    public ArrayList<Product> getHeldProducts() { return heldProducts; }
    public Direction getFacing() { return facing; }
    public boolean hasEquipment() { return currentEquipment != null; }
    public ArrayList<Product> getChosenProducts() {
        ArrayList<Product> allProducts = new ArrayList<>(heldProducts);
        if (currentEquipment != null) {
            allProducts.addAll(currentEquipment.getContents());
        }
        return allProducts;
    }
    public double getTotalPrice() {
        double total = 0.0;
        for (Product p : heldProducts) {
            total += p.getPrice();
        }
        if (currentEquipment != null) {
            for (Product p : currentEquipment.getContents()) {
                total += p.getPrice();
            }
        }
        return total;
    }
    public double getDiscountedPrice() {
        // For now, no discount
        return getTotalPrice();
    }
    public boolean addProduct(Product p) {
        if (currentEquipment != null) {
            return currentEquipment.add(p);
        } else if (heldProducts.size() < 2) {
            heldProducts.add(p);
            return true;
        }
        return false;
    }
    public Product removeProduct(String serial) {
        for (Product p : heldProducts) {
            if (p.getSerial().equals(serial)) {
                heldProducts.remove(p);
                return p;
            }
        }
        if (currentEquipment != null) {
            for (Product p : currentEquipment.getContents()) {
                if (p.getSerial().equals(serial)) {
                    currentEquipment.getContents().remove(p);
                    return p;
                }
            }
        }
        return null;
    }
    public boolean canTakeProduct(Product p) {
        if (currentEquipment != null) {
            return currentEquipment.canAdd(p);
        } else {
            return heldProducts.size() < 2;
        }
    }

    // ===== Setters =====
    public void setEquipment(Equipment eq) { this.currentEquipment = eq; }
    public void setPosition(int row, int col) { this.row = row; this.col = col; }

    // ===== Movement =====
    public void move(Direction dir, StoreMap map) {
        int newRow = row;
        int newCol = col;

        switch (dir) {
            case NORTH -> newRow--;
            case SOUTH -> newRow++;
            case WEST -> newCol--;
            case EAST -> newCol++;
        }

        if (map.isWalkable(newRow, newCol)) {
            this.row = newRow;
            this.col = newCol;
            System.out.println("Shopper " + name + " moved " + dir + " to (" + newRow + ", " + newCol + ")");
        } else {
            System.out.println("You can't move there!");
        }
    }

    /**
     * Faces the given direction without moving.
     */
    public void face(Direction dir) {
        this.facing = dir;
        System.out.println(name + " is now facing " + dir);
    }

    // ===== Product Interaction =====
    public boolean pickProduct(Display d, String serial) {
        Product chosen = null;
        for (Product p : d.getProducts()) {
            if (p.getSerial().equals(serial)) {
                chosen = p;
                break;
            }
        }

        if (chosen == null) {
            System.out.println("Product not found on this display.");
            return false;
        }

        if (!AgeCheck.canPurchase(this, chosen)) {
            System.out.println("You are not allowed to buy this product.");
            return false;
        }

        boolean added = false;
        if (currentEquipment != null) {
            added = currentEquipment.add(chosen);
        } else if (heldProducts.size() < 2) { // can carry 2 items by hand
            heldProducts.add(chosen);
            added = true;
        }

        if (added) {
            d.removeProduct(serial);
            System.out.println("Picked up " + chosen.getName() + " (" + chosen.getSerial() + ")");
            return true;
        } else {
            System.out.println("No more space to carry this item!");
            return false;
        }
    }

    // ===== View Inventory =====
    public void viewCart() {
        System.out.println("\n===  SHOPPER INVENTORY  ===");
        double total = 0;

        // Items in hand
        if (!heldProducts.isEmpty()) {
            System.out.println("Items in hand:");
            for (Product p : heldProducts) {
                System.out.println(" - " + p.displayInfo());
                total += p.getPrice();
            }
        }

        // Items in equipment (basket/cart)
        if (currentEquipment != null && currentEquipment.size() > 0) {
            System.out.println("Items in " + currentEquipment.getType() + ":");
            for (Product p : currentEquipment.getContents()) {
                System.out.println(" - " + p.displayInfo());
                total += p.getPrice();
            }
        }

        // Summary
        System.out.printf("Total value of items: ₱%.2f%n", total);
        System.out.printf("Wallet balance: ₱%.2f%n", wallet.getBalance());
        System.out.println("===========================\n");
    }

    // ===== Wallet Actions =====
    public void pay(double amount) {
        wallet.charge(amount);
    }

    public void addMoney(double amount) {
        wallet.addFunds(amount);
    }

    // ===== Miscellaneous =====
    public void clearCart() {
        heldProducts.clear();
        if (currentEquipment != null) {
            currentEquipment.getContents().clear();
        }
    }

    public void checkOut() { checkedOut = true; }
    public boolean isCheckedOut() { return checkedOut; }
}
