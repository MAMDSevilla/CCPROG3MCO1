import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class CheckoutService extends Service {
    @Override
    public void interact(Shopper shopper, Map map) {
        List<Product> products = shopper.getChosenProducts();
        if (products.isEmpty()) {
            System.out.println("No products to checkout.");
            return;
        }
        double total = shopper.getTotalPrice();
        double discounted = shopper.getDiscountedPrice();
        try (PrintWriter out = new PrintWriter(new File("receipt.txt"))) {
            out.println("Receipt for " + shopper.getName());
            for (Product p : products) {
                out.println(p.getSerialCode() + " " + p.getName() + " " + p.getPrice());
            }
            out.println("Total: " + total);
            if (discounted < total) out.println("Discounted Total: " + discounted);
            System.out.println("Checked out. Receipt saved.");
        } catch (Exception e) {
            System.out.println("Error saving receipt.");
        }
        shopper.checkOut();
    }
}