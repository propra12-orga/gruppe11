import java.awt.*;
import javax.swing.*;
public class Spielfeldtest {
	private static Colour yellow(){
		int red = 255;
		int green = 255;
		int blue = 50;
		
		return new Colour(red, green, blue);
	}

	private static Colour pink(){
		int red = 255;
		int green = 50;
		int blue = 255;
		
		return new Colour(red, green, blue);
	}

	private static Colour red(){
		int red = 255;
		int green = 0;
		int blue = 0;
		
		return new Colour(red, green, blue);
	}

	private static Colour orange(){
		int red = 255;
		int green = 50;
		int blue = 10;
		
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
	/*
        final JLabel moveMeLabel  = new JLabel("move me");
 
        moveMeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        moveMeLabel.setSize(moveMeLabel.getPreferredSize());
	*/ 

	    final Spieler spieler1 = new Spieler(dx, dy, dx/2, yellow(), Grundflaeche.spielersym1);
	    spieler1.active=true;
	    final Spieler spieler2 = new Spieler(dx, dy, dx/2, pink(), Grundflaeche.spielersym2);
	    spieler2.active=true;
	    final Bomb bombe1= new Bomb(dx, dy, dx/2, dx/3, yellow(), orange(), Grundflaeche.bombesym1);
	    final Bomb bombe2= new Bomb(dx, dy, dx/2, dx/3, pink(), red(), Grundflaeche.bombesym2);
	    final MovementListener mListener = new MovementListener() {
            @Override
            public void doMovement(int left, int right, int up, int down, int left1, int right1, int up1, int down1, int bomb1, int bomb2) {
                final double x1 = spieler1.getxPosition();
                final double y1 = spieler1.getyPosition();
                final double newX1 = x1 + (left + right)*0.015;
                final double newY1 = y1 - (up + down)*0.015;
                final double x2 = spieler2.getxPosition();
                final double y2 = spieler2.getyPosition();
                final double newX2 = x2 + (left1 + right1)*0.015;
                final double newY2 = y2 - (up1 + down1)*0.015;
		if (bomb1 == 1) {bombe1.activate(); bombe1.movetoPosition(newX1, newY1);}
		if (bomb2 == 1) {bombe2.activate(); bombe2.movetoPosition(newX2, newY2);}
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (spieler1.active) {spieler1.movetoPosition(newX1, newY1);}
			if (spieler2.active) {spieler2.movetoPosition(newX2, newY2);}
                    }
                });
            }
        };

    //---------------------------------------------------------------------------------
            
        Grundflaeche spielfeld = new Grundflaeche(spieler1, spieler2, dx, dy);
	JFrame test = StdDraw.getframe();
	       
	StdDraw.setXscale (0.0, 1.0);
	StdDraw.setYscale (0.0, 1.0);
   	StdDraw.setPenRadius(.005);

//	spielfeld.draw();
	test.addKeyListener(mListener);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              mListener.setPriority(Thread.NORM_PRIORITY);
              mListener.start();
            }
        });
	 spielfeld.draw(false);
         while(true) {
		StdDraw.clear();
		/*
		spieler1.move();
		dx1 = spieler1.getxPosition();
		dy1 = spieler1.getyPosition();
		*/
		spielfeld.redraw();
		bombe1.redraw(spielfeld, spieler1, spieler2, bombe2);
		bombe2.redraw(spielfeld, spieler1, spieler2, bombe1);
		if (spieler2.active) {spieler2.redraw(spielfeld);}

		if (spieler1.active) {spieler1.redraw(spielfeld);}
//		System.out.println (" loop -- x =" + dx1 + " y= " + dy1);
		StdDraw.show(20);
        }
 }
}
