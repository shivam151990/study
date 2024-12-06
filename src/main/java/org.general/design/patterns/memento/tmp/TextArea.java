package design.patterns.memento.tmp;

/**
 * t1 -> text1
 * remember()
 * t2 -> text2()
 * remember()
 * t3 -> text3()
 * remember()
 * undo()
 */

public class TextArea {

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public void restore(Memento memento) {
        this.text = memento.getText();
    }

    public Memento takeSnapshot() {
        return new Memento(this.text);
    }

    public static class Memento {
        private final String text;

        private Memento(String text) {
            this.text = text;
        }

        private String getText() {
            return text;
        }
    }
}
