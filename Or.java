

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ido Aharon
 * Class to represent an Or binary expression.
 */
public class Or extends BinaryExpression {
    /**
     * Constructor 1.
     * @param expression1 the first expression
     * @param expression2 the second expression
     */
    public Or(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        Map<String, Boolean> m = new TreeMap<>();
        return evaluate(m);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return (e1.evaluate(assignment) || e2.evaluate(assignment));
    }

    @Override
    public String toString() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        String s = "(" + e1.toString() + " | " + e2.toString() + ")";
        return s;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Or(e1.assign(var, expression), e2.assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.norify();
        e2 = e2.norify();
        // ( A NOR B ) NOR ( A NOR B )
        Expression aNorB = new Nor(e1, e2);
        Expression finalNor = new Nor(aNorB, aNorB);
        return finalNor;
    }

    @Override
    public Expression nandify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.nandify();
        e2 = e2.nandify();
        // ( A NAND A ) NAND ( B NAND B )
        Expression aNandA = new Nand(e1, e1);
        Expression bNandB = new Nand(e2, e2);
        Expression finalNand = new Nand(aNandA, bNandB);
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
                return new Val(b1 || b2);
            } catch (Exception ex) {
                // left- success. right- failed
                if (b1) {
                    // if left if true - return true
                    return left;
                } else {
                    // if left is false, return the right
                    return right;
                }
            }
        } catch (Exception ex) {
            // if the left exception failed
            try {
                Boolean b2 = right.evaluate();
                // right- succeed
                if (b2) {
                    return right;
                } else {
                    return left;
                }
            } catch (Exception ex2) {
                // if both failed
                if (left.toString().equals(right.toString())) {
                    return left;
                }
            }
        }
        return new Or(left, right);
    }
}
