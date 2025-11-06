public class AgeCheck {

    /**
     * Determines if the shopper can purchase a given product.
     * Blocks alcohol (and other restricted types) for underage shoppers.
     */
    public static boolean canPurchase(Shopper s, Product p) {
        if (p.getType() == ProductType.ALCOHOL && s.getAge() < 18) {
            System.out.printf("%s cannot purchase alcohol (age %d).%n", s.getName(), s.getAge());
            return false;
        }
        return true;
    }

    /**
     * Checks if the shopper qualifies for senior citizen benefits.
     */
    public static boolean isSenior(Shopper s) {
        return s.getAge() >= 60;
    }

    /**
     * Verifies all items in shopper's cart for restricted products before checkout.
     * Returns false if any underage restriction is violated.
     */
    public static boolean canCheckout(Shopper s) {
        for (Product p : s.getHeldProducts()) {
            if (!canPurchase(s, p)) {
                System.out.println("Restricted item found in cart: " + p.getName());
                return false;
            }
        }
        return true;
    }

    /**
     * Determines overall discount eligibility.
     * Used by checkout to apply senior or PWD discounts.
     */
    public static boolean eligibleForDiscount(Shopper s) {
        return isSenior(s);
    }
}