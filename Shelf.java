public class Shelf extends Display {
    public Shelf(String address) { super(address, DispType.SHELF, 20); }
    @Override public DispType getDisplayType() { return DispType.SHELF; }
}
