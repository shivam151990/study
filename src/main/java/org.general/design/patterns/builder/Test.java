package design.patterns.builder;

public class Test {
    public static void main(String[] args) {
        Student student = new Student.Builder("shivam", 99)
                            .setPh("9742745991")
                            .setEmail("shivam151990@gmail.com")
                            .create();
        System.out.println(student);
    }
}
