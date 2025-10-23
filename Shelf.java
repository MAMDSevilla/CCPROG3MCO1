public class Shelf extends Display {
    public Shelf(String address) { super(address); }
    @Override public DisplayType getDisplayType() { return DisplayType.SHELF; }
}