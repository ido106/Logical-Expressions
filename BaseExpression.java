

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ido Aharon
 * abstract class to of base expression that impelements Expression interface.
 */
public abstract class BaseExpression implements Expression {
    private List<String> variables;

    /**
     * Constructor 1.
     */
    public BaseExpression() {
        this.variables = new ArrayList<>();
    }

    /**
     * method to add a variable to the list.
     * @param e the expression
     */
    protected void addVariable(Expression e) {
        List<String> vList = e.getVariables();
        for (String s : vList) {
            if (!this.variables.contains(s)) {
                this.variables.add(s);
            }
        }
    }

    @Override
    public List<String> getVariables() {
        return this.variables;
    }

    @Override
    public Boolean evaluate() throws Exception {
        Map<String, Boolean> m = new TreeMap<>();
        return evaluate(m);
    }

    @Override
    public abstract Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    @Override
    public abstract String toString();

    @Override
    public abstract Expression assign(String var, Expression expression);

    @Override
    public abstract Expression norify();

    @Override
    public abstract Expression nandify();

    @Override
    public abstract Expression simplify();
}
