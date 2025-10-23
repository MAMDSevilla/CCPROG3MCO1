public class Table extends Display {
    public Table(String address) {
        super(address);
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.TABLE;
    }
}