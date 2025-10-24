public class Refrigerator extends Display {
    public Refrigerator(String address) {
        super(address, DispType.REFRIGERATOR, 10);
    }

    @Override
    public DispType getDisplayType() {
        return DispType.REFRIGERATOR;
    }
}
