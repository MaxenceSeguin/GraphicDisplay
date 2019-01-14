package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Parameter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    protected void saveImageToBufferedImage(Graphics2D g2d){
        Graphics2D currentG2D = this.g2;

        render(g2d);

        this.g2 = currentG2D;
    }

    public void save(String name){
        BufferedImage bImg = new BufferedImage(4800, 2700, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bImg.createGraphics();

        Map antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(antialiasing);
        Map color = new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.addRenderingHints(color);
        Map interpolation = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.addRenderingHints(interpolation);
        Map render = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.addRenderingHints(render);

        saveImageToBufferedImage(g2d);

        try {
            ImageIO.write(bImg, "PNG", new File("./ApplicationImages/"+name+".png"));
            System.out.println("-- saved png");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

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
