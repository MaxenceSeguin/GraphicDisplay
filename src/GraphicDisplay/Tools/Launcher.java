package GraphicDisplay.Tools;

import GraphicDisplay.Shapes.Julia;
import GraphicDisplay.Shapes.KochFlake;
import GraphicDisplay.Shapes.Mandelbrot;
import GraphicDisplay.Shapes.Shape;

public class Launcher {

    public static void main(String args[]) {

        Shape shape = new Julia();
        DisplayComponent component = new DisplayComponent(shape);
        DisplayFrame frame = new DisplayFrame(component);


    }
}
