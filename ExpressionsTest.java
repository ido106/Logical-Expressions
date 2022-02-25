

/**
 * @author Ido Aharon
 * class to test our total classes.
 */
public class ExpressionsTest {
    /**
     * the main class to check everything.
     * @param args nothing in here
     */
    public static void main(String[] args) {
        // create the new vars of the expression
        Expression x = new Var("x");
        Expression y = new Var("y");
        Expression z = new Var("z");
        // create the right side of the expression
        Expression finalRight = new Xor(z, z);
        // create the left side of the expression
        Expression left1 = new And(x, y);
        Expression finalLeft = new Or(left1, left1);
        // create the final expression
        Expression finalEx = new Nand(finalLeft, finalRight);
        // print the final expression
        System.out.println(finalEx);
        // assign other values to x,y,z in the final expression and print the result
        Expression assigned = finalEx.assign("x", new Val(true));
        assigned = assigned.assign("y", new Not(new Var("m")));
        assigned = assigned.assign("z", new Or(new Var("a"), new Var("b")));
        System.out.println(assigned);
        // print the nandified version of the final expression
        System.out.println(finalEx.nandify());
        // print the norified version of the final expression
        System.out.println(finalEx.norify());
        // print the simplified version of the final expression
        System.out.println(finalEx.simplify());
    }
}
