public class BasketStation extends Service {
    @Override
    public void interact(Shopper shopper, Map map) {
        if (!shopper.hasEquipment() && shopper.getChosenProducts().isEmpty() && !shopper.isCheckedOut()) {
            shopper.setEquipment(new Basket());
            System.out.println("Got basket.");
        } else if (shopper.getEquipment() instanceof Basket && shopper.getEquipment().isEmpty()) {
            shopper.setEquipment(null);
            System.out.println("Returned basket.");
        } else {
            System.out.println("Cannot interact with basket station.");
        }
    }
}