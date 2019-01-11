package GraphicDisplay.Shapes;

import GraphicDisplay.Tools.Parameter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Satellite extends Shape {

    public Parameter[] parameters() {
        return new Parameter[0];
    }

    public void changeParameter(Parameter parameter) {
        currentAbscissa = 0;
    }

    protected void drawShape(){
        satellite(g2);
    }

    private void satellite(Graphics2D g2){
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
            java.awt.Shape s = new Ellipse2D.Double(950+10*cos(2*i*PI/15) - 200, 225+10*sin(2*i*PI/15) - 200, 2.0 * 200, 2.0 * 200);
            java.awt.Shape s2 = new Ellipse2D.Double(950+10*cos(2*i*PI/15) - 150, 225+10*sin(2*i*PI/15) - 150, 2.0 * 150, 2.0 * 150);
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
}