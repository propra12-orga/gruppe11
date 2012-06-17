import java.awt.*;
import java.awt.Component.*;
import java.awt.event.*;
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
 	BombermanMenu bmenu = new BombermanMenu("Bomberman Menue");
	bmenu.setSize(300,300);
	bmenu.pack();
	bmenu.setVisible(true);

        Object[] options = {"Ja, weiter spielen", "Nein, Spiel beenden" };
	int restartplay=0;
	double dx1, dy1;
        final double dx = 0.1;
	final double dy = 0.1;
	double x1orig, y1orig, x2orig, y2orig, xtmp, ytmp;

	final Spieler spieler1 = new Spieler(dx, dy, dx/2, yellow(), Grundflaeche.spielersym1);
	spieler1.active=false;
	final Spieler spieler2 = new Spieler(dx, dy, dx/2, pink(), Grundflaeche.spielersym2);
	spieler2.active=false;

	final Bomb bombe1= new Bomb(dx, dy, dx/2, dx/3, yellow(), orange(), spieler1, Grundflaeche.bombesym1);
	final Bomb bombe2= new Bomb(dx, dy, dx/2, dx/3, pink(), red(), spieler2, Grundflaeche.bombesym2);

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

	JFrame test = StdDraw.getframe();
	test.addKeyListener(mListener);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              mListener.setPriority(Thread.NORM_PRIORITY);
              mListener.start();
            }
        });
	while (true){
            
        Grundflaeche spielfeld = new Grundflaeche(spieler1, spieler2, dx, dy);
	spielfeld.draw(false);

	x1orig=spieler1.getxPosition();
	y1orig=spieler1.getyPosition();
	spieler1.lives=3;
	spieler1.score=0;
	spieler1.setPosition(x1orig, y1orig);
	spieler1.setoldPosition(x1orig, y1orig);

	x2orig=spieler2.getxPosition();
	y2orig=spieler2.getyPosition();
	spieler2.lives=3;
	spieler2.score=0;
	spieler2.setPosition(x2orig, y2orig);
	spieler2.setoldPosition(x2orig, y2orig);
	       
	StdDraw.setXscale (0.0, 1.0);
	StdDraw.setYscale (0.0, 1.0);
   	StdDraw.setPenRadius(.005);

	bmenu.textarea.setText("Loop starts");
        while(true) {
		StdDraw.clear();
		spielfeld.redraw();
		bmenu.playerstatus(spielfeld, spieler2, bmenu.p2text.getText());
		bmenu.p2lives.setText(Integer.toString(spieler2.lives));
		bmenu.p2score.setText(Integer.toString(spieler2.score));
		if (spieler2.active) {spieler2.redraw(spielfeld);}
		

		bmenu.playerstatus(spielfeld, spieler1, bmenu.p1text.getText());
		bmenu.p1lives.setText(Integer.toString(spieler1.lives));
		bmenu.p1score.setText(Integer.toString(spieler1.score));
		if (spieler1.active) {spieler1.redraw(spielfeld);}

		bombe1.redraw(spielfeld, spieler1, spieler2, bombe2);
		bombe2.redraw(spielfeld, spieler1, spieler2, bombe1);

		StdDraw.show(20);
		bmenu.p1score.setText(Integer.toString(spieler1.score));
		bmenu.p2score.setText(Integer.toString(spieler2.score));
		if (bmenu.neustart) {break;};
		if (!spieler1.alive() && !spieler2.alive()) {bmenu.textarea.setText("Restart");break;}
	}
	if (!bmenu.neustart){
	       restartplay = JOptionPane.showOptionDialog(bmenu, "Neustart ?", "Spiel", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
		    null, options, options[1]);
	}
        bmenu.neustart=false;
 
       if (restartplay == 1) { break;}
    }
    System.exit(0);
 }
}
