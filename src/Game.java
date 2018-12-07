import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
class Game extends JFrame {

    private static final int SQUARE_SIZE = 30; //size of each square
    private JButton[][] buttons;  // Grid of buttons
    private JLabel remainingFlags;  // Number of flags remaining to be used
    private JButton smileButton;
    private JLabel timeLabel;  // Label showing time elapsed
    private int numMines;  // The num of mines
    private int[][] numAdjacentMines;  // Num of adjacent mines for each square
    private boolean[][] revealed;  // Whether the button has been clicked
    private int numRevealed;  // Num of revealed mines
    private boolean[][] flagged;  // Keep track of flagged squares
    private Image smile;
    private Image flag;
    private Image mine;
    private Image dead;
    private boolean isSmiling;

    Game(int size, int toughness) {
        numMines = size * (1 + toughness / 2);
        this.setSize(size * SQUARE_SIZE, size * SQUARE_SIZE + 50);
        this.setTitle("java.Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void setMines(int size) {
        Random rand = new Random();

        numAdjacentMines = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                numAdjacentMines[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while (count < numMines) {
            xPoint = rand.nextInt(size);
            yPoint = rand.nextInt(size);
            if (numAdjacentMines[xPoint][yPoint] != -1) {
                numAdjacentMines[xPoint][yPoint] = -1;  // -1 represents bomb
                count++;
            }
        }

        // Fill boxes with the correct adjacent mine value
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (numAdjacentMines[i][j] == -1) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            try {
                                if (numAdjacentMines[i + k][j + l] != -1) {
                                    numAdjacentMines[i + k][j + l] += 1;
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
        }
    }

    void main(Game frame, int size) {

        GameActionListener gameEngine = new GameActionListener(frame);
        GameMouseListener myMouseListener = new GameMouseListener(frame);
        JPanel mainPanel = new JPanel();

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        this.numRevealed = 0;

        revealed = new boolean[size][size];
        flagged = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }

        // Set images
        try {
            Image smiley = ImageIO.read(getClass().getResource("images/smile.png"));
            smile = smiley.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, java.awt.Image.SCALE_SMOOTH);

            Image dead = ImageIO.read(getClass().getResource("images/dead.png"));
            this.dead = dead.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, java.awt.Image.SCALE_SMOOTH);

            Image flag = ImageIO.read(getClass().getResource("images/flag.png"));
            this.flag = flag.getScaledInstance(SQUARE_SIZE, SQUARE_SIZE, java.awt.Image.SCALE_SMOOTH);

            Image mine = ImageIO.read(getClass().getResource("images/mine.png"));
            this.mine = mine.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        } catch (Exception ignored) {
        }

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        BoxLayout g1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(g1);

        JLabel jLabel1 = new JLabel(" Flags = ");
        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel1.setHorizontalAlignment(JLabel.LEFT);
        remainingFlags = new JLabel("" + this.numMines);

        isSmiling = true;
        smileButton = new JButton(new ImageIcon(smile));
        smileButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
        smileButton.setMaximumSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
        smileButton.setBorderPainted(true);
        smileButton.setName("smileButton");
        smileButton.addActionListener(gameEngine);

        JLabel jLabel2 = new JLabel(" Time :");
        timeLabel = new JLabel("0");
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        panel1.add(jLabel1);
        panel1.add(remainingFlags);
        panel1.add(Box.createRigidArea(new Dimension((size - 1) * 15 - 80, 50)));
        panel1.add(smileButton, BorderLayout.PAGE_START);
        panel1.add(Box.createRigidArea(new Dimension((size - 1) * 15 - 85, 50)));
        panel1.add(jLabel2);
        panel1.add(timeLabel);

        GridLayout g2 = new GridLayout(size, size);
        panel2.setLayout(g2);

        buttons = new JButton[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].addActionListener(gameEngine);
                buttons[i][j].addMouseListener(myMouseListener);
                buttons[i][j].setOpaque(true); //make background color visible
                panel2.add(buttons[i][j]);
            }
        }

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        frame.setContentPane(mainPanel);
        this.setVisible(true);

        // Assign mines
        setMines(size);

        // Set timer
        TimerThread timer = new TimerThread(this);
        timer.start();

    }

    void buttonRightClicked(int x, int y) {
        if (!revealed[x][y]) {
            if (flagged[x][y]) {
                buttons[x][y].setIcon(null);
                flagged[x][y] = false;
                int old = Integer.parseInt(this.remainingFlags.getText());
                ++old;
                this.remainingFlags.setText("" + old);
            } else {
                if (Integer.parseInt(this.remainingFlags.getText()) > 0) {
                    buttons[x][y].setIcon(new ImageIcon(flag));
                    flagged[x][y] = true;
                    int old = Integer.parseInt(this.remainingFlags.getText());
                    --old;
                    this.remainingFlags.setText("" + old);
                }
            }
        }
    }

    private boolean gameWon() {
        return (this.numRevealed) == (Math.pow(this.numAdjacentMines.length, 2) - this.numMines);
    }

    void buttonClicked(int x, int y) {
        if (!revealed[x][y] && !flagged[x][y]) {
            revealed[x][y] = true;

            switch (numAdjacentMines[x][y]) {
                case -1:
                    try {
                        buttons[x][y].setIcon(new ImageIcon(mine));
                    } catch (Exception ignored) {
                    }
                    buttons[x][y].setBackground(Color.RED);
                    try {
                        smileButton.setIcon(new ImageIcon(dead));
                    } catch (Exception ignored) {
                    }

                    JOptionPane.showMessageDialog(this, "java.Game Over !", null,
                            JOptionPane.ERROR_MESSAGE);

                    this.setVisible(false);
                    Minesweeper minesweeper = new Minesweeper();
                    minesweeper.start(minesweeper);

                    break;

                case 0:
                    buttons[x][y].setBackground(Color.lightGray);
                    ++this.numRevealed;

                    if (gameWon()) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Congratulations! You've Won");

                        this.setVisible(false);
                        minesweeper = new Minesweeper();
                        minesweeper.start(minesweeper);
                    }

                    // Recursively reveal blocks
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            try {
                                buttonClicked(x + i, y + j);
                            } catch (Exception ignored) {
                            }
                        }
                    }

                    break;

                default:
                    buttons[x][y].setText(Integer.toString(numAdjacentMines[x][y]));

                    // Display colored numbers
                    switch (numAdjacentMines[x][y]) {
                        case 1:
                            buttons[x][y].setForeground(Color.BLUE);
                            break;
                        case 2:
                            buttons[x][y].setForeground(Color.GREEN);
                            break;
                        case 3:
                            buttons[x][y].setForeground(Color.RED);
                            break;
                        case 4:
                            buttons[x][y].setForeground(Color.MAGENTA);
                            break;
                        case 5:
                            buttons[x][y].setForeground(Color.BLACK);
                            break;
                        case 6:
                            buttons[x][y].setForeground(Color.cyan);
                            break;
                        case 7:
                            buttons[x][y].setForeground(Color.DARK_GRAY);
                            break;
                        case 8:
                            buttons[x][y].setForeground(Color.YELLOW);
                            break;
                    }
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);
                    ++this.numRevealed;
                    if (gameWon()) {
                        JOptionPane.showMessageDialog(rootPane, "You Won !");
                        this.setVisible(false);
                        minesweeper = new Minesweeper();
                        minesweeper.start(minesweeper);
                    }

                    break;
            }
        }

    }

    // Increment timer label
    void timer() {
        String[] time = this.timeLabel.getText().split(" ");
        int time0 = Integer.parseInt(time[0]);
        ++time0;
        this.timeLabel.setText(time0 + " s");
    }

    // Change from smile to dead and vice versa
    void changeSmile() {
        if (isSmiling) {
            isSmiling = false;
            smileButton.setIcon(new ImageIcon(dead));
        } else {
            isSmiling = true;
            smileButton.setIcon(new ImageIcon(smile));
        }
    }

}


