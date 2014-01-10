/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Exceptions.ArchException;
import GUI.ContainerPanel;
import GUI.Design.DesignWindow;
import Simulation.Elements.BaseElement;
import Simulation.Elements.Gates.AndGate;
import Simulation.Elements.Gates.NandGate;
import Simulation.Elements.Gates.NorGate;
import Simulation.Elements.Gates.OrGate;
import Simulation.Elements.Gates.XnorGate;
import Simulation.Elements.Gates.XorGate;
import Simulation.Elements.NamedWire;
import Simulation.Elements.Wire;
import VerilogCompiler.SyntacticTree.Declarations.ModuleDecl;
import VerilogCompiler.SyntacticTree.ModuleItems.GateDecl;
import VerilogCompiler.SyntacticTree.ModuleItems.ModuleItem;
import VerilogCompiler.SyntacticTree.Others.GateInstance;
import VerilogCompiler.SyntacticTree.Others.Port;
import VerilogCompiler.SyntacticTree.PortDirection;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class CircuitGenerator {

    //<editor-fold defaultstate="collapsed" desc="Attributes">
    private int elementId = 0;
    HashMap<String, Point> startPoints;
    HashMap<String, Point> endPoints;
    HashMap<ModuleItem, String> moduleItemToIdMapping;
    //</editor-fold>

    private CircuitGenerator() {
        startPoints = new HashMap<String, Point>();
        endPoints = new HashMap<String, Point>();
        moduleItemToIdMapping = new HashMap<ModuleItem, String>();
    }

    public ArrayList<BaseElement> generateFromModule(ModuleDecl module) {
        ArrayList<BaseElement> elements = new ArrayList<BaseElement>();

        //<editor-fold defaultstate="collapsed" desc="Generate Ports">
        ArrayList<Port> ports = module.getPortList();
        int wireDefaultLenght = 20, inputsDefaultLenght = 20, verticalSpacing = 20;
        int currentX = 0, currentY = 20, currentX2 = 50, currentY2 = 20;
        for (Port port : ports) {
            try {
                String id = getNewElementId();
                if (port.getDirection() == PortDirection.INPUT) {
                    NamedWire portInput = new NamedWire(currentX2, currentY2,
                            currentX, currentY, new String[]{ port.getIdentifier() });
                    elements.add(portInput);
                    currentX += currentX2;
                    currentX2 *= 2;
                } else {
                }
                Wire portWire = new Wire(currentX, currentY, currentX2, currentY2, null);
                portWire.setId(id);
                elements.add(portWire);

                currentX2 /= 2;
                currentX -= currentX2;
                currentY += verticalSpacing;
                currentY2 += verticalSpacing;
            } catch (ArchException ex) {
                Logger.getLogger(DesignWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Gates">
        ArrayList<GateDecl> gates = module.getGates();
        for (GateDecl gateDecl : gates) {
            String className = "";
            switch (gateDecl.getGateType()) {
                case AND:
                    className = AndGate.class.getName();
                    break;
                case NAND:
                    className = NandGate.class.getName();
                    break;
                case NOR:
                    className = NorGate.class.getName();
                    break;
                case OR:
                    className = OrGate.class.getName();
                    break;
                case XNOR:
                    className = XnorGate.class.getName();
                    break;
                case XOR:
                    className = XorGate.class.getName();
                    break;
            }
            ArrayList<GateInstance> instances = gateDecl.getGateInstanceList();
            for (GateInstance gateInstance : instances) {
                String id = getNewElementId();
                int terminalCount = gateInstance.getTerminalList().size();
                moduleItemToIdMapping.put(gateDecl, id);

                BaseElement gate = constructElement(className, currentX, currentY,
                        currentX + 50, currentY, new String[] { ""+terminalCount });
                elements.add(gate);
            }
        }
        //</editor-fold>

        return elements;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Construction Methods">
    public BaseElement constructElement(String type, int x, int y) {
        Class[] proto = new Class[2];
        proto[0] = proto[1] = int.class;

        Class baseElementClass;
        try {
            baseElementClass = Class.forName(type);
            Constructor constructor = baseElementClass.getConstructor(proto);

            Object[] params = new Object[2];
            params[0] = x;
            params[1] = y;

            BaseElement element = (BaseElement) constructor.newInstance(params);
            return element;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public BaseElement constructElement(String type,
            int x, int y, int x2, int y2, String[] extraParams) {
        Class[] proto = new Class[5];
        proto[0] = proto[1] = proto[2] = proto[3] = int.class;
        proto[4] = String[].class;

        Class baseElementClass;
        try {
            baseElementClass = Class.forName(type);
            Constructor constructor = baseElementClass.getConstructor(proto);

            Object[] params = new Object[5];
            params[0] = x;
            params[1] = y;
            params[2] = x2;
            params[3] = y2;
            params[4] = extraParams;

            BaseElement element = (BaseElement) constructor.newInstance(params);
            return element;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //</editor-fold>
    
    public String getNewElementId() {
        return "baseElement" + (elementId++);
    }

    public static CircuitGenerator getInstance() {
        return CircuitGeneratorHolder.INSTANCE;
    }

    private static class CircuitGeneratorHolder {

        private static final CircuitGenerator INSTANCE = new CircuitGenerator();
    }
}
