package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.Shape;
import java.awt.geom.Line2D;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class QuadraticKoch extends GraphicDisplay.Shapes.Shape {

    public Parameter n = new Parameter(1, 5, 1, "Step number");
    public Parameter amountLines = new Parameter(2, 3, 1, "Amount of edges");
    public Parameter quadAngle = new Parameter(3, 2, 0.05, "Angle");


    private void qKoch(Graphics2D g2, float nn){

        this.n.value = nn;
        int dim = 4;
        double mZoom = (double)1/1.5/(pow(dim,n.value-1));

        g2.scale(mZoom, mZoom);
        g2.setStroke(new BasicStroke((int)(pow(dim,n.value-1)/dx)));
        g2.translate(
                (coordinates[0] + 100) * pow(dim, n.value)*3/ dim,
                (coordinates[1] + 100) * pow(dim, n.value)*3/dim);

        for (int j = 0; j < amountLines.value; j++){
            quadKoch(g2, n.value);
            g2.rotate(2*PI/amountLines.value, currentAbscissa, 0);
        }
    }

    private void quadKoch(Graphics2D g2, float n){
        double[] angles = {-PI/quadAngle.value, PI/quadAngle.value, PI/quadAngle.value, 0, -PI/quadAngle.value,
                -PI/quadAngle.value, PI/quadAngle.value, 0};
        Color[] colors = {
                new Color(0xFF0000),
                new Color(0xFFED00),
                new Color(0x00FF12),
                new Color(0x00FFF6),
                new Color(0x0015FF),
                new Color(0x8300FF),
                new Color(0xFF00F6),
                new Color(0xFF0083),
        };

        if (n != 1){
            for (int i = 0; i < 8; i++){
                quadKoch(g2, n-1);
                g2.rotate(-angles[i], currentAbscissa, 0);
            }
        } else {
            for (int i = 0; i < 8; i++){
                Shape l = new Line2D.Double(currentAbscissa, 0, currentAbscissa+ 300, 0);
                g2.setColor(colors[i]);
                g2.draw(l);
                currentAbscissa += 300;
                g2.rotate(-angles[i], currentAbscissa, 0);
            }
        }
    }

    public void changeParameter(Parameter parameter){
        if (parameter.id == 1){
            n = parameter;
        } else if (parameter.id == 2){
            amountLines = parameter;
        } else {
            quadAngle = parameter;
        }
        currentAbscissa = 0;
    }

    public Parameter[] parameters(){
        return new Parameter[]{quadAngle, amountLines, n};
    }

    protected void drawShape(){
        qKoch(g2,n.value);
    }
}
