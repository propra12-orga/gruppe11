import java.awt.*;
import javax.swing.*;
public class Spielfeldtest {
	private static Colour yellow(){
		int red = 255;
		int green = 255;
		int blue = 100;
		
		return new Colour(red, green, blue);
	}
 public static void main(String[] args) {
 	JFrame f = new BombermanMenu("Bomberman Menue");
	f.setSize(300,300);

	f.pack();
	f.setVisible(true);
	double dx1, dy1;
        final double dx = 0.1;
	final double dy = 0.1;
	double x, y;
     final JLabel moveMeLabel  = new JLabel("move me");
 
        moveMeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        moveMeLabel.setSize(moveMeLabel.getPreferredSize());
 

	final Spieler spieler1 = new Spieler(dx, dy, dx/2, yellow());
	     final MovementListener mListener = new MovementListener() {
            @Override
            public void doMovement(int left, int right, int up, int down) {
                final double x = spieler1.getxPosition();
                final double y = spieler1.getyPosition();
                final double newX1 = x + left + right;
                final double newY1 = y + up + down;
		java.awt.Rectangle pos = moveMeLabel.getBounds();
                final int newX = pos.x + left + right;
                final int newY = pos.y + up + down;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
//                       spieler1.setPosition(newX1, newY1);
//			System.out.println (" x = " + newX + ", y = " + newY);
		        moveMeLabel.setBounds(newX,newY,moveMeLabel.getWidth(), moveMeLabel.getHeight());
                    }
                });
            }
        };
	// int N = Integer.parseInt(args[0]);
        Grundflaeche spielfeld = new Grundflaeche(spieler1, dx, dy);
	JFrame test = StdDraw.getframe();
	       

        test.getContentPane().add(moveMeLabel);
        moveMeLabel.setBounds(0,0,moveMeLabel.getWidth(), moveMeLabel.getHeight());

    //---------------------------------------------------------------------------------
            
	StdDraw.setXscale (0.0, 1.0);
	StdDraw.setYscale (0.0, 1.0);
   	StdDraw.setPenRadius(.005);

	spielfeld.draw();
	test.addKeyListener(mListener);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              mListener.setPriority(Thread.NORM_PRIORITY);
              mListener.start();
            }
        });
         while(true) {
		StdDraw.clear();
		spieler1.move();
		dx1 = spieler1.getxPosition();
		dy1 = spieler1.getyPosition();
		spielfeld.draw();
		spieler1.draw();
//		System.out.println (" loop -- x =" + dx1 + " y= " + dy1);
		StdDraw.show(20);
        }
 }
}
