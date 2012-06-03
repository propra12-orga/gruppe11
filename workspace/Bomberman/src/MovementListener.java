import java.awt.event.*;
import java.text.NumberFormat;

/* 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
*/

public abstract class MovementListener extends Thread implements java.awt.event.KeyListener {
    public long timeSlice = 50; // Millisekunden, wie oft soll die Bewegung abgefragt werden
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private boolean stopFlag = false;

    public void keyTyped(java.awt.event.KeyEvent e) {
        // do nothing
    }

    public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = -1;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = 1;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = -1;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = 1;
    }

    public void keyReleased(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = 0;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = 0;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = 0;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = 0;
    }

    /*
     * Ruft alle timeSlice Millisekunden die abstrakte Methode doMovement auf
     */
    @Override
    public void run() {
        stopFlag = false;
        long lastRunTime = System.currentTimeMillis();
        while(!stopFlag) {
            long timeDif = System.currentTimeMillis() - lastRunTime;
            try {
                sleep(timeSlice - timeDif);
            } catch (InterruptedException ex) { }
            this.doMovement(left,right,up,down);
            lastRunTime = System.currentTimeMillis();
        }
    }

    /*
     * Halte den Thread bei nächster Gelgenheit an
     */
    public void end() {
        stopFlag = true;
    }

    public abstract void doMovement(int left, int right, int up, int down);
}

