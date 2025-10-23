public class Tile {
    enum Type {
        FREE, WALL, DISPLAY, SERVICE
    }

    private Type type;
    private Object amenity;

    public Tile() {
        this.type = Type.FREE;
    }

    public Tile(Type type, Object amenity) {
        this.type = type;
        this.amenity = amenity;
    }

    public boolean isPassable() {
        return type == Type.FREE || type == Type.SERVICE;
    }

    public Object getAmenity() { return amenity; }
    public Type getType() { return type; }
}