public enum DisplayType {
    TABLE(1, 4),
    CHILLED_COUNTER(1, 3),
    REFRIGERATOR(3, 3),
    SHELF(2, 4);

    private final int numTiers;
    private final int capacityPerTier;

    DisplayType(int numTiers, int capacityPerTier) {
        this.numTiers = numTiers;
        this.capacityPerTier = capacityPerTier;
    }

    public int getNumTiers() { return numTiers; }
    public int getCapacityPerTier() { return capacityPerTier; }
}
