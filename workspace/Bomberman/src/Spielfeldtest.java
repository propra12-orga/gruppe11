import java.awt.*;
import java.awt.Component.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.io.*;

// import org.xml.sax.InputSource;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;

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

	private static void randomActivateActor(Spieler spieler1, Spieler spieler2, Actor actor1){
		int randomactivate=(int)(Math.random()*100)+1;
		if ((randomactivate == 77) && !actor1.active && (spieler1.active || spieler2.active)) {
			actor1.setActorType(Actor.addleben);
			actor1.randomactive();
		} else if  ((randomactivate == 87) && !actor1.active && (spieler1.active || spieler2.active)) {
			actor1.setActorType(Actor.addscore);
			actor1.randomactive();
		}
	}

	private static int mousei, mousej;
	private static boolean mousep=false;
	private static OutputStream outstream;
	private static boolean clientconnected = false;
	private static boolean clientready = true;

	private static void setOutputStream (OutputStream s){
		outstream=s;
		clientconnected=true;
	}

	private static void disconnectclient(){
		clientconnected=false;
		clientready=false;
	}

	private static void waitforclient(){
		clientready=false;
	}

	private static void sendtoclient(){
		clientready=true;
	}

 public static void main(String[] args) {

 
 	BombermanMenu bmenu = new BombermanMenu("Bomberman Menue");
	bmenu.setSize(300,300);
	bmenu.pack();
	bmenu.setVisible(true);
	Levels playlevel = Levels.getplaylevel();

	Actor actor1 = new Actor();
	String feldstueck;

	Object[] steine= {"0", "1", "2", "100", "102"};
        Object[] options = {"Ja, weiter spielen", "Nein, Spiel beenden" };
	int restartplay=0;
	double dx1, dy1;
        final double dx = playlevel.getDx();
	final double dy = playlevel.getDy();
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

	( new Thread() {
		public void run() {
  			// Port der Serveranwendung
		  	final int SERVER_PORT = 10002;
	 		int iup, idown, ileft, iright, ibomb, iready;

			try
			{
	 		   // Erzeugen der Socket/Binden an Port/Wartestellung
         		   // Der Server akzeptiert nun 10 gleichzeitige 
         		   // Verbindungsanfragen
			   ServerSocket socket = new ServerSocket (SERVER_PORT, 10);

	 		   //  XML Parser Stuff
	 		   SAXBuilder sb= new SAXBuilder();
	 		   Document doc = null;
         		   Element root = null;
	 		   Element up, down, left, right, bomb;
			while (true)
         {
            // Ab hier ist der Server "scharf" geschaltet
            // und wartet auf Verbindungen von Clients
            System.out.println ("Warten auf Verbindungen ...");

            // im Aufruf der Methode accept() verharrt die
            // Server-Anwendung solange, bis eine Verbindungs-
            // anforderung eines Client eingegangen ist.
            // Ist dies der Fall, so wird die Anforderung akzeptiert
            Socket client = socket.accept();

            // Ausgabe der Informationen ueber den Client
            System.out.println (
               "\nVerbunden mit Rechner: " +
               client.getInetAddress().getHostName()+ " Port: " +
               client.getPort());

            // Erzeugen eines Puffers
            byte[] b = new byte [10000];
	    int anzahl=0;
	    int skipb, countb;
            // Datenstrom zum Lesen verwenden
            InputStream stream = client.getInputStream();
	    setOutputStream (client.getOutputStream());
	 	
            // Sind Daten verfuegbar?
            while (stream.available() == 0)
               ;

            // Ankommende Daten lesen und ausgeben
            while ((anzahl=stream.read (b)) != -1)
            {
	    skipb=0;
	    countb=0;
		    /*
               System.out.println (
                  "Nachricht empfangen: Anzahl bytes="+anzahl+"-" + new String (b));
		  */

	       try {
	       ByteArrayOutputStream baos = new ByteArrayOutputStream();
	       while (countb <= anzahl) {
	         baos.write(b,countb,1);

	         if ((skipb >= 158) && (b[countb] == (byte) 0x003e)) {
	       		InputStream is = new ByteArrayInputStream(baos.toByteArray());
			skipb=0;
			baos.reset();
		       
	       		doc=sb.build(is);
	       		root=doc.getRootElement();

	       		iup=Integer.parseInt(root.getChild("up").getText());
	     		idown=Integer.parseInt(root.getChild("down").getText());
	       		ileft=Integer.parseInt(root.getChild("left").getText());
	       		iright=Integer.parseInt(root.getChild("right").getText());
	       		ibomb=Integer.parseInt(root.getChild("bomb").getText());
	       		iready=Integer.parseInt(root.getChild("ready").getText());

			final double x2 = spieler2.getxPosition();
       	       		final double y2 = spieler2.getyPosition();
       	       		final double newX2 = x2 + (ileft + iright)*0.015;
               		final double newY2 = y2 - (iup + idown)*0.015;
	       		if (ibomb == 1) {bombe2.activate(); bombe2.movetoPosition(newX2, newY2);}
	       		if (iready == 1) {
		       		sendtoclient();}
			if (spieler2.active) {spieler2.movetoPosition(newX2, newY2);}

			// Vorspulen bis zum nächsten "<" 
			//
			while ((countb+1) <= anzahl) {
				if (b[countb+1] != (byte) 0x003c) {
				       countb++;
				}
				else 
				{
					break;}
			}		
               	 }
		 countb++;
		 skipb++;
	      
	       }
	       }
	        catch (JDOMException j){
      		   System.out.println ("JDOM Problem:\n" +
                             j.getMessage());
      		}
	

	    }
            // Verbindung zum Client beenden
            client.close();
	    disconnectclient();
	    waitforclient();
            System.out.println ("Der Client wurde bedient ...");
         }
      }
      catch (UnknownHostException e)
      {
         // Wenn Rechnername nicht bekannt ist ...
         System.out.println ("Rechnername unbekannt:\n" +
                             e.getMessage());
      }
      catch (IOException e)
      {
         // Wenn Kommunikation fehlschlaegt ...
         System.out.println ("Fehler waehrend der Kommunikation:\n" +
                             e.getMessage());
      }
		}
	     }).start();



	while (true){
            
        Grundflaeche spielfeld = new Grundflaeche(spieler1, spieler2);
	spielfeld.setTextActive(Levels.getEditMode());
	spielfeld.create(false);

	x1orig=spieler1.getxPosition();
	y1orig=spieler1.getyPosition();
	spieler1.lives=spielfeld.anzahlleben;
	spieler1.score=0;
	spieler1.setPosition(x1orig, y1orig);
	spieler1.setoldPosition(x1orig, y1orig);

	x2orig=spieler2.getxPosition();
	y2orig=spieler2.getyPosition();
	spieler2.lives=spielfeld.anzahlleben;
	spieler2.score=0;
	spieler2.setPosition(x2orig, y2orig);
	spieler2.setoldPosition(x2orig, y2orig);
	       
	StdDraw.setXscale (0.0, 1.0);
	StdDraw.setYscale (0.0, 1.0);
   	StdDraw.setPenRadius(.005);
	
	actor1.active = false;
        while(true) {
		randomActivateActor(spieler1, spieler2, actor1);

		StdDraw.clear();
		spielfeld.redraw();
		if (actor1.active) {
			actor1.move();
			actor1.redraw(spielfeld, spieler1, spieler2);
		}

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

		if (clientconnected && clientready){
			if (!StdDraw.save(outstream)){
				disconnectclient();}
			waitforclient();
			}
		StdDraw.show(20);
		if (Levels.getEditMode()){
		       while ( StdDraw.mousePressed()) {
			       mousep=true;
			       mousei=spielfeld.geti(StdDraw.mouseX());
			       mousej=spielfeld.getj(StdDraw.mouseY());
		       }
		       if (mousep) {
			  feldstueck=(String)JOptionPane.showInputDialog(null, "Feld aendern", "Feldnummer:", JOptionPane.PLAIN_MESSAGE,null,steine,"");
			  if ((feldstueck != null) && (feldstueck.length() > 0)) {
		          	spielfeld.initialposition[mousej][mousei]=Integer.parseInt(feldstueck);
				spielfeld.create(true);
			  }
			  mousep=false;
		       }
		}
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
    bmenu.saveScore();
    System.exit(0);
 }
}
