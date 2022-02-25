

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ido Aharon
 * class to represent a variable and it's value
 */
public class Var implements Expression {
    private String var;

    /**
     * constructor 1.
     * @param var the var name
     */
    public Var(String var) {
        this.var = var;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // if the assignment doesn't contain this var throw exception
        if (!assignment.containsKey(this.var)) {
            throw new Exception("The variable doesn't exist in the assignment.");
        } else {
            return assignment.get(this.var);
        }
    }

    @Override
    public Boolean evaluate() throws Exception {
        throw new Exception("The variable doesn't exist in the assignment.");
    }

    @Override
    public List<String> getVariables() {
        // return a list with the var name
        List<String> l = new ArrayList<>();
        l.add(this.var);
        return l;
    }

    @Override
    public Expression assign(String var1, Expression expression) {
        // there is only one string in this class, so just check if the provided string equals this var string.
        if (var1.equals(this.var)) {
            return expression;
        }
        return this;
    }

    @Override
    public String toString() {
        return this.var;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    /**
     * method to get string name.
     * @return name
     */
    public String getVarName() {
        return this.var;
    }

    /**
     * method to return the simplified value- just return the var.
     * @return the var
     */
    public Expression simplify() {
        return this;
    }
}
