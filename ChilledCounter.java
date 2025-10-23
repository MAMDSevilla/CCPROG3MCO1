public class ChilledCounter extends Display {
    public ChilledCounter(String address) {
        super(address);
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.CHILLED_COUNTER;
    }
}
