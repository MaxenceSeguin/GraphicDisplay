package GraphicDisplay.Tools;

import GraphicDisplay.Shapes.*;
import GraphicDisplay.Shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {

    /** The list of button we want to display **/
    private ArrayList<JButton> buttons = new ArrayList<>();
    /** The keylistener to move the shape around and to zoom it in **/
    private KeyListener kListener;
    /** The display component we are working with **/
    public DisplayComponent dcomponent;

    public ButtonPanel(DisplayComponent component) {
        super(new GridLayout(1,0,0,0));

        construct(component);
    }

    /**
     * Build this panel. Create the buttons, link them to their according listener etc.
     * @param component
     */
    private void construct (DisplayComponent component){
        this.dcomponent = component;
        createKeyControllers();
        createButtons();
        displayButtons();
    }

    /**
     * Adds the KeyListener kListener to all the component of the pane.
     */
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

    /**
     * Creates all the button needed - according to the shape linked to this panel.
     * Also creates the mouse wheel listener for each of those.
     * Finally adds those buttons to the Arraylist so that we can cast them later in the frame.
     */
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

    /**
     * Displays all the buttons from the button Arraylist in this panel.
     */
    private void displayButtons(){

        for (JButton button : buttons){
            button.addKeyListener(kListener);
            this.add(button);
        }

        this.revalidate();
    }

    /**
     * This method is called when the combo box is used.
     * THis will change the shape we are displaying in the frame.
     * Therefore we need to rebuild all the buttons according to this new shape.
     * @param index index of the shape we want.
     */
    public void reset(int index){

        Shape newShape = new KochFlake();

        switch (index) {
            case 1 : newShape = new QuadraticKoch(); break;
            case 2 : newShape = new Satellite(); break;
            case 3 : newShape = new DragonCurve(); break;
            case 4 : newShape = new Mandelbrot(); break;
            case 5 : newShape = new Julia(); break;
            case 6 : newShape = new CesaroCruve(); break;
            case 7 : newShape = new AffineDeRhamCurve(); break;
        }

        this.dcomponent.isVisible = false;
        this.dcomponent = new DisplayComponent(newShape);

        construct(this.dcomponent);
    }


}
