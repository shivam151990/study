package design.patterns.state.simple_atm;

public class Working implements ATMState {

    private Atm atm;
    public Working(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void withdraw(int amount) {
        int cashAvailable = atm.getCashAvailable();
        if (amount > cashAvailable) {
            System.out.println("Insufficient funds to withdraw");
            atm.setState(new OutOfCash(atm));
            return;
        }
        System.out.println("$" + amount + " has been dispersed.");
        int newCashAvailable = cashAvailable - amount;
        atm.setCashAvailable(newCashAvailable);
        if (newCashAvailable == 0) {
            atm.setState(new OutOfCash(atm));
        }
    }

    @Override
    public void deposit(int amount) {
        System.out.println("$" + amount + " has been deposited.");
        atm.setCashAvailable(atm.getCashAvailable() + amount);
    }
}
