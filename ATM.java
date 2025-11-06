import java.util.Scanner;

/**
 * ATM service station that allows shoppers to withdraw funds into their wallet.
 */
public class ATM {
    private String id;
    private double atmBalance;
    private final Scanner sc = new Scanner(System.in);

    public ATM(String id, int row, int col) {
        this.id = id;
        this.atmBalance = 50000.00; // default machine balance, can be changed later
    }

    /**
     * Main interaction between shopper and ATM.
     */
    public void interact(Shopper s) {
        System.out.println("\nWelcome to ATM " + id + "!");
        System.out.printf("ATM Balance: ₱%.2f%n", atmBalance);
        System.out.printf("Your Wallet: ₱%.2f%n", s.getWallet().getBalance());

        System.out.print("Enter amount to withdraw: ₱");
        double amount;

        try {
            amount = Double.parseDouble(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a numeric amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Please enter a positive amount.");
            return;
        }

        if (atmBalance >= amount) {
            atmBalance -= amount;
            s.addMoney(amount);
            System.out.printf("Successfully withdrawn ₱%.2f%n", amount);
            System.out.printf("Your new wallet balance: ₱%.2f%n", s.getWallet().getBalance());
        } else {
            System.out.println("ATM has insufficient funds.");
        }
    }

    // Optional future feature: restock ATM
    public void refill(double amount) {
        atmBalance += amount;
    }

    public double getAtmBalance() { return atmBalance; }
}
