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
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("Invalid age. Please enter a positive integer.");
                        continue;
                    }
                    try {
                        age = Integer.parseInt(input);
                        if (age > 0) break;
                        System.out.println("Invalid age. Please enter a positive integer.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age. Please enter a valid integer.");
                    }
                }
            Shopper shopper = new Shopper(name, age);
            Map map = new Map();
            map.spawnSh opper(shopper);
            while (map.isRunning()) {
                map.printMap();
                System.out.println();
                System.out.println("Command (move: w a s d; face: i j k l; space interact; view: v; quit: q): ");
                System.out.print("Choose your Action: ");
                String command = scanner.nextLine();
                if (command.isEmpty()) {
                    System.out.println("You entered nothing. Please enter a command.");
                    continue;
                }

                command = command.toLowerCase();
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
                    default: System.out.println("Invalid Action!");
                }
            }
            System.out.println("Restart? y/n");
            if (!scanner.nextLine().equals("y")) break;
        }
        scanner.close();
    }
}
