

/**
 * @author Ido Aharon
 * abstract unary expression class that extends base expression class.
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression expression;

    /**
     * constructor.
     * @param e the expression
     */
    public UnaryExpression(Expression e) {
        super();
        this.expression = e;
        super.addVariable(e);
    }

    /**
     * return the expression.
     * @return expression
     */
    protected Expression getExpression() {
        return this.expression;
    }
}
