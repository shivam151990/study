package design.patterns.abstractfactory;

public class WindowsCheckBox implements CheckBox {
    @Override
    public void paint() {
        System.out.println("Windows style checkbox");
    }
}
