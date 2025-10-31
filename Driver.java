import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
/**
 * Entry point for the Supermarket Simulation.
 * Initializes the world and starts the interactive loop.
 */

 /*
    IMPORTANT:
    Run these commands =
    *If on CMD: chcp 65001
                java -Dfile.encoding=UTF-8 -cp . Driver
    *If on PowerShell: [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
                       java "-Dfile.encoding=UTF-8" -cp . Driver
  */
public class Driver {
    public static void main(String[] args) throws Exception {
        
         // === Force UTF-8 globally ===
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        System.setProperty("file.encoding", "UTF-8");

        // init
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n====================================");
        System.out.println("   WELCOME TO THE SUPERMARKET SIM   ");
        System.out.println("====================================");

        while (running) {
            // --- Shopper setup ---
            System.out.print("Enter your name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter your age: ");
            int age;
            try {
                age = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Defaulting age to 25.");
                age = 25;
            }

            System.out.print("Enter starting wallet balance (₱): ");
            double balance;
            try {
                balance = Double.parseDouble(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Defaulting ₱500.00.");
                balance = 500.00;
            }

            // --- Initialize entities ---
            Shopper shopper = new Shopper(name, age, balance);
            StoreMap map = new StoreMap();
            map.initializeLayout();
            map.setShopper(shopper);
            shopper.setPosition(21, 11);

            System.out.printf("\nShopper %s spawned at entrance! (₱%.2f)\n",
                    shopper.getName(), shopper.getWallet().getBalance());

            // --- Start game loop ---
            UserInput ui = new UserInput(map, shopper);
            boolean restartFromGame = ui.start();

            // --- Restart option ---
            if (!restartFromGame) {
                System.out.print("\nWould you like to restart? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    running = false;
                }
            }
        }

        System.out.println("\nThanks for visiting the Supermarket Simulation!");
        scanner.close();
    }
}
