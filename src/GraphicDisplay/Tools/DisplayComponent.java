package GraphicDisplay.Tools;

import GraphicDisplay.Shapes.Shape;

import javax.swing.*;
import java.awt.*;

public class DisplayComponent extends JComponent {

    public GraphicDisplay.Shapes.Shape shape;
    public boolean isVisible = true;

    public DisplayComponent(Shape shape) {
        super();
        this.setFocusable(true);
        this.shape = shape;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (isVisible)
        shape.render(g2);

    }
}
