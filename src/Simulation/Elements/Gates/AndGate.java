/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation.Elements.Gates;

import Exceptions.ArchException;
import java.awt.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudezs@gmail.com >
 */
public class AndGate extends GateElement {

    public AndGate(int x, int y) throws ArchException {
        super(x, y);
    }

    public AndGate(int x, int y, int x2, int y2, String[] extraParams) throws ArchException {
        super(x, y, x2, y2, extraParams);        
    }

    @Override
    public void setPoints() {
        super.setPoints();
        
        Point triPoints[] = newPointArray(23);
        interpolatePoint2(lead1, lead2, triPoints[0], triPoints[22], 0, hs2);
        int i;
        for (i = 0; i != 10; i++) {
            double a = i * .1;
            double b = Math.sqrt(1 - a * a);
            interpolatePoint2(lead1, lead2,
                    triPoints[i + 1], triPoints[21 - i],
                    .5 + a / 2, b * hs2);
        }
        triPoints[11] = new Point(lead2);
        if (isInverting()) {
            littleCircle = interpolatePoint(point1, point2, .5 + (ww + 4) / dn);
            lead2 = interpolatePoint(point1, point2, .5 + (ww + 8) / dn);
        }
        gatePolygon = createPolygon(triPoints);
    }

    @Override
    boolean calcFunction() {
        int i;
        boolean f = true;
        for (i = 0; i != inputCount; i++) {
            //System.out.println("getInput " + i + " = " + getInput(i));
            f &= getInput(i);
        }
        //System.out.println("Voltage ref: " + voltageSourceReference);
        //System.out.println("AndGate output " + f);
        return f;
    }
    
    @Override
    public Element getXmlElement(Document document) {
        Element element = super.getXmlElement(document);
        element.setAttribute("type", AndGate.class.getName());
        
        return element;
    }
}
