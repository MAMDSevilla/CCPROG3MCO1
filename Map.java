
// Map.java
import java.util.*;

public class Map {
    private static final int ROWS = 22;
    private static final int COLS = 22;
    private final Tile[][] tiles = new Tile[ROWS][COLS];
    private Shopper shopper;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private boolean running = true;

    private final java.util.Map<ProductType, String[]> productNames = new HashMap<>();
    private final java.util.Map<ProductType, double[]> priceRanges = new HashMap<>();

    public Map() {
        initializeProductData();
        initializeMap();
        stockDisplays();
    }

    private void initializeProductData() {
        productNames.put(ProductType.CHICKEN, new String[]{"Thigh fillet", "Breast fillet", "Ground", "Wings", "Drumsticks"});
        productNames.put(ProductType.BEEF, new String[]{"Rib", "Shank", "Ground", "Sirloin", "Brisket"});
        productNames.put(ProductType.SEAFOOD, new String[]{"Tilapia", "Sugpo", "Squid", "Salmon", "Tuna"});
        productNames.put(ProductType.ALCOHOL, new String[]{"Beer", "Vodka", "Soju", "Whiskey", "Wine"});
        productNames.put(ProductType.SOFT_DRINK, new String[]{"Cola", "Soda", "Sparkling water", "Lemonade", "Iced tea"});
        productNames.put(ProductType.FRUIT, new String[]{"Apple", "Orange", "Grapes", "Banana", "Mango"});
        productNames.put(ProductType.CEREAL, new String[]{"Oatmeal", "Barley", "Quinoa", "Cornflakes", "Rice puffs"});
        productNames.put(ProductType.CANNED_GOODS, new String[]{"Tuna", "Sardines", "Soup", "Beans", "Corn"});
        productNames.put(ProductType.CONDIMENTS, new String[]{"Salt", "Pepper", "Paprika", "Soy sauce", "Vinegar"});
        productNames.put(ProductType.JUICE, new String[]{"Orange", "Pineapple", "Apple", "Grape", "Mango"});
        productNames.put(ProductType.NOODLES, new String[]{"Ramen", "Miswa", "Instant noodles", "Udon", "Soba"});
        productNames.put(ProductType.SNACKS, new String[]{"Candies", "Cookies", "Junk food", "Chips", "Popcorn"});

        priceRanges.put(ProductType.CHICKEN, new double[]{100, 250});
        priceRanges.put(ProductType.BEEF, new double[]{200, 500});
        priceRanges.put(ProductType.SEAFOOD, new double[]{150, 400});
        priceRanges.put(ProductType.ALCOHOL, new double[]{50, 200});
        priceRanges.put(ProductType.SOFT_DRINK, new double[]{20, 50});
        priceRanges.put(ProductType.FRUIT, new double[]{15, 70});
        priceRanges.put(ProductType.CEREAL, new double[]{50, 150});
        priceRanges.put(ProductType.CANNED_GOODS, new double[]{30, 100});
        priceRanges.put(ProductType.CONDIMENTS, new double[]{10, 50});
        priceRanges.put(ProductType.JUICE, new double[]{25, 60});
        priceRanges.put(ProductType.NOODLES, new double[]{15, 40});
        priceRanges.put(ProductType.SNACKS, new double[]{5, 30});
    }

