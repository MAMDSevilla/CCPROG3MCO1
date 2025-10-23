import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String name;
            while (true) {
                System.out.print("Enter name: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                }
                System.out.println("Invalid name. Please enter a non-empty name.");
            }
            int age;
            while (true) {
                System.out.print("Enter age: ");
                try {
                    age = Integer.parseInt(scanner.nextLine().trim());
                    if (age > 0) {
                        break;
                    }
                    System.out.println("Invalid age. Please enter a positive integer.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age. Please enter a valid integer.");
                }
            }
            Shopper shopper = new Shopper(name, age);
            Map map = new Map();
            map.spawnShopper(shopper);
            while (map.isRunning()) {
                map.printMap();
                System.out.println("Command (w a s d move, i j k l face, space interact, v view, q quit): ");
                String command = scanner.nextLine();
                if (command.isEmpty()) continue;
                char ch = command.charAt(0);
                switch (ch) {
                    case 'w': map.moveShopper(Direction.NORTH); break;
                    case 's': map.moveShopper(Direction.SOUTH); break;
                    case 'a': map.moveShopper(Direction.WEST); break;
                    case 'd': map.moveShopper(Direction.EAST); break;
                    case 'i': shopper.setFacing(Direction.NORTH); break;
                    case 'k': shopper.setFacing(Direction.SOUTH); break;
                    case 'j': shopper.setFacing(Direction.WEST); break;
                    case 'l': shopper.setFacing(Direction.EAST); break;
                    case ' ': map.interactFront(); break;
                    case 'v': map.viewChosen(); break;
                    case 'q': map.setRunning(false); break;
                }
            }
            System.out.println("Restart? y/n");
            if (!scanner.nextLine().equals("y")) break;
        }
        scanner.close();
    }
}
