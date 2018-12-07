import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
class GameMouseListener implements MouseListener {
    private Game parent;

    GameMouseListener(Game parent) {
        this.parent = parent;
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (SwingUtilities.isRightMouseButton(arg0)) {
            Object eventSource = arg0.getSource();
            JButton clickedButton = (JButton) eventSource;
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.buttonRightClicked(x, y);
        }
    }
}
