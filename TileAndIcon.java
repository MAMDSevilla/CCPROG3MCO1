public class TileAndIcon {
    private boolean passable;
    private char icon;
    private Object amenity;

    public TileAndIcon(boolean passable, char icon) {
        this.passable = passable;
        this.icon = icon;
    }

    public char getIcon() { return icon; }
    public boolean isPassable() { return passable; }
    public void setIcon(char icon) { this.icon = icon; }

    public void setAmenity(Object a) { this.amenity = a; }
    public Object getAmenity() { return amenity; }

    public boolean isWalkable() {
        return passable && amenity == null;
    }

    public enum IconType {
        WALL('#'),
        ENTRANCE('E'),
        EXIT('X'),
        SHOPPER('○'),
        BASKET_STATION('▽'),
        CART_STATION('▯'),
        TABLE('T'),
        REFRIGERATOR('R'),
        CHILLED_COUNTER('C'),
        SHELF('S'),
        PRODUCT('~'),
        CHECKOUT_COUNTER('θ'),
        PRODUCT_SEARCH('P'),
        ATM('M'),
        FLOOR('◌');

        private final char symbol;

        IconType(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }
    }

    public static char getIconFor(Object obj) {
        if (obj == null) return IconType.FLOOR.getSymbol();
        String name = obj.getClass().getSimpleName();

        return switch (name) {
            case "Entrance" -> IconType.ENTRANCE.getSymbol(); 
            case "Exit" -> IconType.EXIT.getSymbol();
            case "BasketStation" -> IconType.BASKET_STATION.getSymbol();
            case "CartStation" -> IconType.CART_STATION.getSymbol();
            case "Display" -> IconType.SHELF.getSymbol();
            case "CheckoutCounter" -> IconType.CHECKOUT_COUNTER.getSymbol();
            case "ATM" -> IconType.ATM.getSymbol();
            case "ProductSearch" -> IconType.PRODUCT_SEARCH.getSymbol();
            case "Shopper" -> IconType.SHOPPER.getSymbol();
            default -> IconType.FLOOR.getSymbol();
        };
    }
}