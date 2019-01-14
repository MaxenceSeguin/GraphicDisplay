package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.geom.Line2D;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class KochFlake extends Shape {

    public Parameter flakeAngle = new Parameter(1, 3, 0.05, "Angle of Koch");
    public Parameter amountLines = new Parameter(2, 3, 1, "Amount of edges");
    public Parameter n = new Parameter(3, 5, 1, "Step number");


    private void koch(Graphics2D g2, float nn){
        this.n.value = nn;

        double dim = 2+2*sin((flakeAngle.value -2)*PI/ flakeAngle.value /2); /* Length of an edge */
        double mZoom = (double)1/1.5/(pow(dim,n.value-1)); /* Zoom according to the flakeAngle value */

        g2.scale(mZoom, mZoom);
        g2.setStroke(new BasicStroke((int)(pow(dim,n.value-1)/dx)));
        g2.translate(
                (coordinates[0] + 100) * pow(dim, n.value)*3/ dim,
                (coordinates[1] + 100) * pow(dim, n.value)*3/dim);

        for (int j = 0; j < amountLines.value; j++){
            kochLine(g2, n.value);
            g2.rotate(2*PI/amountLines.value, currentAbscissa, 0);
        }

    }

    private void kochLine(Graphics2D g2, float n){

        double[] angles = {PI/ flakeAngle.value, 2*PI-2*PI/ flakeAngle.value, PI/ flakeAngle.value, 0};
        Color[] colors = {new Color(202,0, 255),
                new Color(1,0, 255),
                new Color(0x5A37FF),
                new Color(0xFFA4F9)};

        if (n != 1){
            for (int i = 0; i < 4; i++){
                kochLine(g2, n-1);
                g2.rotate(-angles[i], currentAbscissa, 0);
            }
        } else {
            for (int i = 0; i < 4; i++){
                java.awt.Shape l = new Line2D.Double(currentAbscissa, 0, currentAbscissa + 300, 0);
                g2.setColor(colors[i]);
                g2.draw(l);
                currentAbscissa += 300;
                g2.rotate(-angles[i], currentAbscissa, 0);
            }
        }

    }

    public void changeParameter(Parameter parameter){
         if (parameter.id == 1){
             flakeAngle = parameter;
         } else if (parameter.id == 2){
             amountLines = parameter;
         } else {
             n = parameter;
         }
         currentAbscissa = 0;
    }

    public Parameter[] parameters(){
        return new Parameter[]{flakeAngle, amountLines, n};
    }

    protected void drawShape(){

        g2.setColor(Color.MAGENTA);
        g2.drawLine(0,0,1000,1000);
        koch(g2,n.value);
        g2.drawLine(0,0,1000,1000);
    }
}
