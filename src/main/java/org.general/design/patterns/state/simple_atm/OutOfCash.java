package design.patterns.state.simple_atm;

public class OutOfCash implements ATMState {

    private Atm atm;
    public OutOfCash(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void withdraw(int amount) {
        System.out.println("ATM is out of cash!");
    }

    @Override
    public void deposit(int amount) {
        System.out.println("$" + amount + " has been deposited");
        atm.setCashAvailable(atm.getCashAvailable() + amount);
        atm.setState(new Working(atm));
    }
}
