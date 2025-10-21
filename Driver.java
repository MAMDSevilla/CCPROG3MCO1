import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            System.out.println("Enter age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
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
