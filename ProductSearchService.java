import java.util.List;
import java.util.Scanner;

public class ProductSearchService extends Service {
    @Override
    public void interact(Shopper shopper, Map map) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();
        List<String> addresses = map.searchProductAddresses(name);
        if (addresses.isEmpty()) {
            System.out.println("No displays with that product.");
        } else {
            System.out.println("Found at:");
            for (String addr : addresses) {
                System.out.println(addr);
            }
        }
        scanner.close();
    }
}
