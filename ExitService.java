public class ExitService extends Service {
    @Override
    public void interact(Shopper shopper, Map map) {
        if ((shopper.isCheckedOut() || shopper.getChosenProducts().isEmpty()) && !shopper.hasEquipment()) {
            map.setRunning(false);
            System.out.println("Exiting supermarket.");
        } else {
            System.out.println("Cannot exit.");
        }
    }
}