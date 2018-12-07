/**
 * @author John Berkley, Bryan Lee, Joshua Chen, Saul Galaviz
 * CPP Class: CS 3560
 * Date Created: Nov 05, 2018
 */
class TimerThread implements Runnable {
    private Thread t;
    private Game newGame;

    TimerThread(Game newGame) {
        this.newGame = newGame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                newGame.timer();
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}