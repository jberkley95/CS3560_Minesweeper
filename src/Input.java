import javax.swing.*;

/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
class Input extends JFrame {

    final private Minesweeper minesweeper;
    private int size;

    Input(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        this.setSize(400, 100);
        this.setTitle("java.Input");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    void set(int n) {
        size = n;
        minesweeper.proceed(size);
    }

    void main(Input Input) {
        InputActionListener inputEngine = new InputActionListener(Input);

        size = 0;

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Enter grid size : ");
        panel.add(label);

        JTextField text = new JTextField(30);
        text.addActionListener(inputEngine);
        panel.add(text);

        Input.setContentPane(panel);
        this.setVisible(true);
    }
}
