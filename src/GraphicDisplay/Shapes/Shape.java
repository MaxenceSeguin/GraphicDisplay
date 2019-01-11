package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Parameter;

import java.awt.*;

public abstract class Shape {

    protected Graphics2D g2;
    protected double currentAbscissa = 0;
    protected double dx = 1, dy = 1;
    protected double[] coordinates = {100, 25};

    public Shape(){}

    public final void render(Graphics2D g2){
        this.g2 = g2;
        zoom();
        translate();
        drawShape();
    }

    abstract protected void drawShape();

    /** Called every time the shape is rendered **/
    private void zoom(){ g2.scale(dx, dy);}
    private void translate(){ g2.translate(coordinates[0], coordinates[1]);}

    /** Called every time the shape is repainted **/
    public final void zoom(double d2x, double d2y){
        currentAbscissa = 0;
        dx *= d2x;
        dy *= d2y;
    }

    public final void translate(double dx, double dy){
        currentAbscissa = 0;
        coordinates[0] += dx;
        coordinates[1] += dy;
    }

    abstract public Parameter[] parameters();

    abstract public void changeParameter(Parameter parameter);
}
