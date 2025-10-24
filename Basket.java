public class Basket {
    private Equipment equipment;

    public Basket() {
        this.equipment = new Equipment("Basket", 15);
    }

    public boolean add(Product p) {
        return equipment.add(p);
    }

    public boolean remove(Product p) {
        return equipment.remove(p);
    }

    public int size() {
        return equipment.size();
    }

    public int getCapacity() {
        return equipment.getCapacity();
    }

    public String getType() {
        return equipment.getType();
    }

    public Equipment getInnerEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return equipment.toString();
    }
}
