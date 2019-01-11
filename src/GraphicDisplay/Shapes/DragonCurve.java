package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static java.lang.Math.*;

public class DragonCurve extends Shape {

    public Parameter n = new Parameter(1, 1, 1, "Step number");
    public Parameter angle = new Parameter(2, 2, 0.05, "Angle");

    private ArrayList<Integer> angleVector(){
        ArrayList<Integer> angles = new ArrayList<>();
        angles.add(1);
        for (int i = 0; i < n.value; i++){
            angles.add(1);
            int size = angles.size();
            for (int j = 0; j < size-1; j++){
                angles.add(-angles.get(size-2-j));
            }
        }
        return angles;
    }

    private void curve(){

        float edgeLength = 300;

        java.awt.Shape line;
        ArrayList<Integer> angles = angleVector();
        int i = 0;
        int size = angles.size();

        //Color c1 = new Color(255, 215, 245);
        //Color c2 = new Color(113, 2, 196);
        Color c1 = new Color((int)(random()*255), (int)(random()*255), (int)(random()*255));
        Color c2 = new Color((int)(random()*255), (int)(random()*255), (int)(random()*255));

        float dr = (float) (c1.getRed() - c2.getRed()) / size,
                dg = (float) (c1.getGreen() - c2.getGreen()) / size,
                db = (float) (c1.getBlue() - c2.getBlue()) / size;
        float r = c2.getRed(), g = c2.getGreen(), b = c2.getBlue();

        for (Integer sign : angles){
            line = new Line2D.Double(currentAbscissa, 0, currentAbscissa + edgeLength, 0);
            g2.setColor(new Color((int)r,(int)g,(int)b));
            r += (r < 255)? dr : 0;
            g += (g < 255)? dg : 0;
            b += (b < 255)? db : 0;
            g2.draw(line);
            currentAbscissa += edgeLength;
            g2.rotate(sign * PI/angle.value, currentAbscissa, 0);
            i++;
        }
        line = new Line2D.Double(currentAbscissa, 0, currentAbscissa + edgeLength, 0);
        g2.draw(line);
        currentAbscissa += edgeLength;
    }

    private void dragon(){
        double zoom = 1/pow(pow(2,0.5),n.value);
        g2.rotate(-PI/4 * n.value, 0, 0);

        g2.scale(zoom,zoom);
        g2.setStroke(new BasicStroke((float) (1/zoom)/3));

        curve();
        curve();
        curve();
        curve();
        g2.rotate(PI/2, currentAbscissa, 0);
        curve();
        curve();
        curve();
        curve();
    }

    @Override
    protected void drawShape() {
        dragon();
    }

    @Override
    public Parameter[] parameters() {
        return new Parameter[]{n, angle};
    }

    @Override
    public void changeParameter(Parameter parameter) {
        if (parameter.id == 1){
            n = parameter;
        } else if  (parameter.id == 2){
            angle = parameter;
        }
        currentAbscissa = 0;
    }
}
