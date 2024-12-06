package design.patterns.abstractfactory;

import java.util.HashMap;
import java.util.Map;

public class GuiCreatorFactory {

    private static final Map<String, Button> buttonMap = new HashMap<>();
    private static final Map<String, CheckBox> checkBoxMap = new HashMap<>();

    static {
        buttonMap.put("WindowsButton", new WindowsButton());
        checkBoxMap.put("WindowsCheckBox", new WindowsCheckBox());
        checkBoxMap.put("MacCheckBox", new MacCheckBox());
        checkBoxMap.put("MacButton", new MacCheckBox());
    }

    public static Button getButton(String type) {
        return buttonMap.get(type);
    }
    public static CheckBox getCheckBox(String type) {
        return checkBoxMap.get(type);
    }
}
