public class Table extends Display {
    public Table(String address) {
        super(address, DispType.TABLE, 10);
    }

    @Override
    public DispType getDisplayType() {
        return DispType.TABLE;
    }
}
