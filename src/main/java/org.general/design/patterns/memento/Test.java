package design.patterns.memento;

public class Test {
    public static void main(String[] args) {
        TextEditorCareTaker ct = new TextEditorCareTaker();
        TextEditor t1 = new TextEditor("Text 1");

        ct.save(t1);
        t1.setText("Text 2");
        ct.save(t1);
        t1.setText("Text 3");
        ct.save(t1);

        ct.restore(t1);
        ct.restore(t1);
        System.out.println(t1.getText());
    }
}
