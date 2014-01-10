/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VerilogCompiler.SyntacticTree.Expressions;

import VerilogCompiler.Interpretation.ExpressionValue;
import VerilogCompiler.SemanticCheck.ErrorHandler;
import VerilogCompiler.SemanticCheck.ExpressionType;
import VerilogCompiler.SyntacticTree.Base;
import VerilogCompiler.Utils.StringUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SizedHexNumberExpression extends NumberExpression {
    int size;
    Base base;
    String hexNumber;
    
    Long evaluatedValue;

    public SizedHexNumberExpression(int size, Base base, String hexNumber, int line, int column) {
        super(line, column);
        this.size = size;
        this.base = base;
        this.hexNumber = hexNumber;
    }
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public String getHexNumber() {
        return hexNumber;
    }

    public void setHexNumber(String hexNumber) {
        this.hexNumber = hexNumber;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", size, 
                StringUtils.getInstance().BaseToString(base), hexNumber);
    }

    @Override
    public ExpressionType validateSemantics() {
        try {
            evaluatedValue = Long.decode("#" + hexNumber);
            return ExpressionType.INTEGER;
        } catch (NumberFormatException e) {
            ErrorHandler.getInstance().handleError(line, column, 
                    hexNumber + " is not a valid hexadecimal number");
            return ExpressionType.ERROR;
        }        
    }

    @Override
    public ExpressionValue evaluate(VerilogCompiler.Interpretation.SimulationScope simulationScope, 
    String moduleName) {
        return new ExpressionValue(evaluatedValue, size);
    }
    
}
