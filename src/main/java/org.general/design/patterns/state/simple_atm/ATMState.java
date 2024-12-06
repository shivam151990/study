package design.patterns.state.simple_atm;

public interface ATMState {
    void withdraw(int amount);
    void deposit(int amount);
}
