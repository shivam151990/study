package design.patterns.abstractfactory;

public class MacGuiFactory implements GuiFactory {
    @Override
    public Button getButton() {
        return new MacButton();
    }

    @Override
    public CheckBox getCheckBox() {
        return new MacCheckBox();
    }
}
