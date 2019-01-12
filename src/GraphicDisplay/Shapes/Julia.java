package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Complex;
import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.geom.Line2D;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Julia extends Shape{

    private int iteration;
    private double ix1 = -4, iy1=-4, ix2=6, iy2=6, di = 0.1, d = 0.1;
    public Parameter delta = new Parameter(1, 0, 1, "Delta");
    public Parameter maxIteration = new Parameter(2, 100, 20, "Max iterations");
    public Parameter cx = new Parameter(3, 1, 0.005, "c abscissa");
    public Parameter cy = new Parameter(4, 1, 0.005, "c ordinate");

    public Julia(){
        coordinates = new double[]{0,0};
        dx = 1.5;
        dy = 1.5;
    }

    @Override
    protected void drawShape() {
        System.out.println("Processing");
        g2.scale(1/dx, 1/dy);
        double distancex = ix2 - ix1, distancey = iy2 - iy1;
        double centerx = ix1 + distancex/2, centery = iy1 + distancey/2;
        ix1 = centerx - distancex / 2 / dx; ix2 = centerx + distancex / 2 / dx;
        iy1 = centery - distancey / 2 / dy; iy2 = centery + distancey / 2 / dy;
        double xtranslatation = coordinates[0] / 25 * (ix2-ix1) / 15; // 15 is the translation parameter
        double ytranslatation = coordinates[1] / 25 * (iy2-iy1) / 15;
        g2.scale(1400/(-ix1+ix2), 1400/(ix2-ix1));
        ix1 += xtranslatation; ix2 += xtranslatation; iy1 += ytranslatation; iy2 += ytranslatation;
        g2.translate(-ix1, -iy1);
        d /= dx;
        mandelbrot();
        coordinates = new double[]{0,0};
        dx = 1; dy = 1;
        System.out.println("Done " + d);
    }

    private void mandelbrot(){
        g2.setStroke(new BasicStroke((float)d));

        Line2D l = new Line2D.Double(ix1, iy1, ix1+d, iy1);

        Color c1 = new Color(17, 0, 12);
        Color c2 = new Color(255, 187, 239);
        int r1=c1.getRed(), r2=c2.getRed(), g1=c1.getGreen(), g2=c2.getGreen(), b1=c1.getBlue(), b2=c2.getBlue();
        float rr = (float)r2-r1, gg = (float)g2-g1, bb = (float)b2-b1, arr = abs(rr), abb = abs(bb), agg = abs(gg);
        double dr=rr/maxIteration.value, dg=gg/maxIteration.value, db=bb/maxIteration.value;

        for (double x = ix1; x < ix2; x += d){
            for (double y = iy1; y < iy2; y += d){
                if (mandelbrotBelongingTest(x,y)){
                    this.g2.setColor(Color.BLACK);
                } else {
                    this.g2.setColor(new Color(
                            /*(int)(r1+3*iteration)%256,
                            (int)(g1+4*iteration)%256,
                            (int)(b1+5*iteration)%256)*/
                            (float)((r1 + (15*iteration*dr)%arr)/255),
                            (float)(g1 + (15*iteration*dg)%agg)/255,
                            (float)(b1 + (15*iteration*db)%abb)/255
                    ));
                }
                l.setLine(x,y,x+d,y+d);
                this.g2.draw(l);
            }
        }
    }

    private Complex c = new Complex(0, 0);
    private Complex c1 = new Complex(0, 0);

    private Complex cm = new Complex(1,0);
    private Complex z = new Complex(0, 0);
    private Complex feigen = new Complex(-1.401155, 0);


    private boolean mandelbrotBelongingTest(double x, double y){

        boolean diverge = false;
        iteration = 0;

        z.set(x, y);

        c.set(cx.value, cy.value);

        //cm.set(1,0); cm.divides(c);


        while (!diverge && iteration < maxIteration.value){
            z.cubic();
            z.plus(c);
            if(z.abs() > 2){
                diverge = true;
            }
            iteration++;
        }

        return !diverge;
    }

    @Override
    public Parameter[] parameters() {
        return new Parameter[]{delta, maxIteration, cx, cy};
    }

    @Override
    public void changeParameter(Parameter parameter) {
        if (parameter.id == 1){
            d = di * pow(2, parameter.value);
        } else if (parameter.id == 2){
            maxIteration = parameter;
        } else if (parameter.id == 3){
            cx = parameter;
        } else if (parameter.id == 4){
            cy = parameter;
        }
        currentAbscissa = 0;
    }
}

