package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Complex;
import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;

public class DeRhamCurve extends Shape {

    Parameter n = new Parameter(1, 1, 1, "Number of steps");
    Parameter ax = new Parameter(2, (float)0.5, 0.001, "Affine parameter real part");
    Parameter ay = new Parameter(3, (float)0.5, 0.001, "Affine parameter imaginary part");
    Complex a = new Complex(ax.value, ay.value);

    public DeRhamCurve(){
        coordinates = new double[]{0, 0};
    }

    /**
     * Draws on the Graphics2D the point in the complex plan.
     * @param points the list of points to print
     */
    private void drawComplex(ArrayList<Complex> points){
        Line2D l = new Line2D.Double(0, 0, 0, 0);
        double d = 0.0001;
        int ind = 0;
        float unit = (float)1/points.size();
        Color c = new Color(255, 69, 19);
        for (Complex z : points){
            double re = z.re(); double im = z.im();
            l.setLine(re, -im, re + d, -im);
            g2.setColor(new Color(min(unit*ind, 1), 0, min(ind*unit, 1)));
            g2.draw(l);
            ind++;
        }
    }

    /**
     * Creates a boolean table that will identify the order in which the functions will be call to build the next step.
     */
    private boolean[] functionOrder(){
        boolean res[] = new boolean[(int)n.value];
        Random rand = new Random();
        for (int i = 0; i < n.value; i++){
            res[i] = rand.nextBoolean();
        }
        return res;
    }

    /**
     * Build the sequence of points to print using the "chaos game" technique. (slower)
     */
    private ArrayList<Complex> IFSresult (ArrayList<Complex> sequence){

        boolean[] order = functionOrder();
        int sequenceSize;
        int index;
        Complex nextValue;

        for (int i = 0; i < n.value; i++){

            sequenceSize = sequence.size();
            index = 0;
            if (order[i]){
                while (index < sequenceSize){
                    nextValue = d0(sequence.get(index));
                    sequence.add(nextValue);
                    index++;
                }
            } else {
                while (index < sequenceSize){
                    nextValue = d1(sequence.get(index));
                    sequence.add(nextValue);
                    index++;
                }
            }
        }
        return sequence;
    }

    /**
     * Build the sequence by taking a point and then iterating every possible combination of function (do and d1).
     * (faster)
     */
    private ArrayList<Complex> IFSresult2 (Complex z){
        ArrayList<Complex> list = new ArrayList<>();
        list.add(z);
        int sequenceSize;
        Complex c, s;

        for (int i = 0; i < n.value; i++){

            sequenceSize = list.size();

            for (int j = 0; j < sequenceSize; j++){
                c = list.remove(j);
                s = d0(c);
                c = d1(c);
                list.add(j, c);
                list.add(s);
            }
        }

        return list;
    }

    /**
     * First function we are using to build the sequence.
     */
    private Complex d0 (Complex z){
        Complex z2 = new Complex(z.re(), z.im());
        z2.times(a);
        return z2;
    }

    /**
     * Second function we are using to build he sequence.
     */
    private Complex d1 (Complex z){
        Complex z2 = new Complex(z.re(), z.im());
        Complex A = new Complex(1-a.re(), -a.im());
        z2.times(A);
        z2.plus(a);
        return z2;
    }

    @Override
    protected void drawShape() {
        g2.scale(600, 600);
        g2.setStroke(new BasicStroke((float)0.001));
        a.set(ax.value, ay.value);
        g2.setColor(Color.BLACK);
        g2.fillRect(-10, -10, 20, 20);

        Complex z1 = new Complex(0.4, 0.3);
        Complex z2 = new Complex(0.4, 0.7);
        Complex z3 = new Complex(0.6, 0.3);
        Complex z4 = new Complex(0.6, 0.7);
        ArrayList<Complex> zList = new ArrayList<>();
        zList.add(z1);zList.add(z2);zList.add(z3);zList.add(z4);

        ArrayList<Complex> finalSequence = IFSresult2(z1);

        drawComplex(finalSequence);

    }

    @Override
    public Parameter[] parameters() {
        return new Parameter[]{n, ax, ay};
    }

    @Override
    public void changeParameter(Parameter parameter) {
        if (parameter.id == 1){
            n = parameter;
        } else if (parameter.id == 2){
            ax = parameter;
        } else if (parameter.id == 3){
            ay = parameter;
        }
    }
}
