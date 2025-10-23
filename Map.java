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
        productNames.put(ProductType.FRUIT, new String[]{"Apples", "Oranges", "Grapes"});
        productNames.put(ProductType.VEGETABLE, new String[]{"Cabbage", "Lettuce", "Broccoli"});
        productNames.put(ProductType.MILK, new String[]{"Fresh Milk", "Soy Milk", "Almond Milk"});
        productNames.put(ProductType.FROZEN_FOOD, new String[]{"Hotdog", "Chicken Nuggets", "Tocino"});
        productNames.put(ProductType.CHEESE, new String[]{"Sliced Cheese", "Keso de Bola", "Mozzarella"});
        productNames.put(ProductType.BREAD, new String[]{"Baguette", "Croissant", "Bagel"});
        productNames.put(ProductType.CLEANING_AGENTS, new String[]{"Detergent", "Bleach", "Dish Soap"});
        productNames.put(ProductType.HOME_ESSENTIALS, new String[]{"Broom", "Mop", "Plunger"});
        productNames.put(ProductType.HAIR_CARE, new String[]{"Shampoo", "Conditioner", "Hair Wax"});
        productNames.put(ProductType.BODY_CARE, new String[]{"Soap", "Body Wash", "Shower Gel"});
        productNames.put(ProductType.DENTAL_CARE, new String[]{"Toothpaste", "Toothbrush", "Dental Floss"});
        productNames.put(ProductType.CLOTHES, new String[]{"Shirt", "Shorts", "Pants"});
        productNames.put(ProductType.STATIONERY, new String[]{"Paper", "Tape", "Pencil"});
        productNames.put(ProductType.PET_FOOD, new String[]{"Cat Food", "Dog Food", "Bird Seed"});

        priceRanges.put(ProductType.FRUIT, new double[]{15, 70});
        priceRanges.put(ProductType.VEGETABLE, new double[]{15, 70});
        priceRanges.put(ProductType.MILK, new double[]{50, 120});
        priceRanges.put(ProductType.FROZEN_FOOD, new double[]{80, 200});
        priceRanges.put(ProductType.CHEESE, new double[]{100, 300});
        priceRanges.put(ProductType.BREAD, new double[]{30, 100});
        priceRanges.put(ProductType.CLEANING_AGENTS, new double[]{50, 200});
        priceRanges.put(ProductType.HOME_ESSENTIALS, new double[]{100, 500});
        priceRanges.put(ProductType.HAIR_CARE, new double[]{80, 300});
        priceRanges.put(ProductType.BODY_CARE, new double[]{60, 250});
        priceRanges.put(ProductType.DENTAL_CARE, new double[]{40, 150});
        priceRanges.put(ProductType.CLOTHES, new double[]{200, 1000});
        priceRanges.put(ProductType.STATIONERY, new double[]{10, 100});
        priceRanges.put(ProductType.PET_FOOD, new double[]{100, 400});
    }

    private void initializeMap() {
        for (int r = 0; r < ROWS; r++) for (int c = 0; c < COLS; c++) tiles[r][c] = new Tile();

        for (int c = 0; c < COLS; c++) { tiles[0][c] = new Tile(Tile.Type.WALL, null); tiles[ROWS-1][c] = new Tile(Tile.Type.WALL, null); }
        for (int r = 0; r < ROWS; r++) { tiles[r][0] = new Tile(Tile.Type.WALL, null); tiles[r][COLS-1] = new Tile(Tile.Type.WALL, null); }

        tiles[21][0] = new Tile(Tile.Type.SERVICE, new EntranceService());
        tiles[21][1] = new Tile(Tile.Type.SERVICE, new ExitService());
        tiles[20][0] = new Tile(Tile.Type.SERVICE, new BasketStation());
        tiles[20][1] = new Tile(Tile.Type.SERVICE, new CartStation());
        tiles[15][15] = new Tile(Tile.Type.SERVICE, new ProductSearchService());
        tiles[15][16] = new Tile(Tile.Type.SERVICE, new ProductSearchService());

        for (int c = 0; c < 8; c++) tiles[18][c] = new Tile(Tile.Type.SERVICE, new CheckoutService());

        createShelfRow(4, 7, 5, "GF, Aisle 1, Shelf ", ProductType.CLEANING_AGENTS);
        createShelfRow(4, 7, 7, "GF, Aisle 1, Shelf ", ProductType.HOME_ESSENTIALS);

        createTableRow(4, 7, 17, "GF, Aisle 3, Table ", ProductType.FRUIT);
        createTableRow(4, 7, 19, "GF, Aisle 3, Table ", ProductType.VEGETABLE);
        createTableRow(4, 7, 21, "GF, Aisle 3, Table ", ProductType.BREAD);

        createShelfRow(9, 12, 7, "GF, Aisle 5, Shelf ", ProductType.HAIR_CARE);
        createShelfRow(9, 12, 9, "GF, Aisle 5, Shelf ", ProductType.BODY_CARE);
        createShelfRow(9, 12, 11, "GF, Aisle 5, Shelf ", ProductType.DENTAL_CARE);
        createShelfRow(9, 12, 13, "GF, Aisle 5, Shelf ", ProductType.CLOTHES);
        createShelfRow(9, 12, 15, "GF, Aisle 5, Shelf ", ProductType.STATIONERY);
        createShelfRow(9, 12, 17, "GF, Aisle 5, Shelf ", ProductType.PET_FOOD);

        createRefrigeratorRow(14, 16, 1, "GF, Wall 1, Refrigerator ", ProductType.MILK);
        createRefrigeratorRow(14, 16, 4, "GF, Wall 1, Refrigerator ", ProductType.FROZEN_FOOD);
        createRefrigeratorRow(14, 16, 7, "GF, Wall 1, Refrigerator ", ProductType.CHEESE);
    }

    private void createShelfRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) tiles[r][col] = new Tile(Tile.Type.DISPLAY, new Shelf(base + i));
    }
    private void createTableRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) tiles[r][col] = new Tile(Tile.Type.DISPLAY, new Table(base + i));
    }
    private void createRefrigeratorRow(int start, int end, int col, String base, ProductType type) {
        for (int r = start, i = 1; r <= end; r++, i++) tiles[r][col] = new Tile(Tile.Type.DISPLAY, new Refrigerator(base + i));
    }

    private void stockDisplays() {
        for (int r = 0; r < ROWS; r++) for (int c = 0; c < COLS; c++) {
            Tile t = tiles[r][c];
            if (t.getType() == Tile.Type.DISPLAY) {
                Display d = (Display) t.getAmenity();
                ProductType type = getProductTypeForAddress(d.getAddress());
                if (type == null) continue;
                int max = d.getDisplayType().getNumTiers() * d.getDisplayType().getCapacityPerTier();
                int num = random.nextInt(max - 1) + 1;
                for (int i = 0; i < num; i++) {
                    String name = productNames.get(type)[random.nextInt(productNames.get(type).length)];
                    double[] range = priceRanges.get(type);
                    double price = range[0] + random.nextDouble() * (range[1] - range[0]);
                    Product p = new Product(type, name, price);
                    int tier = random.nextInt(d.getDisplayType().getNumTiers());
                    while (!d.addProduct(p, tier)) tier = random.nextInt(d.getDisplayType().getNumTiers());
                }
            }
        }
    }

    private ProductType getProductTypeForAddress(String address) {
        if (address.contains("Table 1")) return ProductType.FRUIT;
        if (address.contains("Table 2")) return ProductType.VEGETABLE;
        if (address.contains("Table 3")) return ProductType.BREAD;
        if (address.contains("Shelf 3")) return ProductType.CLEANING_AGENTS;
        if (address.contains("Shelf 4")) return ProductType.HOME_ESSENTIALS;
        if (address.contains("Shelf 1")) return ProductType.HAIR_CARE;
        if (address.contains("Shelf 2")) return ProductType.BODY_CARE;
        if (address.contains("Shelf 3")) return ProductType.DENTAL_CARE;
        if (address.contains("Shelf 4")) return ProductType.CLOTHES;
        if (address.contains("Shelf 5")) return ProductType.STATIONERY;
        if (address.contains("Shelf 6")) return ProductType.PET_FOOD;
        if (address.contains("Refrigerator 1")) return ProductType.MILK;
        if (address.contains("Refrigerator 2")) return ProductType.FROZEN_FOOD;
        if (address.contains("Refrigerator 3")) return ProductType.CHEESE;
        return null;
    }

    public void spawnShopper(Shopper s) { shopper = s; shopper.setPosition(21, 0); }

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
        
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (r == shopper.getRow() && c == shopper.getCol()) System.out.print("[" + switch (shopper.getFacing()) {
                    case NORTH -> '^'; case SOUTH -> 'v'; case EAST -> '>'; case WEST -> '<';
                } + "] ");
                else {
                    Tile t = tiles[r][c];
                    char sym = switch (t.getType()) {
                        case WALL -> '#';
                        case DISPLAY -> switch (((Display) t.getAmenity()).getDisplayType()) {
                            case TABLE -> 'T'; case REFRIGERATOR -> 'R'; case SHELF -> 'S'; case CHILLED_COUNTER -> 'C';
                        };
                        case SERVICE -> {
                            Object srv = t.getAmenity();
                            if (srv instanceof EntranceService) yield 'E';
                            else if (srv instanceof ExitService) yield 'X';
                            else if (srv instanceof CartStation) yield 'G';
                            else if (srv instanceof BasketStation) yield 'B';
                            else if (srv instanceof ProductSearchService) yield 'I';
                            else if (srv instanceof CheckoutService) yield '$';
                            else yield '.';
                        }
                        default -> '.';
                    };
                    System.out.print("[" + sym + "] ");
                }
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
