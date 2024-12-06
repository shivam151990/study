package design.patterns.memento.tmp;

import design.patterns.memento.tmp.TextArea.Memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditor {

    private Deque<Memento> stk;
    private TextArea textArea;

    public TextEditor() {
        stk = new ArrayDeque<>();
        textArea = new TextArea();
    }

    public void criteriaToSaveSnapshot(String text) {
        textArea.setText(text);
        stk.push(textArea.takeSnapshot());
    }

    public void undo() {
        Memento prevState = stk.pop();
        textArea.restore(prevState);
    }

}
