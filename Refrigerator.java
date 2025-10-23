public class Refrigerator extends Display {
    public Refrigerator(String address) {
        super(address);
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.REFRIGERATOR;
    }
}
