package design.patterns.composite;

public class Runner {
    public static void main(String[] args) {
        File f1 = new File("file1");
        File f2 = new File("file2");

        Directory d1 = new Directory("d1");
        Directory d2 = new Directory("d2");
        d2.addComponent(f1);
        d2.addComponent(f2);

        d2.showDetails();
    }
}
