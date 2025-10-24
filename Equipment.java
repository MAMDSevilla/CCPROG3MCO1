import java.util.ArrayList;

public class Equipment {
    private String eqType;
    private int capacity;
    private ArrayList<Product> contents;

    public Equipment(String eqType, int capacity) {
        this.eqType = eqType;
        this.capacity = capacity;
        this.contents = new ArrayList<>();
    }

    public boolean add(Product p) {
        if (contents.size() < capacity) {
            contents.add(p);
            return true;
        }
        return false;
    }

    public boolean remove(Product p) {
        return contents.remove(p);
    }

    public ArrayList<Product> getContents() {
        return contents;
    }

    public int size() {
        return contents.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return eqType;
    }

    public void clear() {
        contents.clear();
    }

    @Override
    public String toString() {
        return eqType + " (" + contents.size() + "/" + capacity + ")";
    }
}