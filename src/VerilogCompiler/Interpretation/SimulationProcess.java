/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VerilogCompiler.Interpretation;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimulationProcess {
    SimulationScope simulationScope;
    
    public void init() {
        simulationScope.init();
    }
    
    public void runStep() {
        simulationScope.runStep();
    }
}
