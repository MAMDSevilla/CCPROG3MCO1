import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a checkout counter where the shopper pays for their items.
 * Handles total computation, age verification, and payment.
 */
public class CheckoutCounter {
    // Fields
    private String id;
    private AgeCheck ageCheck;

    // Constructor
    public CheckoutCounter(String id, int row, int col) {
        this.id = id;
        this.ageCheck = new AgeCheck();
    }

    /**
     * Main interaction with shopper at checkout.
     */
    public void interact(Shopper s) {
        System.out.println("\nWelcome to Checkout Counter " + id + "!");

        if (s.getChosenProducts().isEmpty()) {
            System.out.println("Your cart/basket is empty!");
            return;
        }

        // Step 1: Check age-restricted products
        if (!AgeCheck.canCheckout(s)) {
            System.out.println("Age restriction: You cannot purchase restricted products.");
            return;
        }

        // Step 2: Calculate total
        double total = calculateTotal(s);
        if (total <= 0) {
            System.out.println("No valid products to checkout.");
            return;
        }

        System.out.printf("Subtotal: ₱%.2f%n", total);

        // Step 3: Payment
        if (s.getWallet().charge(total)) {
            System.out.println("Payment successful. Thank you for shopping!");
            generateReceipt(s, total);
            s.clearCart();
        } else {
            System.out.println("Insufficient balance. Please add funds or remove items.");
        }
    }

    /**
     * Computes total price of all products in shopper's cart.
     */
    private double calculateTotal(Shopper s) {
        double sum = 0;
        for (Product p : s.getChosenProducts()) {
            sum += p.getPrice();
        }
        return sum;
    }

    /**
     * Generates and prints a simple receipt to the console.
     */
    private void generateReceipt(Shopper s, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== SUPERMARKET RECEIPT =====\n");
        sb.append("Shopper: ").append(s.getName()).append("\n");
        sb.append("Date: ").append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
        sb.append("-------------------------------\n");

        for (Product p : s.getChosenProducts()) {
            sb.append(String.format("%-20s ₱%.2f%n", p.getProductName(), p.getPrice()));
        }

        sb.append("-------------------------------\n");
        sb.append(String.format("TOTAL: ₱%.2f%n", total));
        sb.append("===============================\n");

        // Print to console
        System.out.println(sb.toString());
    }
}
