public class Money {
    private double balance;

    public Money(double balance) {
        this.balance = balance;
    }

    public void addFunds(double amount) {
        balance += amount;
    }

    public boolean charge(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        System.out.println("Insufficient funds.");
        return false;
    }

    public double getBalance() { return balance; }
}