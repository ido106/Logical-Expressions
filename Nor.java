

import java.util.Map;

/**
 * @author Ido Aharon
 * class to represent Nor binary expression.
 */
public class Nor extends BinaryExpression {
    /**
     * constructor 1.
     * @param expression1 first expression
     * @param expression2 second expression
     */
    public Nor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        // return true if both of the statements are false.
        if (!e1.evaluate(assignment) && !e2.evaluate(assignment)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        String s = "(" + e1.toString() + " V " + e2.toString() + ")";
        return s;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Nor(e1.assign(var, expression), e2.assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Nor(e1.norify(), e2.norify());
    }

    @Override
    public Expression nandify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.nandify();
        e2 = e2.nandify();
        // [ ( A NAND A ) NAND ( B NAND B ) ] NAND [ ( A NAND A ) NAND ( B NAND B ) ]
        Expression aNandA = new Nand(e1, e1);
        Expression bNandB = new Nand(e2, e2);
        Expression aNandB = new Nand(aNandA, bNandB);
        Expression finalNand = new Nand(aNandB, aNandB);
        return finalNand;
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
                    // if left if true
                    return new Val(false);
                } else {
                    // if left is false
                    return new Not(right);
                }
            }
        } catch (Exception ex) {
            // if the left exception failed
            try {
                Boolean b2 = right.evaluate();
                // right- succeed
                if (b2) {
                    return new Val(false);
                } else {
                    return new Not(left);
                }
            } catch (Exception ex2) {
                // if both failed
                if (left.toString().equals(right.toString())) {
                    return new Not(left);
                }
            }
        }
        return new Nor(left, right);
    }
}
