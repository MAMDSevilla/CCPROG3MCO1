public class Accessway {
    private String id;
    private int row, col;
    private AccesswayType type;

    public Accessway(String id, int row, int col, AccesswayType type) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public void interact(Shopper s, StoreMap m) {
        switch (type) {
            case ENTRANCE -> System.out.println(s.getName() + " enters through " + id);
            case EXIT -> System.out.println(s.getName() + " exits through " + id);
            default -> System.out.println("Accessway interaction not defined.");
        }
    }
}
