package design.patterns.memento;

public class TextEditor {

    private String text;

    public TextEditor(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public TextEditorMemento save() {
        return new TextEditorMemento(text);
    }

    public void restore(TextEditorMemento memento) {
        this.text = memento.getText();
    }

    public void setText(String text) {
        this.text = text;
    }
}
