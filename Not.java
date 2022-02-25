

import java.util.Map;

/**
 * @author Ido Aharon
 * class to represent a Not expression.
 */
public class Not extends UnaryExpression {
    /**
     * constructor.
     * @param expression the expression
     */
    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Expression e = super.getExpression();
        return  !(e.evaluate(assignment));
    }

    @Override
    public String toString() {
        Expression e = super.getExpression();
        String s = "~(" + e.toString() + ")";
        return s;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        Expression e = super.getExpression();
        return new Not(e.assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression e = super.getExpression();
        e = e.norify();
        // A NOR A
        return new Nor(e, e);
    }

    @Override
    public Expression nandify() {
        Expression e = super.getExpression();
        e = e.nandify();
        // A NAND A
        return new Nand(e, e);
    }

    @Override
    public Expression simplify() {
        Expression e = super.getExpression();
        try {
            boolean b = e.evaluate();
            return new Val(!b);
        } catch (Exception ex) {
            return new Not(e.simplify());
        }
    }
}
