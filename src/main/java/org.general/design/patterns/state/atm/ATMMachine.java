package design.patterns.state.atm;

public class ATMMachine {
    ATMState hasCard;
    ATMState noCard;
    ATMState hasPin;
    ATMState noCash;

    // current state
    ATMState atmState;
    int cashInTheMachine = 2000;
    boolean correctPinEntered = false;

    public ATMMachine() {
        this.hasCard = new HasCard(this);
        this.noCard = new NoCard(this);
        this.hasPin =  new HasPin(this);
        this.noCash =  new NoCash(this);

        atmState = new NoCard(this);

        if (cashInTheMachine == 0) {
            atmState = new NoCash(this);
        }
    }

    void setATMState(ATMState atmState) {
        this.atmState = atmState;
    }

    public void setCashInMachine(int amount) {
        this.cashInTheMachine = amount;
    }

    public void insertCard() {
        atmState.insertCard();
    }

    public void ejectCard() {
        atmState.ejectCard();
    }

    public void requestCash(int amount) {
        atmState.requestCash(amount);
    }

    public void insertPin(int pin) {
        atmState.insertPin(pin);
    }

    public ATMState getHasCardState() {
        return hasCard;
    }

    public ATMState getNoCardState() {
        return noCard;
    }

    public ATMState getHasPinState() {
        return hasPin;
    }

    public ATMState getNoCashState() {
        return noCash;
    }
}
