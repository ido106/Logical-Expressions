

import java.util.Map;

/**
 * @author Ido Aharon
 * class to represent Nand binary expression.
 */
public class Nand extends BinaryExpression {
    /**
     * Constructor 1.
     * @param expression1 first expression
     * @param expression2 second expression
     */
    public Nand(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        // if one of the statements or more is false return true
        if (!e1.evaluate(assignment) || !e2.evaluate(assignment)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        String s = "(" + e1.toString() + " A " + e2.toString() + ")";
        return s;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Nand(e1.assign(var, expression), e2.assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.norify();
        e2 = e2.norify();
        // [ ( A NOR A ) NOR ( B NOR B ) ] NOR [ ( A NOR A ) NOR ( B NOR B ) ]
        Expression aNorA = new Nor(e1, e1);
        Expression bNorB = new Nor(e2, e2);
        Expression norAB = new Nor(aNorA, bNorB);
        Expression finalNor = new Nor(norAB, norAB);
        return finalNor;
    }

    @Override
    public Expression nandify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Nand(e1.nandify(), e2.nandify());
    }

    @Override
    public Expression simplify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        Expression left = e1.simplify();
        Expression right = e2.simplify();
        try {
            // calculate left expression
            Boolean b1 = left.evaluate();
            try {
                // calculate right expression
                Boolean b2 = right.evaluate();
                return new Val(this.evaluate());
            } catch (Exception ex) {
                // left- success. right- failed
                if (b1) {
                    // if left if true - return the right value
                    return new Not(right);
                } else {
                    // if left is false, return false
                    return new Val(true);
                }
            }
        } catch (Exception ex) {
            // if the left exception failed
            try {
                Boolean b2 = right.evaluate();
                // right- succeed
                if (b2) {
                    return new Not(left);
                } else {
                    return new Val(true);
                }
            } catch (Exception ex2) {
                // if both failed
                if (left.toString().equals(right.toString())) {
                    return new Not(left);
                }
            }
        }
        return new Nand(left, right);
    }
}
