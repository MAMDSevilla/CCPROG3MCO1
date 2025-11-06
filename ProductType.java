import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public enum ProductType {
    FRUIT("FRU", DispType.TABLE),
    VEGETABLE("VEG", DispType.TABLE),
    MILK("MLK", DispType.REFRIGERATOR),
    FROZEN_FOOD("FRZ", DispType.REFRIGERATOR),
    CHEESE("CHS", DispType.REFRIGERATOR),
    CHICKEN("CHK", DispType.CHILLED_COUNTER),
    BEEF("BEF", DispType.CHILLED_COUNTER),
    SEAFOOD("SEA", DispType.CHILLED_COUNTER),
    BREAD("BRD", DispType.TABLE),
    CEREAL("CER", DispType.SHELF),
    NOODLES("NDL", DispType.SHELF),
    SNACKS("SNK", DispType.SHELF),
    CANNED_GOODS("CAN", DispType.SHELF),
    CONDIMENTS("CON", DispType.SHELF),
    EGGS("EGG", DispType.TABLE),
    SOFT_DRINK("SFT", DispType.SHELF),
    JUICE("JUC", DispType.SHELF),
    ALCOHOL("ALC", DispType.SHELF),
    CLEANING_AGENTS("CLE", DispType.SHELF),
    HOME_ESSENTIALS("HOM", DispType.SHELF),
    HAIR_CARE("HAR", DispType.SHELF),
    BODY_CARE("BOD", DispType.SHELF),
    DENTAL_CARE("DEN", DispType.SHELF),
    CLOTHES("CLO", DispType.SHELF),
    STATIONERY("STN", DispType.SHELF),
    PET_FOOD("PET", DispType.SHELF);

    private final String code;
    private final DispType compatibleDisplay;
    private final AtomicInteger counter = new AtomicInteger(1);

    ProductType(String code, DispType compatibleDisplay) {
        this.code = code;
        this.compatibleDisplay = compatibleDisplay;
    }

    public String getCode() {
        return code;
    }

    public DispType getCompatibleDisplay() {
        return compatibleDisplay;
    }

    public String generateSerial() {
        int id = counter.getAndIncrement();
        return String.format("%s%05d", code, id);
    }

    /**
     * Returns a list of all predefined products.
     */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Thigh fillet", ProductType.CHICKEN, 150.0, 5));
        products.add(new Product("Breast fillet", ProductType.CHICKEN, 160.0, 5));
        products.add(new Product("Ground", ProductType.CHICKEN, 140.0, 5));
        products.add(new Product("Rib", ProductType.BEEF, 200.0, 5));
        products.add(new Product("Shank", ProductType.BEEF, 180.0, 5));
        products.add(new Product("Ground", ProductType.BEEF, 170.0, 5));
        products.add(new Product("Tilapia", ProductType.SEAFOOD, 120.0, 5));
        products.add(new Product("Sugpo", ProductType.SEAFOOD, 250.0, 5));
        products.add(new Product("Squid", ProductType.SEAFOOD, 130.0, 5));
        products.add(new Product("Beer", ProductType.ALCOHOL, 50.0, 10));
        products.add(new Product("Vodka", ProductType.ALCOHOL, 300.0, 5));
        products.add(new Product("Soju", ProductType.ALCOHOL, 80.0, 10));
        products.add(new Product("Cola", ProductType.SOFT_DRINK, 25.0, 20));
        products.add(new Product("Soda", ProductType.SOFT_DRINK, 20.0, 20));
        products.add(new Product("Sparkling water", ProductType.SOFT_DRINK, 30.0, 15));
        products.add(new Product("Apple", ProductType.FRUIT, 15.0, 30));
        products.add(new Product("Orange", ProductType.FRUIT, 12.0, 30));
        products.add(new Product("Grapes", ProductType.FRUIT, 50.0, 10));
        products.add(new Product("Oatmeal", ProductType.CEREAL, 80.0, 10));
        products.add(new Product("Barley", ProductType.CEREAL, 70.0, 10));
        products.add(new Product("Quinoa", ProductType.CEREAL, 90.0, 10));
        products.add(new Product("Tuna", ProductType.CANNED_GOODS, 60.0, 15));
        products.add(new Product("Sardines", ProductType.CANNED_GOODS, 40.0, 15));
        products.add(new Product("Soup", ProductType.CANNED_GOODS, 35.0, 15));
        products.add(new Product("Salt", ProductType.CONDIMENTS, 10.0, 50));
        products.add(new Product("Pepper", ProductType.CONDIMENTS, 15.0, 50));
        products.add(new Product("Paprika", ProductType.CONDIMENTS, 20.0, 30));
        products.add(new Product("Orange", ProductType.JUICE, 45.0, 20));
        products.add(new Product("Pineapple", ProductType.JUICE, 40.0, 20));
        products.add(new Product("Apple", ProductType.JUICE, 42.0, 20));
        products.add(new Product("Ramen", ProductType.NOODLES, 25.0, 25));
        products.add(new Product("Miswa", ProductType.NOODLES, 20.0, 25));
        products.add(new Product("Instant noodles", ProductType.NOODLES, 15.0, 30));
        products.add(new Product("Candies", ProductType.SNACKS, 5.0, 100));
        products.add(new Product("Cookies", ProductType.SNACKS, 30.0, 20));
        products.add(new Product("Junk food", ProductType.SNACKS, 10.0, 50));
        return products;
    }
}
