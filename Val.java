

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ido Aharon
 * representing truth values
 */
public class Val implements Expression {
    private Boolean b;

    /**
     * Constructor 1.
     * @param b the boolean value (true or false)
     */
    public Val(Boolean b) {
        this.b = b;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) {
        return this.b;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.b;
    }

    @Override
    public List<String> getVariables() {
        List<String> l = new ArrayList<>();
        return l;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
    public String toString() {
        // if the boolean value is true
        if (this.b) {
            return "T";
        } else {
            // if the boolean value is false
            return "F";
        }
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
