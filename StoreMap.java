import java.util.ArrayList;

/**
 * Represents the supermarket’s ground floor layout.
 * Handles map generation, placement of displays and services,
 * and rendering to console.
 */
public class StoreMap {
    private final int rows = 22;
    private final int cols = 22;

    private TileAndIcon[][] grid;
    private ArrayList<Display> displays;
    private ArrayList<Object> services; 
    private Shopper shopper;

    // ===== Constructor =====
    public StoreMap() {
        this.grid = new TileAndIcon[rows][cols];
        this.displays = new ArrayList<>();
        this.services = new ArrayList<>();
    }

    // =============================================================
    // MAP INITIALIZATION
    // =============================================================

    public void initializeLayout() {
        char[][] layout = {
            "######################".toCharArray(),
            "#CCCCCC◌CCCCCC◌CCCCCC#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌SS◌◌SS◌◌TT◌◌SS◌◌SS◌#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "#◌◌◌◌◌◌◌P◌◌◌◌P◌◌◌◌◌◌◌#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "#◌◌◌◌◌◌◌◌◌##◌◌◌◌◌◌◌◌◌#".toCharArray(),
            "##θ#θ#θ#θ◌##◌θ#θ#θ#θ##".toCharArray(),
            "#M◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌M#".toCharArray(),
            "#▽◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌▯#".toCharArray(),
            "##########XE##########".toCharArray(),
        };

        // fill grid based on layout
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = layout[r][c];
                boolean walkable = switch (ch) {
                    case '#', 'C', 'S', 'T', 'θ' -> false;
                    default -> true;
                };

                grid[r][c] = new TileAndIcon(walkable, ch);

                // detect and create amenities/services dynamically
                switch (ch) {
                    case 'E' -> {
                        Accessway entrance = new Accessway("EN1", r, c, AccesswayType.ENTRANCE);
                        grid[r][c].setAmenity(entrance);
                        services.add(entrance);
                    }
                    case 'X' -> {
                        Accessway exit = new Accessway("EX1", r, c, AccesswayType.EXIT);
                        grid[r][c].setAmenity(exit);
                        services.add(exit);
                    }
                    case '▽' -> {
                        EquipmentStation basketStation = new EquipmentStation("ES1", r, c, 5, EquipmentType.BASKET);
                        grid[r][c].setAmenity(basketStation);
                        services.add(basketStation);
                    }
                    case '▯' -> {
                        EquipmentStation cartStation = new EquipmentStation("ES2", r, c, 5, EquipmentType.CART);
                        grid[r][c].setAmenity(cartStation);
                        services.add(cartStation);
                    }
                    case 'M' -> {
                        ATM atm = new ATM("ATM" + r + c, r, c);
                        grid[r][c].setAmenity(atm);
                        services.add(atm);
                    }
                    case 'P' -> {
                        ProductSearch ps = new ProductSearch(this);
                        grid[r][c].setAmenity(ps);
                        services.add(ps);
                    }
                    case 'S' -> {
                        Display shelf = new Display("SHELF-" + r + "-" + c, DispType.SHELF, 10);
                        grid[r][c].setAmenity(shelf);
                        displays.add(shelf);
                    }
                    case 'T' -> {
                        Display table = new Display("TABLE-" + r + "-" + c, DispType.TABLE, 10);
                        grid[r][c].setAmenity(table);
                        displays.add(table);
                    }
                    case 'C' -> {
                        Display chilled = new Display("CHILL-" + r + "-" + c, DispType.CHILLED_COUNTER, 10);
                        grid[r][c].setAmenity(chilled);
                        displays.add(chilled);
                    }
                    case 'θ' -> {
                        CheckoutCounter counter = new CheckoutCounter("CC-" + r + "-" + c, r, c);
                        grid[r][c].setAmenity(counter);
                        services.add(counter);
                    }
                }
            }
        }
    }

    // =============================================================
    // HELPER METHODS
    // =============================================================

    /**
     * Adds a rectangular group of display tiles (e.g., shelves, tables).
     */
    private void addDisplayBlock(int startRow, int startCol, int height, int width, DispType type) {
        for (int r = startRow; r < startRow + height; r++) {
            for (int c = startCol; c < startCol + width; c++) {
                Display d = new Display("D-" + type + "-" + r + "-" + c, type, 10);
                placeAmenity(r, c, d);
                displays.add(d);
            }
        }
    }

    /**
     * Places a service or display on the map grid.
     */
    private void placeAmenity(int row, int col, Object amenity) {
        char symbol = TileAndIcon.getIconFor(amenity);

        // Decide whether the amenity should be passable (walkable)
        boolean passable = false;
        if (amenity instanceof Accessway) {
            passable = true;
        } else if (amenity instanceof ProductSearch) {
            passable = true;
        } else if (amenity instanceof EquipmentStation) {
            passable = true;
        } else if (amenity instanceof ATM) {
            passable = true;
        } else {
            passable = false;
        }

        TileAndIcon tile = new TileAndIcon(passable, symbol);
        tile.setAmenity(amenity);
        grid[row][col] = tile;
    }
    // =============================================================
    // MAP RENDERING
    // =============================================================

    /**
     * Prints the map to the console, showing all tiles and the shopper’s position.
     */
    public void render() {
        // Legend
        System.out.println("\n=== LEGEND ===");
        System.out.println("# : Wall            | ◌ : Floor         | θ : Checkout Counter  | ○ : Shopper");
        System.out.println("▽ : Basket Station  | ▯ : Cart Station  | M : ATM               | P : Product Search");
        System.out.println("C : Chilled Counter | S : Shelf         | T : Table             | E : Entrance  | X : Exit");

        // Current balance and grocery total
        if (shopper != null) {
            double balance = shopper.getWallet().getBalance();
            double groceryTotal = 0.0;
            // Sum from held products
            for (Product p : shopper.getHeldProducts()) {
                groceryTotal += p.getPrice();
            }
            // Sum from equipment
            if (shopper.getEquipment() != null) {
                for (Product p : shopper.getEquipment().getContents()) {
                    groceryTotal += p.getPrice();
                }
            }
            System.out.printf("\nCurrent Wallet Balance: ₱%.2f%n", balance);
            System.out.printf("Current Grocery Total: ₱%.2f \t\tCurrent Facing Direction: %s%n", groceryTotal, shopper.getFacing());
        }

        System.out.println("\n===== SUPERMARKET MAP (Ground Floor) =====");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (shopper != null && shopper.getRow() == r && shopper.getCol() == c) {
                    // ANSI escape codes — green shopper icon
                    final String GREEN = "\u001B[32m";
                    final String RESET = "\u001B[0m";
                    System.out.print(GREEN + TileAndIcon.IconType.SHOPPER.getSymbol() + RESET + " ");
                } else {
                    System.out.print(grid[r][c].getIcon() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("==========================================");
    }

    // =============================================================
    // MOVEMENT / TILE CHECKS
    // =============================================================

    /**
     * Checks if a tile can be walked on by the shopper.
     */
    public boolean isWalkable(int r, int c) {
        if (!isWithinBounds(r, c)) return false;
        return grid[r][c].isWalkable();
    }

    /**
     * Returns true if (r, c) is inside the map.
     */
    public boolean isWithinBounds(int r, int c) {
        return r >= 0 && c >= 0 && r < rows && c < cols;
    }

    // =============================================================
    // GETTERS / SETTERS
    // =============================================================

    public void setShopper(Shopper s) { this.shopper = s; }
    public Shopper getShopper() { return shopper; }
    public ArrayList<Display> getDisplays() { return displays; }
    public ArrayList<Object> getServices() { return services; }
    public TileAndIcon[][] getGrid() { return grid; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
