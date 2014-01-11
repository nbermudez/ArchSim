/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VerilogCompiler.SyntacticTree.Expressions;

import VerilogCompiler.Interpretation.ExpressionValue;
import VerilogCompiler.SemanticCheck.ExpressionType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleNumberExpression extends NumberExpression {
    long unsignedNumber;

    public SimpleNumberExpression(long unsignedNumber, int line, int column) {
        super(line, column);
        this.unsignedNumber = unsignedNumber;
    }

    public long getUnsignedNumber() {
        return unsignedNumber;
    }

    public void setUnsignedNumber(int unsignedNumber) {
        this.unsignedNumber = unsignedNumber;
    }

    @Override
    public String toString() {
        return Long.toString(unsignedNumber);
    }

    @Override
    public ExpressionType validateSemantics() {
        return ExpressionType.INTEGER;
    }

    @Override
    public ExpressionValue evaluate(VerilogCompiler.Interpretation.SimulationScope simulationScope, String moduleName) {
        return new ExpressionValue(unsignedNumber, 32);
    }
    
}
