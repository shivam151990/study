package design.patterns.state.atm;

public class HasPin implements ATMState {
    // context
    ATMMachine atmMachine;

    public HasPin(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
    }

    @Override
    public void insertCard() {
        System.out.println("Card is inserted");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card is ejected");
        atmMachine.setATMState(atmMachine.getNoCardState());
    }

    @Override
    public void insertPin(int pin) {
        System.out.println("Pin is entered.");
    }

    @Override
    public void requestCash(int amount) {
        if (amount <= atmMachine.cashInTheMachine) {
            System.out.println(amount + " has been withdrawn");
            atmMachine.setCashInMachine(atmMachine.cashInTheMachine - amount);
        } else {
            System.out.println("Out of money.");
            atmMachine.setATMState(atmMachine.getNoCashState());
        }
        ejectCard();

        if (atmMachine.cashInTheMachine <= 0 ) {
            System.out.println("Out of money.");
            atmMachine.setATMState(atmMachine.getNoCashState());
        }
    }
}
