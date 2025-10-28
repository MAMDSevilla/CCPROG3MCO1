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

    public boolean interact(Shopper s, StoreMap m) {
        switch (type) {
            case ENTRANCE -> {
                System.out.println("You are already inside the supermarket.");
                return false;
            }
            case EXIT -> {
                s.checkOut();
                System.out.println(s.getName() + " exits through " + id + ". Thank you for shopping!");
                System.out.print("Do you want to restart the game? (y/n): ");
                java.util.Scanner sc = new java.util.Scanner(System.in);
                String choice = sc.nextLine().trim().toLowerCase();
                if (choice.equals("y")) {
                    System.out.println("Restarting the game...");
                    return true;
                } else {
                    System.out.println("Ending the game. Goodbye!");
                    System.exit(0);
                    return false; // not reached
                }
            }
            default -> {
                System.out.println("Accessway interaction not defined.");
                return false;
            }
        }
    }
}
