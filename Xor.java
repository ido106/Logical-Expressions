

import java.util.Map;

/**
 * @author Ido Aharon
 * class to represent Xor binary expression.
 */
public class Xor extends BinaryExpression {
    /**
     * Constructor 1.
     * @param expression1 first expression
     * @param expression2 second expression
     */
    public Xor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        // if only one expression is true, return true
        if ((e1.evaluate(assignment) && !e2.evaluate(assignment))
                || (!e1.evaluate(assignment) && e2.evaluate(assignment))) {
            return true;
        }
        // else return false
        return false;
    }

    @Override
    public String toString() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        String s = "(" + e1.toString() + " ^ " + e2.toString() + ")";
        return s;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Xor(e1.assign(var, expression), e2.assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.norify();
        e2 = e2.norify();
        // [ ( A NOR A ) NOR ( B NOR B ) ] NOR ( A NOR B )
        Expression norA = new Nor(e1, e1);
        Expression norB = new Nor(e2, e2);
        Expression norAB = new Nor(norA, norB);
        Expression aNorB = new Nor(e1, e2);
        Expression finalNor = new Nor(norAB, aNorB);
        return finalNor;
    }

    @Override
    public Expression nandify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.nandify();
        e2 = e2.nandify();
        // [ A NAND ( A NAND B ) ] NAND [ B NAND ( A NAND B ) ]
        Expression aNandB = new Nand(e1, e2);
        Expression aNandAB = new Nand(e1, aNandB);
        Expression bNandAB = new Nand(e2, aNandB);
        Expression finalNand = new Nand(aNandAB, bNandAB);
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
                    // if left if true - return the right value
                    return new Not(right);
                } else {
                    // if left is false, return false
                    return right;
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
                    return left;
                }
            } catch (Exception ex2) {
                // if both failed
                if (left.toString().equals(right.toString())) {
                    return new Val(false);
                }
            }
        }
        return new Xor(left, right);
    }
}
