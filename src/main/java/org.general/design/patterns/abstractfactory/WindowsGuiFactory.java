package design.patterns.abstractfactory;

public class WindowsGuiFactory implements GuiFactory {
    @Override
    public Button getButton() {
        return new WindowsButton();
    }

    @Override
    public CheckBox getCheckBox() {
        return new WindowsCheckBox();
    }
}
