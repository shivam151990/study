package design.patterns.practice.adapter;

public class WeighingMachineImpl implements WeighingMachine {

    public WeighingMachineImpl() {
    }

    @Override
    public double getWeightInPound() {
        return 10;
    }
}
