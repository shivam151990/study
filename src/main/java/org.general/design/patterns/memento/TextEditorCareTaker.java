package design.patterns.memento;

import java.util.Deque;
import java.util.LinkedList;

public class TextEditorCareTaker {

    private Deque<TextEditorMemento> memory;

    public TextEditorCareTaker() {
        this.memory = new LinkedList<>();
    }

    public void save(TextEditor editor) {
        memory.push(editor.save());
    }

    public void restore(TextEditor editor) {
        if (!memory.isEmpty()) {
            editor.restore(memory.pop());
        }
    }

}
