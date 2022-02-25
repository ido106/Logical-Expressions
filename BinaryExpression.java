

/**
 * @author Ido Aharon
 * abstract binary expression class that extends base expression class.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression expression1;
    private Expression expression2;

    /**
     * constructor.
     * @param expression1 first expression
     * @param expression2 second expression
     */
    public BinaryExpression(Expression expression1, Expression expression2) {
        super();
        this.expression1 = expression1;
        this.expression2 = expression2;
        super.addVariable(expression1);
        super.addVariable(expression2);
    }

    /**
     * get first expression.
     * @return first expression
     */
    protected Expression getExpression1() {
        return this.expression1;
    }

    /**
     * return second expression.
     * @return second expression
     */
    protected Expression getExpression2() {
        return this.expression2;
    }
}
