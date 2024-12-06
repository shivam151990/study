package design.patterns.interpreter;

public class NumberExpression implements Expression <Integer> {

    private Integer item;

    public NumberExpression(Integer item) {
        this.item = item;
    }

    @Override
    public Integer interpret() {
        return item;
    }
}
