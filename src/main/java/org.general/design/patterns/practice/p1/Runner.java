package design.patterns.practice.p1;

public class Runner {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        int c = a + b;

        AbstractExpression expression = new AddExpression(
                new AddExpression(
                        new NumberExpression(2),
                        new NumberExpression(3)
                ),
                new NumberExpression(4)
        );
        Context context = new Context();
        int result = expression.interpret(context);
        System.out.println("Result: " + result);
    }
}
