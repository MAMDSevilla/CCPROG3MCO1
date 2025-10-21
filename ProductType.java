public enum ProductType {
    FRUIT("FRU", DisplayType.TABLE, true, false, false, false),
    CEREAL("CER", DisplayType.SHELF, true, false, false, false),
    NOODLES("NDL", DisplayType.SHELF, true, false, false, false),
    SNACKS("SNK", DisplayType.SHELF, true, false, false, false),
    CANNED_GOODS("CAN", DisplayType.SHELF, true, false, false, false),
    CONDIMENTS("CON", DisplayType.SHELF, true, false, false, false),
    SOFT_DRINK("SFT", DisplayType.SHELF, false, true, false, false),
    JUICE("JUC", DisplayType.SHELF, false, true, false, false),
    ALCOHOL("ALC", DisplayType.SHELF, false, true, true, true),
    CHICKEN("CHK", DisplayType.CHILLED_COUNTER, true, false, false, false),
    BEEF("BEF", DisplayType.CHILLED_COUNTER, true, false, false, false),
    SEAFOOD("SEA", DisplayType.CHILLED_COUNTER, true, false, false, false);

    private final String prefix;
    private final DisplayType displayType;
    private final boolean isFood;
    private final boolean isBeverage;
    private final boolean restrictedForMinors;
    private final boolean noSeniorDiscount;

    ProductType(String prefix, DisplayType displayType, boolean isFood, boolean isBeverage, boolean restrictedForMinors, boolean noSeniorDiscount) {
        this.prefix = prefix;
        this.displayType = displayType;
        this.isFood = isFood;
        this.isBeverage = isBeverage;
        this.restrictedForMinors = restrictedForMinors;
        this.noSeniorDiscount = noSeniorDiscount;
    }

    public String getPrefix() { return prefix; }
    public DisplayType getDisplayType() { return displayType; }
    public boolean isFood() { return isFood; }
    public boolean isBeverage() { return isBeverage; }
    public boolean isRestrictedForMinors() { return restrictedForMinors; }
    public boolean isNoSeniorDiscount() { return noSeniorDiscount; }

    public static ProductType fromPrefix(String serial) {
        String pre = serial.substring(0, 3);
        for (ProductType type : values()) {
            if (type.prefix.equals(pre)) return type;
        }
        throw new IllegalArgumentException("Invalid serial");
    }
}