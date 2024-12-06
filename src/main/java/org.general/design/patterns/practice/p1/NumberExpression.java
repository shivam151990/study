package design.patterns.practice.p1;

public class NumberExpression implements AbstractExpression {

    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret(Context context) {
        return number;
    }
}
