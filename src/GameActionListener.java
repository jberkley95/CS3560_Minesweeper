import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
class GameActionListener implements ActionListener {
    private Game parent;

    GameActionListener(Game parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        JButton clickedButton = (JButton) eventSource;
        String name = clickedButton.getName();
        if (name.equals("smileButton")) {
            parent.changeSmile();
        } else {
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.buttonClicked(x, y);

        }
    }
}