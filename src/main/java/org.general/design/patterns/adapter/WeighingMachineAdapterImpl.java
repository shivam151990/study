package design.patterns.adapter;

public class WeighingMachineAdapterImpl implements WeightMachineAdapter {

    private final WeighingMachine weighingMachine;

    public WeighingMachineAdapterImpl(WeighingMachine weighingMachine) {
        this.weighingMachine = weighingMachine;
    }

    @Override
    public int getWeightInKg() {
        return weighingMachine.getWeightInPounds() * 2;
    }
}
