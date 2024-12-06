package design.patterns.state.atm;

public class NoCash implements ATMState {
    ATMMachine atmMachine;

    public NoCash(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
    }

    @Override
    public void insertCard() {
        System.out.println("Out of money");
    }

    @Override
    public void ejectCard() {
        System.out.println("Out of money");
    }

    @Override
    public void insertPin(int pin) {
        System.out.println("Out of money");
    }

    @Override
    public void requestCash(int amount) {
        System.out.println("Out of money");
    }
}
