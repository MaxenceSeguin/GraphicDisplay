import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class FrameTestBase extends JFrame {


    public static void main(String args[]) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GraphicComponent component = new GraphicComponent();

        frame.getContentPane().add(component, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JTextComponent inputM = new JTextArea(1,3);

        JButton addN = new JButton("ADD to N");
        JButton deaddN = new JButton("REMOVE to N");
        JButton addLines = new JButton("ADD edge");
        JButton deaddLines = new JButton("REMOVE edge");

        //panel.add(inputM);
        panel.add(addN);
        panel.add(deaddN);
        panel.add(addLines);
        panel.add(deaddLines);

        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        component.setFocusable(true);

        MouseWheelListener mwListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                component.changeM(e.getWheelRotation()*0.05);
                System.out.println(component.flakeAngle);
            }
        };

        KeyListener kListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'z'){
                    component.translate(0, -25);
                }
                else if (e.getKeyChar() == 's'){
                    component.translate(0, 25);
                }
                else if (e.getKeyChar() == 'q'){
                    component.translate(-25, 0);
                }
                else if (e.getKeyChar() == 'd'){
                    component.translate(25, 0 );
                }
                else if (e.getKeyChar() == '+'){
                    component.zoom(1.1);
                }
                else if (e.getKeyChar() == '-'){
                    component.zoom(0.9);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        frame.addMouseWheelListener(mwListener);
        panel.addKeyListener(kListener);
        addN.addKeyListener(kListener);
        deaddN.addKeyListener(kListener);
        component.addKeyListener(kListener);
        addLines.addKeyListener(kListener);
        deaddLines.addKeyListener(kListener);

        /*inputM.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    component.n = Integer.parseInt(inputM.getText());
                    component.repaint();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });*/


        addN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                component.increment(1);
            }
        });
        deaddN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                component.increment(-1);
            }
        });
        addLines.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                component.addLines(1);
            }
        });
        deaddLines.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                component.addLines(-1);
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
        /*while (component.flakeAngle > 1.5){
            component.changeM(-0.01);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e){
                System.out.println("prob");
            }
        }*/
    }
}