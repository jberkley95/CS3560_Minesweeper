import javax.swing.*;

/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
public class Minesweeper {

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.start(minesweeper);
    }

    private void start(Minesweeper minesweeper) {
        Input input = new Input(minesweeper);
        input.main(input);
    }

    void proceed(int size) {
        Object[] options = {"Easy", "Medium", "Hard"};
        int toughness = JOptionPane.showOptionDialog(null,
                "Select a difficulty!", "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (toughness == -1)
            System.exit(0);
        Game newGame = new Game(size, toughness);
        newGame.main(newGame, size);

    }
}
