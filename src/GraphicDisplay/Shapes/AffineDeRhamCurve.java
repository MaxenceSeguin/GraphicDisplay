package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Complex;
import GraphicDisplay.Tools.Parameter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AffineDeRhamCurve extends Shape {

    private Parameter a = new Parameter(1, (float)0.5, 0.05, "Alpha");
    private Parameter b = new Parameter(2, (float)0.5, 0.05, "Beta");
    private Parameter d = new Parameter(3, (float)0.5, 0.05, "Delta");
    private Parameter e = new Parameter(4, (float)0.5, 0.05, "Epsilon");
    private Parameter g = new Parameter(5, (float)0.5, 0.05, "Gamma");
    private Parameter n = new Parameter(6, (float)0.5, 0.05, "Nu");
    private Parameter step = new Parameter(7, 1, 1, "Number of Steps");

    public AffineDeRhamCurve(){
        coordinates = new double[]{0,0};
    }

    private void drawComplex(ArrayList<double[]> points){
        Ellipse2D e = new Ellipse2D.Double(0,0,0,0);
        int ind = 0;
        int size = points.size();
        Color c = new Color(255, 137, 28);
        Color k = new Color(95, 7, 171);
        float kr = (float)k.getRed()/255;
        float kg = (float)k.getGreen()/255;
        float kb = (float)k.getBlue()/255;
        float dr = (float)(c.getRed()-k.getRed())/size/255;
        float dg = (float)(c.getGreen()-k.getGreen())/size/255;
        float db = (float)(c.getBlue()-k.getBlue())/size/255;
        for (double[] point : points){
            double u = point[0]; double v = point[1];
            e.setFrame(u, -v, 0.001, 0.001);
            g2.setColor(new Color(kr+ind*dr, kg+ind*dg, kb+ind*db));
            g2.fill(e);
            ind++;
        }
    }

    private double[] affine0(double[] point){
        double u = point[0];
        double v = point[1];
        return new double[]{a.value*u + d.value*v, b.value*u + e.value*v};
    }

    private double[] affine1(double[] point){
        double u = point[0];
        double v = point[1];
        return new double[]{a.value + u*(1-a.value) + v*g.value, b.value - u*b.value + v*n.value};
    }

    private ArrayList<double[]> IFSresult (double[] point){
        ArrayList<double[]> list = new ArrayList<>();
        list.add(point);
        int sequenceSize;
        double[] c, s;

        for (int i = 0; i < step.value; i++){

            sequenceSize = list.size();

            for (int j = 0; j < sequenceSize; j++){
                c = list.remove(j);
                s = affine0(c);
                c = affine1(c);
                list.add(j, c);
                list.add(s);
            }
        }

        return list;
    }

    //TODO Implement affine1 and create table of points.

    @Override
    protected void drawShape() {
        System.out.println("Processing :\nstep  " + step.value + "\n"
                + "alpha  " + a.value + "\n"
                + "beta  " + b.value + "\n"
                + "delta  " + d.value + "\n"
                + "epsilon  " + e.value + "\n"
                + "gamma  " + g.value + "\n"
                + "nu  " + n.value + "\n");

        g2.scale(600, 600);
        g2.setStroke(new BasicStroke((float)0.001));
        g2.setColor(Color.BLACK);
        g2.fillRect(-10, -10, 20, 20);

        double[] z1 = new double[]{0.4, 0.3};

        ArrayList<double[]> finalSequence = IFSresult(z1);

        drawComplex(finalSequence);
        System.out.println("Done");
    }

    @Override
    public Parameter[] parameters() {
        return new Parameter[]{a,b,d,e,g,n,step};
    }

    @Override
    public void changeParameter(Parameter parameter) {
        switch (parameter.id){
            case 1 : a = parameter; return;
            case 2 : b = parameter; return;
            case 3 : d = parameter; return;
            case 4 : e = parameter; return;
            case 5 : g = parameter; return;
            case 6 : n = parameter; return;
            case 7 : step = parameter;
        }
    }
}
