package GraphicDisplay.Tools;

import GraphicDisplay.Shapes.CesaroCruve;
import GraphicDisplay.Shapes.Shape;

public class Launcher {

    public static void main(String args[]) {

        Shape shape = new CesaroCruve();
        DisplayComponent component = new DisplayComponent(shape);
        DisplayFrame frame = new DisplayFrame(component);


    }
}