    private void initializeMap() {
        for (int r = 0; r < ROWS; r++) for (int c = 0; c < COLS; c++) tiles[r][c] = new Tile();

        for (int c = 0; c < COLS; c++) { 
            tiles[0][c] = new Tile(Tile.Type.WALL, null); 
            tiles[ROWS-1][c] = new Tile(Tile.Type.WALL, null); 
        }

        for (int r = 0; r < ROWS; r++) { 
            tiles[r][0] = new Tile(Tile.Type.WALL, null); 
            tiles[r][COLS-1] = new Tile(Tile.Type.WALL, null); 
        }

        tiles[21][11] = new Tile(Tile.Type.SERVICE, new EntranceService());
        tiles[21][9] = new Tile(Tile.Type.SERVICE, new ExitService());
        tiles[20][1] = new Tile(Tile.Type.SERVICE, new BasketStation());
        tiles[20][20] = new Tile(Tile.Type.SERVICE, new CartStation());
        tiles[15][8] = new Tile(Tile.Type.SERVICE, new ProductSearchService());
        tiles[15][13] = new Tile(Tile.Type.SERVICE, new ProductSearchService());

        int[] cols = {2,4,6,8,13,15,17,19};
        for (int col : cols) {
            tiles[18][col] = new Tile(Tile.Type.SERVICE, new CheckoutService());
        }

        // Add walls in row 18 at specified columns
        int[] wallCols = {1,3,5,7,10,11,14,16,18,20};
        for (int col : wallCols) {
            tiles[18][col] = new Tile(Tile.Type.WALL, null);
        }

        // Add walls in row 17 at columns 10 and 11
        tiles[17][10] = new Tile(Tile.Type.WALL, null);
        tiles[17][11] = new Tile(Tile.Type.WALL, null);

        // Tables for MCO1
        createTableRow(4, 7, 17, "GF, Aisle 3, Table 1 ", ProductType.FRUIT);
        createTableRow(4, 7, 19, "GF, Aisle 3, Table 2 ", ProductType.CEREAL);
        createTableRow(4, 7, 21, "GF, Aisle 3, Table 3 ", ProductType.NOODLES);

        // Shelves for MCO1
        createShelfRow(4, 7, 5, "GF, Aisle 1, Shelf ", ProductType.ALCOHOL);
        createShelfRow(9, 12, 7, "GF, Aisle 5, Shelf ", ProductType.CANNED_GOODS);

        // New shelves for condiments in rows 2-3, columns 10-13
        for (int r = 3; r <= 4; r++) {
            for (int c = 10; c <= 13; c++) {
                tiles[r][c] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Aisle 4, Shelf " + (c - 9)));
            }
        }

        // Additional shelves in rows 4-7 and 10-13, columns 2,3,6,7,14,15,18,19
        // Row 4-7: col 2,3 alcohol; col 6,7 softdrinks; col 14,15 cereal; col 18,19 canned_goods
        for (int r = 4; r <= 7; r++) {
            tiles[r][2] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 2, Shelf " + (r - 3)));
            tiles[r][3] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 2, Shelf " + (r - 3)));
            tiles[r][6] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 3, Shelf " + (r - 3)));
            tiles[r][7] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 3, Shelf " + (r - 3)));
            tiles[r][14] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 6, Shelf " + (r - 3)));
            tiles[r][15] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 6, Shelf " + (r - 3)));
            tiles[r][18] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 7, Shelf " + (r - 3)));
            tiles[r][19] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 4-7, Aisle 7, Shelf " + (r - 3)));
        }
        // Row 10-13: col 2,3 condiments; col 6,7 juice; col 14,15 noodles; col 18,19 snacks
        for (int r = 10; r <= 13; r++) {
            tiles[r][2] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 2, Shelf " + (r - 9)));
            tiles[r][3] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 2, Shelf " + (r - 9)));
            tiles[r][6] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 3, Shelf " + (r - 9)));
            tiles[r][7] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 3, Shelf " + (r - 9)));
            tiles[r][14] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 6, Shelf " + (r - 9)));
            tiles[r][15] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 6, Shelf " + (r - 9)));
            tiles[r][18] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 7, Shelf " + (r - 9)));
            tiles[r][19] = new Tile(Tile.Type.DISPLAY, new Shelf("GF, Row 10-13, Aisle 7, Shelf " + (r - 9)));
        }

        // Chilled Counters for MCO1
        int counterNum = 1;
        for (int c = 1; c <= 6; c++) {
            tiles[1][c] = new Tile(Tile.Type.DISPLAY, new ChilledCounter("GF, Wall 1, Chilled Counter " + counterNum));
            counterNum++;
        }
        for (int c = 8; c <= 13; c++) {
            tiles[1][c] = new Tile(Tile.Type.DISPLAY, new ChilledCounter("GF, Wall 1, Chilled Counter " + counterNum));
            counterNum++;
        }
        for (int c = 15; c <= 20; c++) {
            tiles[1][c] = new Tile(Tile.Type.DISPLAY, new ChilledCounter("GF, Wall 1, Chilled Counter " + counterNum));
            counterNum++;
        }
    }

