package design.patterns.practice.p1;

public class AddExpression implements AbstractExpression {

    private AbstractExpression exp1;
    private AbstractExpression exp2;

    public AddExpression(AbstractExpression exp1, AbstractExpression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public int interpret(Context context) {
        return exp1.interpret(context) + exp2.interpret(context);
    }
}
