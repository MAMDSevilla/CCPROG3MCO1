public class EquipmentStation {
    private String id;
    private int row, col;
    private int availableUnits;
    private EquipmentType type;

    public EquipmentStation(String id, int row, int col, int availableUnits, EquipmentType type) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.availableUnits = availableUnits;
        this.type = type;
    }

    public void interact(Shopper s) {
        if (availableUnits <= 0) {
            System.out.println("No more " + type.toString().toLowerCase() + "s available at this station.");
            return;
        }

        switch (type) {
            case CART -> {
                s.setEquipment(new Cart()); // use Cart class with updated capacity
                System.out.println(s.getName() + " picked up a shopping cart!");
            }
            case BASKET -> {
                s.setEquipment(new Basket()); // use Basket class with updated capacity
                System.out.println(s.getName() + " picked up a shopping basket!");
            }
        }

        availableUnits--;
    }

    // Getters
    public String getId() { return id; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public EquipmentType getType() { return type; }
    public int getAvailableUnits() { return availableUnits; }

    // Optional helper
    public void restock(int units) {
        availableUnits += units;
    }
}