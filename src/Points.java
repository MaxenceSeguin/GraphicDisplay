import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Points extends JPanel {
    private int centerx = 725, centery = 375;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.red);

        coloredCircle(255,255,0, g2d);

    }
    public double cos(double f){
        return Math.cos(f);
    }

    public double sin(double f){
        return Math.sin(f);
    }

    /**
     * Creates a N-agone centered in the screen
     * @param n number of edge
     * @param g
     */
    public void nAgone(int n, Graphics2D g){
        int ray = 300;
        double angleUnit = 2*Math.PI/n;
        for (int i = 0; i < n; i++){
            g.drawLine((int) (centerx + ray * cos(angleUnit*i)), (int) (centery + ray * sin(angleUnit*i)),
                    (int) (centerx + ray * cos(angleUnit*(i+1))), (int) (centery + ray * sin(angleUnit*(i+1))));
        }
    }

    public void coloredCircle(int R, int G, int B, Graphics2D g){
        int ray;
        int rays= 300;
        int n = 20;
        double angle = Math.PI/n;
        int count;
        for (int i = 0; i < 2*n; i++){
            count = 2*n-i;
            ray = rays*(count/2/n);
            g.setColor(new Color(R*(count/2/n), G*(count/2/n), B*(count/2/n)));
            g.drawLine(centerx, centery, (int) (centerx+ray*cos(i*angle)),
                    (int) (centery+ray*sin(i*angle)));
        }
    }

    public static void main(String[] args) {
        Points points = new Points();
        JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(1450, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}