package design.patterns.practice.adapter;



public class WeighingMachineConverter  implements WeighingMachineAdapter {

    private WeighingMachine weighingMachine;


    public WeighingMachineConverter(WeighingMachine weighingMachine) {
        this.weighingMachine = weighingMachine;
    }

    @Override
    public double getWeightInKg() {
        return weighingMachine.getWeightInPound() * 2;
    }
}