    private void createShelfRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) {
            tiles[r][col] = new Tile(Tile.Type.DISPLAY, new Shelf(base + i));
        }
    }
    private void createTableRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) {
            tiles[r][col] = new Tile(Tile.Type.DISPLAY, new Table(base + i));
        }
    }
    private void createRefrigeratorRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) {
            tiles[r][col] = new Tile(Tile.Type.DISPLAY, new Refrigerator(base + i));
        }
    }
    private void createChilledCounterRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) {
            tiles[r][col] = new Tile(Tile.Type.DISPLAY, new ChilledCounter(base + i));
        }
    }

    private void stockDisplays() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Tile t = tiles[r][c];
                if (t.getType() == Tile.Type.DISPLAY) {
                    Display d = (Display) t.getAmenity();
                    ProductType type = getProductTypeForAddress(d.getAddress());
                    if (type == null) continue;
                    int max = d.getDisplayType().getNumTiers() * d.getDisplayType().getCapacityPerTier();
                    int num = random.nextInt(max - 1) + 1;
                    List<List<Product>> tiers = d.getTiers();
                    int capacityPerTier = d.getDisplayType().getCapacityPerTier();
                    for (int i = 0; i < num; i++) {
                        String name = productNames.get(type)[random.nextInt(productNames.get(type).length)];
                        double[] range = priceRanges.get(type);
                        double price = range[0] + random.nextDouble() * (range[1] - range[0]);
                        Product p = new Product(type, name, price);
                        
                        // Find available tiers
                        List<Integer> availableTiers = new ArrayList<>();
                        for (int tier = 0; tier < tiers.size(); tier++) {
                            if (tiers.get(tier).size() < capacityPerTier) {
                                availableTiers.add(tier);
                            }
                        }
                        if (!availableTiers.isEmpty()) {
                            int tier = availableTiers.get(random.nextInt(availableTiers.size()));
                            d.addProduct(p, tier);
                        }
                    }
                }
            }
        }
    }

    private ProductType getProductTypeForAddress(String address) {
        if (address.contains("Table 1")) return ProductType.FRUIT;
        if (address.contains("Table 2")) return ProductType.CEREAL;
        if (address.contains("Table 3")) return ProductType.NOODLES;
        if (address.contains("Aisle 1") && address.contains("Shelf ")) return ProductType.ALCOHOL;
        if (address.contains("Aisle 5") && address.contains("Shelf ")) return ProductType.CANNED_GOODS;
        if (address.contains("Aisle 4") && address.contains("Shelf ")) return ProductType.CONDIMENTS;
        if (address.contains("Row 4-7") && address.contains("Aisle 2") && address.contains("Shelf ")) return ProductType.ALCOHOL;
        if (address.contains("Row 4-7") && address.contains("Aisle 3") && address.contains("Shelf ")) return ProductType.SOFT_DRINK;
        if (address.contains("Row 4-7") && address.contains("Aisle 6") && address.contains("Shelf ")) return ProductType.CEREAL;
        if (address.contains("Row 4-7") && address.contains("Aisle 7") && address.contains("Shelf ")) return ProductType.CANNED_GOODS;
        if (address.contains("Row 10-13") && address.contains("Aisle 2") && address.contains("Shelf ")) return ProductType.CONDIMENTS;
        if (address.contains("Row 10-13") && address.contains("Aisle 3") && address.contains("Shelf ")) return ProductType.JUICE;
        if (address.contains("Row 10-13") && address.contains("Aisle 6") && address.contains("Shelf ")) return ProductType.NOODLES;
        if (address.contains("Row 10-13") && address.contains("Aisle 7") && address.contains("Shelf ")) return ProductType.SNACKS;
        if (address.contains("Chilled Counter ")) {
            int counterNum = Integer.parseInt(address.substring(address.lastIndexOf(" ") + 1));
            if (counterNum >= 1 && counterNum <= 6) return ProductType.CHICKEN;
            if (counterNum >= 7 && counterNum <= 12) return ProductType.BEEF;
            if (counterNum >= 13 && counterNum <= 18) return ProductType.SEAFOOD;
        }
        return null;
    }

    public void spawnShopper(Shopper s) { shopper = s; shopper.setPosition(21, 11); shopper.setFacing(Direction.NORTH); }

    public void printMap() {
        //System.out.println();
        Display.nineDashline();
        System.out.println("\nLegend:");
        System.out.println("# - Wall            | T - Table         | R - Refrigerator  | S - Shelf     | C - Chilled Counter");
        System.out.println("E - Entrance        | X - Exit          | G - Cart Station  | B - Basket Station");
        System.out.println("I - Product Search  | $ - Checkout      | . - Empty | ^ v > < - Shopper facing");
        System.out.println();
        System.out.println("Position: (" + shopper.getRow() + ", " + shopper.getCol() + ") Facing: " + shopper.getFacing());
        System.out.println("Current Total: Php " + String.format("%.2f", shopper.getTotalPrice()));
        System.out.println();

        // Print column numbers
        System.out.print("    ");
        for (int c = 0; c < COLS; c++) {
            System.out.printf("%3d", c);
        }
        System.out.println();

        for (int r = 0; r < ROWS; r++) {
            // Print row number
            System.out.printf("%3d", r);
            for (int c = 0; c < COLS; c++) {
                String cell;
                if (r == shopper.getRow() && c == shopper.getCol()) {
                    char facingSym;
                    if (shopper.getFacing() == Direction.NORTH) facingSym = '^';
                    else if (shopper.getFacing() == Direction.SOUTH) facingSym = 'v';
                    else if (shopper.getFacing() == Direction.EAST) facingSym = '>';
                    else facingSym = '<';
                    cell = String.valueOf(facingSym);
                } else {
                    Tile t = tiles[r][c];
                    char sym;
                    if (t.getType() == Tile.Type.WALL) {
                        sym = '#';
                    } else if (t.getType() == Tile.Type.DISPLAY) {
                        DisplayType dt = ((Display) t.getAmenity()).getDisplayType();
                        if (dt == DisplayType.TABLE) sym = 'T';
                        else if (dt == DisplayType.REFRIGERATOR) sym = 'R';
                        else if (dt == DisplayType.SHELF) sym = 'S';
                        else if (dt == DisplayType.CHILLED_COUNTER) sym = 'C';
                        else sym = '?';
                    } else if (t.getType() == Tile.Type.SERVICE) {
                        Object srv = t.getAmenity();
                        if (srv instanceof EntranceService) sym = 'E';
                        else if (srv instanceof ExitService) sym = 'X';
                        else if (srv instanceof CartStation) sym = 'G';
                        else if (srv instanceof BasketStation) sym = 'B';
                        else if (srv instanceof ProductSearchService) sym = 'I';
                        else if (srv instanceof CheckoutService) sym = '$';
                        else sym = '.';
                    } else {
                        sym = '.';
                    }
                    cell = String.valueOf(sym);
                }
                System.out.printf("%3s", cell);
            }
            System.out.println();
        }
    }

    public void moveShopper(Direction dir) {
        int nr = shopper.getRow(), nc = shopper.getCol();
        switch (dir) { case NORTH -> nr--; case SOUTH -> nr++; case EAST -> nc++; case WEST -> nc--; }
        if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS || !tiles[nr][nc].isPassable()) { System.out.println("Cannot move."); return; }
        shopper.setPosition(nr, nc);
    }

    public void interactFront() {
        int fr = shopper.getRow(), fc = shopper.getCol();
        switch (shopper.getFacing()) { case NORTH -> fr--; case SOUTH -> fr++; case EAST -> fc++; case WEST -> fc--; }
        if (fr < 0 || fr >= ROWS || fc < 0 || fc >= COLS) return;
        Tile t = tiles[fr][fc];
        if (t.getAmenity() == null) return;
        if (t.getType() == Tile.Type.SERVICE) ((Service) t.getAmenity()).interact(shopper, this);
        else if (t.getType() == Tile.Type.DISPLAY) interactWithDisplay((Display) t.getAmenity());
    }

    private void interactWithDisplay(Display d) {
        while (true) {
            System.out.println("1. Take  2. Return");
            String choiceStr = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
                continue;
            }
            if (choice == 1) {
                if (!shopper.hasEquipment() && shopper.getChosenProducts().size() >= 2) {
                    System.out.println("Your hand is full. You can only return items or checkout.");
                    continue;
                }
                for (int tier = 0; tier < d.getDisplayType().getNumTiers(); tier++) {
                    System.out.println();
                    System.out.println("Tier " + tier + ":");
                    List<Product> tierProducts = d.getTiers().get(tier);
                    if (tierProducts.isEmpty()) {
                        System.out.println("Empty");
                    } else {
                        for (Product p : tierProducts) System.out.println(p.getSerialCode() + " " + p.getName() + " Php " + String.format("%.2f", p.getPrice()));
                    }
                }
                int tier = 0;
                if (d.getDisplayType().getNumTiers() > 1) {
                    while (true) {
                        System.out.println();
                        System.out.print("Enter tier: ");
                        String tierStr = scanner.nextLine().trim();
                        try {
                            tier = Integer.parseInt(tierStr);
                            if (tier < 0 || tier >= d.getDisplayType().getNumTiers()) {
                                System.out.println("Invalid tier. Please enter a number between 0 and " + (d.getDisplayType().getNumTiers() - 1) + ".");
                                continue;
                            }
                            List<Product> tierProducts = d.getTiers().get(tier);
                            if (tierProducts.isEmpty()) {
                                System.out.println("This tier is empty. Please select another tier.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }
                } else {
                    List<Product> tierProducts = d.getTiers().get(tier);
                    if (tierProducts.isEmpty()) {
                        System.out.println("This tier is empty. No products available.");
                        return;
                    }
                }
                while (true) {
                    System.out.print("Enter serial code: ");
                    String serial = scanner.nextLine().trim();
                    Product p = d.removeProduct(serial, tier);
                    if (p == null) {
                        System.out.println("Not found. Please try again.");
                        continue;
                    }
                    if (!shopper.canTakeProduct(p)) {
                        d.addProduct(p, tier);
                        System.out.println("Restricted. Please try again.");
                        continue;
                    }
                    if (!shopper.addProduct(p)) {
                        d.addProduct(p, tier);
                        System.out.println("Full. Please try again.");
                        continue;
                    }
                    System.out.println("Taken.");
                    return;
                }
            } else if (choice == 2) {
                List<Product> prods = shopper.getChosenProducts();
                if (prods.isEmpty()) {
                    System.out.println("None.");
                    return;
                }
                for (Product p : prods) System.out.println(p.getSerialCode() + " " + p.getName() + " Php " + String.format("%.2f", p.getPrice()));
                while (true) {
                    System.out.print("Enter serial code: ");
                    String serial = scanner.nextLine().trim();
                    Product p = shopper.removeProduct(serial);
                    if (p == null) {
                        System.out.println("Not found. Please try again.");
                        continue;
                    }
                    if (!p.getType().getDisplayType().equals(d.getDisplayType())) {
                        shopper.addProduct(p);
                        System.out.println("Wrong display. Please try again.");
                        continue;
                    }
                    int tier = 0;
                    if (d.getDisplayType().getNumTiers() > 1) {
                        while (true) {
                            System.out.print("Enter tier: ");
                            String tierStr = scanner.nextLine().trim();
                            try {
                                tier = Integer.parseInt(tierStr);
                                if (tier < 0 || tier >= d.getDisplayType().getNumTiers()) {
                                    System.out.println("Invalid tier. Please enter a number between 0 and " + (d.getDisplayType().getNumTiers() - 1) + ".");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                    }
                    if (!d.addProduct(p, tier)) {
                        shopper.addProduct(p);
                        System.out.println("Full. Please try again.");
                        continue;
                    }
                    System.out.println("Returned.");
                    return;
                }
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    public void viewChosen() {
        System.out.println();
        System.out.println(shopper.hasEquipment() ? shopper.getEquipment().getClass().getSimpleName() : "Hand Carrying: ");
        List<Product> prods = shopper.getChosenProducts();
        if (prods.isEmpty()) { System.out.println("Empty."); return; }
        java.util.Map<String, Integer> qty = new HashMap<>();
        java.util.Map<String, Double> price = new HashMap<>();
        for (Product p : prods) { qty.merge(p.getName(), 1, Integer::sum); price.put(p.getName(), p.getPrice()); }
        for (String n : qty.keySet()) System.out.println(n + " x" + qty.get(n) + " = Php " + String.format("%.2f", price.get(n) * qty.get(n)));
    }

    public List<String> searchProductAddresses(String name) {
        List<String> res = new ArrayList<>();
        for (int r = 0; r < ROWS; r++) for (int c = 0; c < COLS; c++) {
            Tile t = tiles[r][c];
            if (t.getType() == Tile.Type.DISPLAY && ((Display) t.getAmenity()).containsProduct(name))
                res.add(((Display) t.getAmenity()).getAddress());
        }
        return res;
    }

    public boolean isRunning() { return running; }
    public void setRunning(boolean r) { running = r; }
    public Scanner getScanner() { return scanner; }
}
