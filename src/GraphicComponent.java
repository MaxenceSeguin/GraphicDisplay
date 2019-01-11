import javax.swing.*;
import java.awt.*;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class GraphicComponent extends JComponent {

    private double centerx = 1450/2, centery = 750/2;
    public double flakeAngle = 3;
    public double quadAngle = 2;
    private double dim = 3;
    private double x = 0;
    public double zoom = 1;
    public int n = 5;
    public int amountLines = 3;
    public double[] coordinates = {100, 25};


    public GraphicComponent() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.scale(zoom, zoom);
        //qKoch(g2, n);
        //koch(g2, n);
        satllite(g2,n);

    }

    private void circleOfDoom(Graphics2D g2){
        double rays= 1000;
        int n = 4000;
        int m = 16;
        double angle = Math.PI/n;
        double R = 255, G = 0, B = 200;
        for (double i = m*Math.PI; i > 0; i-=angle){
            g2.setColor(new Color((int)(R*(i/m/PI)), (int)(G*(i/m/PI)), (int)(B*(i/m/PI))));
            java.awt.Shape l = new Line2D.Double(centerx, centery, centerx+rays*(i/m/PI)*cos(i),
                    centery+rays*(i/m/PI)*sin(i));
            g2.draw(l);
        }
    }

    private void koch(Graphics2D g2, int nn){
        this.n = nn;
        dim = 2+2*sin((flakeAngle -2)*PI/ flakeAngle /2);
        double mZoom = (double)1/1.5/(pow(dim,n-1));
        g2.scale(mZoom, mZoom);
        g2.setStroke(new BasicStroke((int)(pow(dim,n-1)/zoom)));
        g2.translate((coordinates[0] + 100) * pow(dim, n)*3/ dim, (coordinates[1] + 100) * pow(dim, n)*3/dim);
        for (int j = 0; j < amountLines; j++){
            kochLine(g2, n);
            g2.rotate(2*PI/amountLines, x, 0);
        }
    }

    private void satllite(Graphics2D g2, int nn){
        g2.translate(coordinates[0], coordinates[1]);
        Random rand = new Random();
        g2.scale(0.3,0.3);
        g2.rotate(-PI/3+PI/6);
        g2.setColor(new Color(255,255,255));
        g2.fillRect(-1000, -1000, 10000, 10000);
        int ang[] = {50, 30, -100, -35, -50};
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 5; j++){
                int nimber = 100 + 7 * (j+1) + 10 *(i+1);
                Color color = new Color(nimber,nimber,nimber);
                g2.setColor(color);
                g2.fillRect(100 + i*50, 100 + j*50, 50, 50);
                g2.fillRect(1200 + i*50, 100 + j*50, 50, 50);
            }
        }
        g2.setColor(Color.black);
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 5; j++){
                for (int k = 0; k < 5; k++) {
                    double angle = PI/(rand.nextInt(25)+ang[k]);
                    g2.rotate(angle, 100 + i*50+25, 100 + j*50+25);
                    g2.drawRect(100 + i*50, 100 + j*50, 50, 50);
                    g2.rotate(-angle, 100 + i*50+25, 100 + j*50+25);
                }
            }
        }
        for (int i = 0; i < 15; i++){
            Shape s = new Ellipse2D.Double(950+10*cos(2*i*PI/15) - 200, 225+10*sin(2*i*PI/15) - 200, 2.0 * 200, 2.0 * 200);
            Shape s2 = new Ellipse2D.Double(950+10*cos(2*i*PI/15) - 150, 225+10*sin(2*i*PI/15) - 150, 2.0 * 150, 2.0 * 150);
            if(i==7) {
                g2.setColor(new Color(100, 103, 105));
                g2.fill(s);
                g2.setColor(new Color(255, 255, 255));
                g2.fill(s2);
                g2.setColor(Color.black);
            }
            g2.draw(s);
            g2.draw(s2);
        }
        int trait = 30;
        for (int i = 0; i < 10*trait; i++){
            int chiffre = rand.nextInt(trait);
            java.awt.Shape l = new Line2D.Double(
                    950 + 30 * cos(i*2*PI/trait), 225 + 30 * sin(i*2*PI/trait),
                    950 + 30 * cos((i+chiffre)*2*PI/trait), 225 + 30 * sin((i+chiffre)*2*PI/trait));
            g2.draw(l);
        }
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 5; j++){
                for (int k = 0; k < 5; k++) {
                    double angle = PI/(rand.nextInt(25)+ang[k]);
                    g2.rotate(angle, 1200 + i*50+25, 100 + j*50+25);
                    g2.drawRect(1200 + i*50, 100 + j*50, 50, 50);
                    g2.rotate(-angle, 1200 + i*50+25, 100 + j*50+25);
                }
            }
        }
        g2.rotate(3*PI/24);
        g2.scale(1.2, 1.2);
        g2.drawArc(-50, -450, 5000, 2000, 160, 310);
    }

    private void kochLine(Graphics2D g2, int n){

        double[] angles = {PI/ flakeAngle, 2*PI-2*PI/ flakeAngle, PI/ flakeAngle, 0};
        Color[] colors = {new Color(202,0, 255),
                new Color(1,0, 255),
                new Color(0x5A37FF),
                new Color(0xFFA4F9)};

        if (n != 1){
            for (int i = 0; i < 4; i++){
                kochLine(g2, n-1);
                g2.rotate(-angles[i], x, 0);
            }
        } else {
            for (int i = 0; i < 4; i++){
                java.awt.Shape l = new Line2D.Double(x, 0, x + 300, 0);
                g2.setColor(colors[i]);
                g2.draw(l);
                x += 300;
                g2.rotate(-angles[i], x, 0);
            }
        }

    }

    private void qKoch(Graphics2D g2, int nn){
        this.n = nn;
        dim = 4;
        double mZoom = (double)1/1.5/(pow(dim,n-1));
        g2.scale(mZoom, mZoom);
        g2.setStroke(new BasicStroke((int)(pow(dim,n-1)/zoom)));
        g2.translate((coordinates[0] + 100) * pow(dim, n)*3/ dim, (coordinates[1] + 100) * pow(dim, n)*3/dim);

        for (int j = 0; j < amountLines; j++){
            quadKoch(g2, n);
            g2.rotate(2*PI/amountLines, x, 0);
        }
    }

    private void quadKoch(Graphics2D g2, int n){
        double[] angles = {-PI/quadAngle, PI/quadAngle, PI/quadAngle, 0, -PI/quadAngle, -PI/quadAngle, PI/quadAngle, 0};
        Color[] colors = {
                new Color(0xFF0000),
                new Color(0xFFED00),
                new Color(0x00FF12),
                new Color(0x00FFF6),
                new Color(0x0015FF),
                new Color(0x8300FF),
                new Color(0xFF00F6),
                new Color(0xFF0083),
        };

        if (n != 1){
            for (int i = 0; i < 8; i++){
                quadKoch(g2, n-1);
                g2.rotate(-angles[i], x, 0);
            }
        } else {
            for (int i = 0; i < 8; i++){
                Shape l = new Line2D.Double(x, 0, x + 300, 0);
                g2.setColor(colors[i]);
                g2.draw(l);
                x += 300;
                g2.rotate(-angles[i], x, 0);
            }
        }
    }

    public void zoom(double delta){
        x = 0;
        zoom *= delta;
        repaint();
    }

    public  void translate(double dx, double dy){
        x = 0;
        coordinates[0] += dx;
        coordinates[1] += dy;
        repaint();
    }
    public void increment(int dn){
        x = 0;
        n += dn;
        repaint();
    }

    public void changeM(double dm){
        x = 0;
        flakeAngle += dm;
        repaint();
    }

    public void addLines(int d){
        x = 0;
        amountLines += d;
        repaint();
    }

}
