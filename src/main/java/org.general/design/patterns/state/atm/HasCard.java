package design.patterns.state.atm;

public class HasCard implements ATMState {

    // context
    ATMMachine atmMachine;

    private int validPin = 1234;

    public HasCard(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
    }

    @Override
    public void insertCard() {
        System.out.println("You've just entered a card. You can't enter more cards.");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card ejected");
        // change the state
        atmMachine.setATMState(atmMachine.getNoCardState());
    }

    @Override
    public void insertPin(int pin) {
        if (pin == validPin) {
            System.out.println("Pin is correct");
            atmMachine.correctPinEntered = true;
            atmMachine.setATMState(atmMachine.getHasPinState());
        } else {
            System.out.println("Invalid Pin");
            atmMachine.correctPinEntered = false;
            ejectCard();
        }
    }

    @Override
    public void requestCash(int amount) {
        System.out.println("You have to insert PIN.");
    }
}
