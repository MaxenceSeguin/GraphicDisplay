/**
 * This class represents the frame in which the shapes and the option menu will be displayed.
 */

package GraphicDisplay.Tools;

import GraphicDisplay.Shapes.CesaroCruve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayFrame extends JFrame {

    public DisplayFrame(DisplayComponent component){
        super();

/**
 * First we build the button panel and add it to the frame.
 * It also adds the DisplayComponent linked tot his panel to the frame.
 */
        ButtonPanel panel = new ButtonPanel(component);

        displayComponents(panel);

/**
 * Then we build and add to the frame the combo box that will allow us to chose which shape we want to work with.
 * This combo box will always appear first in the option list.
 */
        JComboBox list = new JComboBox<>(new String[]{"Koch Flake", "Quadratic Koch", "Satellite", "Dragon",
                "Mandelbrot", "Julia", "Cesàro curve", "Affine de Rham curve"});
        list.setFocusable(false);

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int item = list.getSelectedIndex();
                panel.reset(item);
                displayComponents(panel);

            }
        };
        list.addActionListener(aListener);

        JButton saveButton = new JButton("Save Image");
        MouseListener mListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String name = JOptionPane.showInputDialog(panel.getParent(),
                        "Name of the file", null);
                if (name != null) {
                    panel.dcomponent.shape.save(name);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        saveButton.addMouseListener(mListener);

        panel.add(list, 0);
        panel.add(saveButton, 1);

/**
 * Finally we set the frame up, with all the options wanted, and launch it.
 */
        setFrameOptionAndDisplay();
    }

    private void displayComponents(ButtonPanel panel){

        this.getContentPane().add(panel, BorderLayout.SOUTH);
        this.getContentPane().add(panel.dcomponent, BorderLayout.CENTER, 0);
    }

    private void setFrameOptionAndDisplay(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
    }
}
