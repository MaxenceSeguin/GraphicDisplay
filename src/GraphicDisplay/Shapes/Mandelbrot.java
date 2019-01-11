package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Complex;
import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static java.lang.Math.*;

public class Mandelbrot extends Shape {

    private int iteration; //TODO Implement modulo for better visuals on the colors
    private double ix1 = -4, iy1=-4, ix2=6, iy2=6, di = 0.1, d = 0.1;
    public Parameter delta = new Parameter(1, 0, 1, "Delta");
    public Parameter maxIteration = new Parameter(2, 60, 10, "Max iterations");
    public Parameter ship = new Parameter(3, 2, 0.2, "Ship length");


    public Mandelbrot(){
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
                //new Rectangle2D.Double(ix1,iy1,delta+delta/15,delta+delta/15);

        Color c1 = new Color(1, 2, 39);
        Color c2 = new Color(193, 85, 31);
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
                                    (float)((r1 + (5*iteration*dr)%arr)/255),
                                    (float)(g1 + (5*iteration*dg)%agg)/255,
                                    (float)(b1 + (5*iteration*db)%abb)/255
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

        double X = 0, Y = 0, Xtemp, Ytemp;

        while (!diverge && iteration < maxIteration.value){
            Xtemp = X*X - Y*Y + x;
            Ytemp = abs(ship.value*X*Y) + y;
            if (X == Xtemp && Y == Ytemp){
                iteration = (int) maxIteration.value;
            }
            X = Xtemp;
            Y = Ytemp;
            if(X*X + Y*Y > pow(2,2)){
                diverge = true;
            }
            iteration++;
        }

        return !diverge;
    }

    /*private boolean mandelbrotBelongingTest(double x, double y){

        boolean diverge = false;
        iteration = 0;

        c.set(x, y);

        cm.set(1,0); cm.divides(c); cm.divides(c);

        z.set(0, 0);

        while (!diverge && iteration < maxIteration.value){
            z.times(z);
            z.plus(c);
            if(z.abs() > 2){
                diverge = true;
            }
            iteration++;
        }

        return !diverge;
    }*/

    @Override
    public Parameter[] parameters() {
        return new Parameter[]{delta, maxIteration, ship};
    }

    @Override
    public void changeParameter(Parameter parameter) {
        if (parameter.id == 1){
            d = di * pow(2, parameter.value);
        } else if (parameter.id == 2){
            maxIteration = parameter;
        } else if (parameter.id == 3){
            ship = parameter;
        }
        currentAbscissa = 0;
    }
}
