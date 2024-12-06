package design.patterns.state.atm;

public class NoCard implements ATMState {
    // context
    ATMMachine atmMachine;

    public NoCard(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
    }

    @Override
    public void insertCard() {
        System.out.println("Card inserted");
        System.out.println("Please enter PIN");
        atmMachine.setATMState(atmMachine.getHasCardState());
    }

    @Override
    public void ejectCard() {
        System.out.println("Please insert card first.");
    }

    @Override
    public void insertPin(int pin) {
        System.out.println("Please insert card first.");
    }

    @Override
    public void requestCash(int amount) {
        System.out.println("Please  insert card first.");
    }
}
