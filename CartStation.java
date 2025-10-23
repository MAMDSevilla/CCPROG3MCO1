public class CartStation extends Service {
    @Override
    public void interact(Shopper shopper, Map map) {
        if (!shopper.hasEquipment() && shopper.getChosenProducts().isEmpty() && !shopper.isCheckedOut()) {
            shopper.setEquipment(new Cart());
            System.out.println("Got cart.");
        } else if (shopper.getEquipment() instanceof Cart && shopper.getEquipment().isEmpty()) {
            shopper.setEquipment(null);
            System.out.println("Returned cart.");
        } else {
            System.out.println("Cannot interact with cart station.");
        }
    }
}