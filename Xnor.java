

import java.util.Map;

/**
 * @author Ido Aharon
 * class to represent a Xnor binary expression.
 */
public class Xnor extends BinaryExpression {
    /**
     * constructor 1.
     * @param expression1 the first expression
     * @param expression2 the second expression
     */
    public Xnor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        Xor x = new Xor(e1, e2);
        // return the opposite result of the xor
        return !x.evaluate(assignment);
    }

    @Override
    public String toString() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        // create the new string
        String s = "(" + e1.toString() + " # " + e2.toString() + ")";
        return s;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        return new Xnor(e1.assign(var, expression), e2.assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.norify();
        e2 = e2.norify();
        // [ A NOR ( A NOR B ) ] NOR [ B NOR ( A NOR B ) ]
        Expression aNorB = new Nor(e1, e2);
        Expression aNor1 = new Nor(e1, aNorB);
        Expression bNor1 = new Nor(e2, aNorB);
        Expression finalNor = new Nor(aNor1, bNor1);
        return finalNor;
    }

    @Override
    public Expression nandify() {
        Expression e1 = super.getExpression1();
        Expression e2 = super.getExpression2();
        e1 = e1.nandify();
        e2 = e2.nandify();
        // [ ( A NAND A ) NAND ( B NAND B ) ] NAND ( A NAND B )
        Expression aNandA = new Nand(e1, e1);
        Expression bNandB = new Nand(e2, e2);
        Expression aNandB = new Nand(e1, e2);
        Expression leftNand = new Nand(aNandA, bNandB);
        Expression finalNand = new Nand(leftNand, aNandB);
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
            Boolean b2 = right.evaluate();
            if (b1 == b2) {
                return new Val(true);
            }
        } catch (Exception e) {
            if (left.toString().equals(right.toString())) {
                return new Val(true);
            }
        }
        return new Xnor(left, right);
    }
}
