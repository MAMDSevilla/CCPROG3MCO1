import java.util.Scanner;

/**
 * Handles all keyboard input and interactions for the shopper.
 * Controls movement, direction, and interactions with the environment.
 */
public class UserInput {
    private Scanner sc;
    private StoreMap map;
    private Shopper shopper;

    public UserInput(StoreMap map, Shopper shopper) {
        this.map = map;
        this.shopper = shopper;
        this.sc = new Scanner(System.in);
    }

    /**
     * Main input loop.
     */
    public void start() {
        boolean running = true;
        System.out.println("\n===== SUPERMARKET SIMULATION CONTROLS =====");
        System.out.println("Movement: W A S D");
        System.out.println("Look: I J K L");
        System.out.println("Interact: Space Bar");
        System.out.println("View: V");
        System.out.println("Quit: Q");
        System.out.println("==========================================\n");

        while (running) {
            map.render();
            System.out.print("Command: ");
            String cmd = sc.nextLine().trim().toUpperCase();

            switch (cmd) {
                // --- MOVEMENT CONTROLS ---
                case "W":
                    shopper.move(Direction.NORTH, map);
                    break;
                case "A":
                    shopper.move(Direction.WEST, map);
                    break;
                case "S":
                    shopper.move(Direction.SOUTH, map);
                    break;
                case "D":
                    shopper.move(Direction.EAST, map);
                    break;

                // --- FACING CONTROLS (LOOK) ---
                case "I":
                    shopper.face(Direction.NORTH);
                    break;
                case "J":
                    shopper.face(Direction.WEST);
                    break;
                case "K":
                    shopper.face(Direction.SOUTH);
                    break;
                case "L":
                    shopper.face(Direction.EAST);
                    break;

                // --- INTERACTION ---
                case "":
                    interactWithFrontTile();
                    break;

                // --- VIEW CART ---
                case "V":
                    shopper.viewCart();
                    break;

                // --- QUIT ---
                case "Q":
                    System.out.println("Exiting the supermarket simulation. See you next time!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid command. Try again!");
            }
        }
    }

    /**
     * Finds the tile in front of the shopper and interacts with its amenity if any.
     */
    private void interactWithFrontTile() {
        int r = shopper.getRow();
        int c = shopper.getCol();

        switch (shopper.getFacing()) {
            case NORTH -> r--;
            case SOUTH -> r++;
            case EAST -> c++;
            case WEST -> c--;
        }

        if (!map.isWithinBounds(r, c)) {
            System.out.println("Can't interact outside the map!");
            return;
        }

        TileAndIcon target = map.getGrid()[r][c];
        Object amenity = target.getAmenity();

        if (amenity == null) {
            System.out.println("There's nothing to interact with here.");
            return;
        }

        // Dynamically handle different amenity types
        if (amenity instanceof Display d) {
            System.out.println("You are looking at Display: " + d.getId());
            d.listProducts();
        } else if (amenity instanceof EquipmentStation e) {
            e.interact(shopper);
        } else if (amenity instanceof Accessway a) {
            a.interact(shopper, map);
        } else if (amenity instanceof ProductSearch ps) {
            ps.interact(shopper);
        } else if (amenity instanceof CheckoutCounter cc) {
            cc.interact(shopper);
        } else if (amenity instanceof ATM atm) {
            atm.interact(shopper);
        } else {
            System.out.println("Unknown amenity type: " + amenity.getClass().getSimpleName());
        }
    }
}
