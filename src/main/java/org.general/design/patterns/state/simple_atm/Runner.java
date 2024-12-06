package design.patterns.state.simple_atm;

public class Runner {
    public static void main(String[] args) {
        Atm atm = new Atm(1000);

        System.out.println("Cash in ATM: $" + atm.getCashAvailable());

        // Withdraw till run out of cash
        atm.withdraw(500);
        atm.withdraw(500);
        atm.withdraw(5);

        // deposit and again withdraw
        atm.deposit(10);
        atm.withdraw(5);
    }
}
