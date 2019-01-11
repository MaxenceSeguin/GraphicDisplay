package GraphicDisplay.Tools;

import GraphicDisplay.Shapes.*;
import GraphicDisplay.Shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {

    private ArrayList<JButton> buttons = new ArrayList<>();
    private KeyListener kListener;
    public DisplayComponent dcomponent;

    public ButtonPanel(DisplayComponent component) {
        super(new GridLayout(1,0,0,0));

        construct(component);
    }

    private void construct (DisplayComponent component){
        this.dcomponent = component;
        createKeyControllers();
        createButtons();
        displayButtons();
    }

    private void createKeyControllers(){
        this.removeKeyListener(kListener);
        dcomponent.removeKeyListener(kListener);

        for (JButton button : buttons){
            button.removeKeyListener(kListener);
        }

        kListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'z'){
                    dcomponent.shape.translate(0, -25);
                }
                else if (e.getKeyChar() == 's'){
                    dcomponent.shape.translate(0, 25);
                }
                else if (e.getKeyChar() == 'q'){
                    dcomponent.shape.translate(-25, 0);
                }
                else if (e.getKeyChar() == 'd'){
                    dcomponent.shape.translate(25, 0 );
                }
                else if (e.getKeyChar() == '+'){
                    dcomponent.shape.zoom(1.1, 1.1);
                }
                else if (e.getKeyChar() == '-'){
                    dcomponent.shape.zoom(0.9, 0.9);
                }
                dcomponent.repaint();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        this.addKeyListener(kListener);
        dcomponent.addKeyListener(kListener);

        for (JButton button : buttons){
            button.addKeyListener(kListener);
        }
    }

    private void createButtons() {

        Shape shape = dcomponent.shape;

        for (JButton button : buttons){
            this.remove(button);
        }

        buttons = new ArrayList<>();

        for (Parameter parameter : shape.parameters()){
            JButton button = new JButton(parameter.name);
            button.setFocusPainted(false);
            button.setFocusable(false);
            button.setBackground(new Color(0x2CB0FF));
            button.setBorder(BorderFactory.createLineBorder(Color.black,1));
            MouseWheelListener mwListener = new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    parameter.value -= parameter.delta * e.getWheelRotation();
                    shape.changeParameter(parameter);
                    dcomponent.repaint();
                }
            };
            button.addMouseWheelListener(mwListener);
            buttons.add(button);
        }
    }

    private void displayButtons(){

        for (JButton button : buttons){
            button.addKeyListener(kListener);
            this.add(button);
        }

        this.revalidate();
    }

    public void reset(int index){

        Shape newShape = new KochFlake();

        if (index == 1){
            newShape = new QuadraticKoch();
        } else if (index == 2){
            newShape = new Satellite();
        } else if (index == 3){
            newShape = new DragonCurve();
        } else if (index == 4){
            newShape = new Mandelbrot();
        } else if (index == 5){
            newShape = new Julia();
        }

        this.dcomponent = new DisplayComponent(newShape);

        construct(this.dcomponent);
    }


}
