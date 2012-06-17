import java.awt.event.*;
import java.text.NumberFormat;

/**
 * MovementListener ist eine Classe die für die Steuerung der Spieler und das Legen der
 * Bomben verwendet wird.
 * MovementListener läuft als eigener Thread.
 */
public abstract class MovementListener extends Thread implements java.awt.event.KeyListener {
    public long timeSlice = 50; // Millisekunden, wie oft soll die Bewegung abgefragt werden
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private int left1 = 0;
    private int right1 = 0;
    private int up1 = 0;
    private int down1 = 0;
    private int bomb1 = 0;
    private int bomb2 = 0;
    private boolean stopFlag = false;

    public void keyTyped(java.awt.event.KeyEvent e) {
        // do nothing
    }

    public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = -1;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = 1;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = -1;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = 1;
        if (e.getKeyCode() == KeyEvent.VK_S) left1 = -1;
        if (e.getKeyCode() == KeyEvent.VK_D) right1 = 1;
        if (e.getKeyCode() == KeyEvent.VK_E) up1 = -1;
        if (e.getKeyCode() == KeyEvent.VK_X) down1 = 1;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) bomb1 = 1;
        if (e.getKeyCode() == KeyEvent.VK_Q) bomb2 = 1;
    }

    public void keyReleased(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = 0;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = 0;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = 0;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = 0;
        if (e.getKeyCode() == KeyEvent.VK_S) left1 = 0;
        if (e.getKeyCode() == KeyEvent.VK_D) right1 = 0;
        if (e.getKeyCode() == KeyEvent.VK_E) up1 = 0;
        if (e.getKeyCode() == KeyEvent.VK_X) down1 = 0;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) bomb1 = 0;
        if (e.getKeyCode() == KeyEvent.VK_Q) bomb2 = 0;
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
            this.doMovement(left,right,up,down,left1,right1,up1,down1,bomb1,bomb2);
            lastRunTime = System.currentTimeMillis();
        }
    }

    /*
     * Halte den Thread bei nächster Gelgenheit an
     */
    public void end() {
        stopFlag = true;
    }

    /**
     * doMovement muss an der richtigen Stelle genutzt werden, ist hier nur abstract definiert.
     *
     */
    public abstract void doMovement(int left, int right, int up, int down, int left1, int right1, int up1, int down1, int bomb1, int bomb2);
}

