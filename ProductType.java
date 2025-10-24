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
}
