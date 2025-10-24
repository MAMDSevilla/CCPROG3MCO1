import java.util.ArrayList;
import java.util.List;

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
                        Display shelf = new Display("SHELF-" + r + "-" + c, DispType.SHELF, 20);
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
                        chilled.setLocation(r, c);
                        grid[r][c].setAmenity(chilled);
                        displays.add(chilled);
                    }
                    case 'θ' -> {
                        CheckoutCounter counter = new CheckoutCounter("CC-" + r + "-" + c, r, c);
                        grid[r][c].setAmenity(counter);
                        services.add(counter);
            }
        }

        // Populate shelves with products
        populateShelves();

        // Populate specific chilled counters with chicken products
        populateChilledCounters();

        // Populate specific chilled counters with beef products
        populateBeefCounters();

        // Populate specific chilled counters with seafood products
        populateSeafoodCounters();

        // Populate specific shelves with alcohol products
        populateAlcoholShelves();

        // Populate specific shelves with condiment products
        populateCondimentShelves();
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

    /**
     * Populates specific chilled counters in row 1, columns 1-6 with chicken products.
     */
    private void populateChilledCounters() {
        // Find chilled counters in row 1, columns 1-6 (0-indexed: row 1, cols 1-6)
        List<Product> chickenProducts = new ArrayList<>();
        for (Product p : ProductType.getAllProducts()) {
            if (p.getType() == ProductType.CHICKEN) {
                chickenProducts.add(p);
            }
        }
        for (Display d : displays) {
            if (d.getType() == DispType.CHILLED_COUNTER && d.getRow() == 1 && d.getCol() >= 1 && d.getCol() <= 6) {
                // Add up to 4 of each chicken product to these displays
                for (Product template : chickenProducts) {
                    for (int i = 0; i < 4; i++) {
                        Product p = new Product(template.getName(), template.getType(), template.getPrice(), template.getStock());
                        if (!d.addProduct(p)) break;
                    }
                }
            }
        }
    }

    /**
     * Populates specific chilled counters in row 1, columns 8-13 with beef products.
     */
    private void populateBeefCounters() {
        // Find chilled counters in row 1, columns 8-13 (0-indexed: row 1, cols 8-13)
        List<Product> beefProducts = new ArrayList<>();
        for (Product p : ProductType.getAllProducts()) {
            if (p.getType() == ProductType.BEEF) {
                beefProducts.add(p);
            }
        }
        for (Display d : displays) {
            if (d.getType() == DispType.CHILLED_COUNTER && d.getRow() == 1 && d.getCol() >= 8 && d.getCol() <= 13) {
                // Add up to 4 of each beef product to these displays
                for (Product template : beefProducts) {
                    for (int i = 0; i < 4; i++) {
                        Product p = new Product(template.getName(), template.getType(), template.getPrice(), template.getStock());
                        if (!d.addProduct(p)) break;
                    }
                }
            }
        }
    }

    /**
     * Populates specific chilled counters in row 1, columns 15-20 with seafood products.
     */
    private void populateSeafoodCounters() {
        // Find chilled counters in row 1, columns 15-20 (0-indexed: row 1, cols 15-20)
        List<Product> seafoodProducts = new ArrayList<>();
        for (Product p : ProductType.getAllProducts()) {
            if (p.getType() == ProductType.SEAFOOD) {
                seafoodProducts.add(p);
            }
        }
        for (Display d : displays) {
            if (d.getType() == DispType.CHILLED_COUNTER && d.getRow() == 1 && d.getCol() >= 15 && d.getCol() <= 20) {
                // Add up to 4 of each seafood product to these displays
                for (Product template : seafoodProducts) {
                    for (int i = 0; i < 4; i++) {
                        Product p = new Product(template.getName(), template.getType(), template.getPrice(), template.getStock());
                        if (!d.addProduct(p)) break;
                    }
                }
            }
        }
    }

    /**
     * Populates specific shelves in rows 4-7, columns 2-3 with alcohol products.
     */
    private void populateAlcoholShelves() {
        // Find shelves in rows 4-7, columns 2-3 (0-indexed: rows 4-7, cols 2-3)
        for (Display d : displays) {
            if (d.getType() == DispType.SHELF && d.getRow() >= 4 && d.getRow() <= 7 && d.getCol() >= 2 && d.getCol() <= 3) {
                // Add alcohol products to these displays
                Product beer = new Product("Beer", ProductType.ALCOHOL, 50.0, 10);
                Product vodka = new Product("Vodka", ProductType.ALCOHOL, 300.0, 5);
                Product soju = new Product("Soju", ProductType.ALCOHOL, 80.0, 10);
                d.addProduct(beer);
                d.addProduct(vodka);
                d.addProduct(soju);
            }
        }
    }

    /**
     * Populates specific shelves in rows 10-13, columns 2-3 with condiment products.
     */
    private void populateCondimentShelves() {
        // Find shelves in rows 10-13, columns 2-3 (0-indexed: rows 10-13, cols 2-3)
        for (Display d : displays) {
            if (d.getType() == DispType.SHELF && d.getRow() >= 10 && d.getRow() <= 13 && d.getCol() >= 2 && d.getCol() <= 3) {
                // Add condiment products to these displays
                Product salt = new Product("Salt", ProductType.CONDIMENTS, 10.0, 50);
                Product pepper = new Product("Pepper", ProductType.CONDIMENTS, 15.0, 50);
                Product paprika = new Product("Paprika", ProductType.CONDIMENTS, 20.0, 30);
                d.addProduct(salt);
                d.addProduct(pepper);
                d.addProduct(paprika);
            }
        }
    }

    /**
     * Populates shelf displays with products from the provided table.
     */
    private void populateShelves() {
        // Get all shelf displays, excluding specific alcohol and condiment shelves
        ArrayList<Display> shelves = new ArrayList<>();
        for (Display d : displays) {
            if (d.getType() == DispType.SHELF &&
                !((d.getRow() >= 4 && d.getRow() <= 7 && d.getCol() >= 2 && d.getCol() <= 3) ||
                  (d.getRow() >= 10 && d.getRow() <= 13 && d.getCol() >= 2 && d.getCol() <= 3))) {
                shelves.add(d);
            }
        }

        if (shelves.isEmpty()) {
            return; // No shelves to populate
        }

        // Product data: name, type, price, stock
        Object[][] productData = {
            {"Thigh fillet", ProductType.CHICKEN, 150.0, 5},
            {"Breast fillet", ProductType.CHICKEN, 160.0, 5},
            {"Ground", ProductType.CHICKEN, 140.0, 5},
            {"Rib", ProductType.BEEF, 200.0, 5},
            {"Shank", ProductType.BEEF, 180.0, 5},
            {"Ground", ProductType.BEEF, 170.0, 5},
            {"Tilapia", ProductType.SEAFOOD, 120.0, 5},
            {"Sugpo", ProductType.SEAFOOD, 250.0, 5},
            {"Squid", ProductType.SEAFOOD, 130.0, 5},
            {"Beer", ProductType.ALCOHOL, 50.0, 10},
            {"Vodka", ProductType.ALCOHOL, 300.0, 5},
            {"Soju", ProductType.ALCOHOL, 80.0, 10},
            {"Cola", ProductType.SOFT_DRINK, 25.0, 20},
            {"Soda", ProductType.SOFT_DRINK, 20.0, 20},
            {"Sparkling water", ProductType.SOFT_DRINK, 30.0, 15},
            {"Apple", ProductType.FRUIT, 15.0, 30},
            {"Orange", ProductType.FRUIT, 12.0, 30},
            {"Grapes", ProductType.FRUIT, 50.0, 10},
            {"Oatmeal", ProductType.CEREAL, 80.0, 10},
            {"Barley", ProductType.CEREAL, 70.0, 10},
            {"Quinoa", ProductType.CEREAL, 90.0, 10},
            {"Tuna", ProductType.CANNED_GOODS, 60.0, 15},
            {"Sardines", ProductType.CANNED_GOODS, 40.0, 15},
            {"Soup", ProductType.CANNED_GOODS, 35.0, 15},
            {"Salt", ProductType.CONDIMENTS, 10.0, 50},
            {"Pepper", ProductType.CONDIMENTS, 15.0, 50},
            {"Paprika", ProductType.CONDIMENTS, 20.0, 30},
            {"Orange", ProductType.JUICE, 45.0, 20},
            {"Pineapple", ProductType.JUICE, 40.0, 20},
            {"Apple", ProductType.JUICE, 42.0, 20},
            {"Ramen", ProductType.NOODLES, 25.0, 25},
            {"Miswa", ProductType.NOODLES, 20.0, 25},
            {"Instant noodles", ProductType.NOODLES, 15.0, 30},
            {"Candies", ProductType.SNACKS, 5.0, 100},
            {"Cookies", ProductType.SNACKS, 30.0, 20},
            {"Junk food", ProductType.SNACKS, 10.0, 50}
        };

        int shelfIndex = 0;
        for (Object[] data : productData) {
            String name = (String) data[0];
            ProductType type = (ProductType) data[1];
            double price = (Double) data[2];
            int stock = (Integer) data[3];

            Product p = new Product(name, type, price, stock);

            // Add to next shelf, cycle through shelves
            Display shelf = shelves.get(shelfIndex % shelves.size());
            shelf.addProduct(p);
            shelfIndex++;
        }
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
        System.out.println("C : Chilled Counter | S : Shelf         | T : Table             | E : Entrance    | X : Exit");
        System.out.println("\nMovement: W A S D   | Look: I J K L     | Interact: Space Bar   | V: View         | Q: Quit");

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
