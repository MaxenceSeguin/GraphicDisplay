package GraphicDisplay.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayFrame extends JFrame {

    public DisplayFrame(DisplayComponent component){
        super();

        ButtonPanel panel = new ButtonPanel(component);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        displayComponents(panel);

        JComboBox list = new JComboBox<>(new String[]{"Koch Flake", "Quadratic Koch", "Satellite", "Dragon", "Mandelbrot", "Julia"});
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


        panel.add(list, 0);


        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);
    }

    private void displayComponents(ButtonPanel panel){
        this.getContentPane().add(panel, BorderLayout.SOUTH);
        this.getContentPane().add(panel.dcomponent, BorderLayout.CENTER);
    }
}
