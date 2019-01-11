import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class LinesComponent extends JComponent{

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private double centerx = 1450/2, centery = 750/2;
    private double m = 2.1;
    private double[] angles = {PI/m, 2*PI-2*PI/m, PI/m, 0};
    private double x = 0;
    private double zoom = 1;

    private void koch(Graphics2D g2, int n){
        g2.scale((double)1/2/(pow(3,n-1)), (double)1/2/pow(3,n-1));
        g2.setStroke(new BasicStroke((int)pow(3,n-1)));
        g2.translate(500 + 100 * pow(3, n), 200 + 100 * pow(3, n));
        for (int j = 0; j < 3; j++){
            kochLine(g2, n);
            g2.rotate(2*PI/3, x, 0);
        }
    }

    private void kochLine(Graphics2D g2, int n){

        if (n != 1){
            for (int i = 0; i < 4; i++){
                kochLine(g2, n-1);
                g2.rotate(-angles[i], x, 0);
            }
        } else {
            for (int i = 0; i < 4; i++){
                Shape l = new Line2D.Double(x, 0, x + 300, 0);
                g2.draw(l);
                x += 300;
                g2.rotate(-angles[i], x, 0);
            }
        }

    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        zoom += 0.1;
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        koch((Graphics2D)g, 5);
    }



    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(1450, 800));

        testFrame.getContentPane().add(comp, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton newLineButton = new JButton("New Line");
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(newLineButton);
        buttonsPanel.add(clearButton);

        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        newLineButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x1 = (int) (Math.random()*1450);
                int x2 = (int) (Math.random()*1450);
                int y1 = (int) (Math.random()*800);
                int y2 = (int) (Math.random()*800);
                Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
                comp.addLine(x1, y1, x2, y2, randomColor);
            }
        });
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comp.clearLines();
            }
        });
        testFrame.pack();
        testFrame.setVisible(true);
    }

}