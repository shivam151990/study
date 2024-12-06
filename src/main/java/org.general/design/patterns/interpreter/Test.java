package design.patterns.interpreter;

public class Test {
    public static void main(String[] args) {
        Expression<Integer> i1 = new AddExpression(new NumberExpression(10), new NumberExpression(20));
        System.out.println(i1.interpret());
    }
}
