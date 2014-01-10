/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VerilogCompiler.SyntacticTree.Expressions;

import VerilogCompiler.Interpretation.ExpressionValue;
import VerilogCompiler.Interpretation.SimulationScope;
import VerilogCompiler.SemanticCheck.ExpressionType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class StringExpression extends Expression {
    String value;

    public StringExpression(String value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public ExpressionType validateSemantics() {
        return ExpressionType.STRING;
    }

    @Override
    public ExpressionValue evaluate(SimulationScope simulationScope, String moduleName) {
        return new ExpressionValue(value, 0);
    }
    
}
