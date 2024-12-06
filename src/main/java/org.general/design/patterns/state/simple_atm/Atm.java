package design.patterns.state.simple_atm;

public class Atm implements ATMState {

    private int cashAvailable;
    private ATMState currentState;

    public Atm(int cashAvailable) {
        this.cashAvailable = cashAvailable;
        this.currentState = new Working(this);
    }

    public int getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(int cashAvailable) {
        this.cashAvailable = cashAvailable;
    }


    public void setState(ATMState currentState) {
        this.currentState = currentState;
    }

    @Override
    public void withdraw(int amount) {
        currentState.withdraw(amount);
    }

    @Override
    public void deposit(int amount) {
        currentState.deposit(amount);
    }
}
