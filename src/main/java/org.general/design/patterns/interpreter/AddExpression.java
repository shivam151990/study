package design.patterns.interpreter;

public class AddExpression implements Expression<Integer> {

    private Expression<Integer> left;
    private Expression<Integer> right;

    public AddExpression(Expression<Integer> left, Expression<Integer> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer interpret() {
        return left.interpret() + left.interpret();
    }
}
