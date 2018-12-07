import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
class InputActionListener implements ActionListener {
    private Input parent;

    InputActionListener(Input parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object eventSource = evt.getSource();
        JTextField text = (JTextField) eventSource;
        String input;
        int size;

        try {
            input = text.getText();
            size = Integer.parseInt(input);
            if (size <= 6) {
                JOptionPane.showMessageDialog(parent,
                        "Enter an integer greater than 6", "Invalid java.Input!",
                        JOptionPane.ERROR_MESSAGE);
                text.setText("");
            } else {
                parent.setVisible(false);
                parent.set(size);
            }
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(parent,
                    "Enter an integer!", "Invalid java.Input",
                    JOptionPane.ERROR_MESSAGE);
            text.setText("");
        }

    }
}
