import java.awt.*;
import java.awt.Component.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.io.*;

import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

public class BomberManClient {
   // Port der Serveranwendung
   public static final int SERVER_PORT = 10002;

   // Rechnername des Servers
   public static String SERVER_HOSTNAME = "localhost";

	
	private static Colour red(){
		int red = 255;
		int green = 0;
		int blue = 0;
		
		return new Colour(red, green, blue);
	}


	static boolean sflag=true;

	public static void setstop(int istop){
		sflag = !(istop == 1);
	}

  static boolean clientready=true;
  public static void sendtoclient(){
	  clientready=true;
  }

  public static void dontsendtoclient(){
	  clientready=false;
  }


  public static boolean isclientready(){
	  return clientready;
  }

 public static void main(String[] args) {
	Ball ball1 = new Ball(0.5,0.5, 0.5, red());

      if (args.length != 1)
      {
         System.out.println (
            "Aufruf: java SpieleClient <Server-Name>");

         System.exit (1);
      }
	 SERVER_HOSTNAME=args[0];

	try {
         // Erzeugen des Socket und Aufbau der Verbindung
         final Socket socket = new Socket (SERVER_HOSTNAME, SERVER_PORT);
	 InputStream instream = socket.getInputStream();
	 byte[] inbyte = new byte [50000];
	 int bytecount;

	final MovementListener mListener = new MovementListener() {
        	@Override
	        public void doMovement(int left, int right, int up, int down, int left1, int right1, int up1, int down1, int bomb1, int istop) {
			final String sleft, sright, sup, sdown, smessage, sbomb;
			final boolean sendmessage =  (((left+right+up+down+bomb1+left1)!=0) || isclientready());
			sleft = Integer.toString(left);
			sright = Integer.toString(right);
			sup = Integer.toString(up);
			sdown = Integer.toString(down);
			sbomb = Integer.toString(bomb1);
			final int isflag = istop;

			Element root = new Element("bombermanclient");
			Document doc = new Document(root);
			root.addContent(new Element("up").setText(sup));
			root.addContent(new Element("down").setText(sdown));
			root.addContent(new Element("left").setText(sleft));
			root.addContent(new Element("right").setText(sright));
			root.addContent(new Element("bomb").setText(sbomb));

			if (isclientready() || (left1!=0)) {
				root.addContent(new Element("ready").setText("1"));
				dontsendtoclient();}
			else {
				root.addContent(new Element("ready").setText("0"));}

			XMLOutputter serializer = new XMLOutputter();
			smessage=serializer.outputString(doc);
                	SwingUtilities.invokeLater(new Runnable() {
                    		public void run() {
         			// Senden der Nachricht ueber einen Stream
				try {
				  if (sendmessage) { 
         			    socket.getOutputStream().write (smessage.getBytes());
         			    socket.getOutputStream().flush ();
//				System.out.println(smessage);
				  }
				  setstop(isflag);
				}
			        catch (IOException e)
      				{
			         // Wenn die Kommunikation fehlschlaegt
			         System.out.println ("Fehler waehrend der Kommunikation:\n" +
                                 e.getMessage());
			        }
                    		}
                	});
        	}
        };

	JFrame clientframe = StdDraw.getframe();
	clientframe.addKeyListener(mListener);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              mListener.setPriority(Thread.NORM_PRIORITY);
              mListener.start();
            }
        });
	       

         System.out.println ("Verbunden mit Server: " +
            socket.getRemoteSocketAddress());

	          System.out.println ("Client \"" + args [0] +
                             "\" meldet sich am Server an.");

	StdDraw.setXscale (0.0, 1.0);
	StdDraw.setYscale (0.0, 1.0);
   	StdDraw.setPenRadius(.005);
	
	int counter, skip;
	int anzpic=0;
	boolean ffFound;
	byte ffvar = (byte)0x00ff;
	byte eoi = (byte)0x00d9;
//	ByteArrayOutputStream baos = new ByteArrayOutputStream(50000);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while(sflag) {

		StdDraw.clear();
                while (instream.available() == 0){
			if(!sflag) {System.exit(0);}
		}

		while ((bytecount=instream.read (inbyte)) != -1){
//		System.out.println(" Bytecount "+bytecount);
		
		counter=0;
		skip=0;
		ffFound=false;
		while (counter <= bytecount) {
			if (ffFound) {
				if (inbyte[counter] == (byte)0x00d8){
//					System.out.println(" Bild startet bei: "+(counter-1));
				}

				if (inbyte[counter] == (byte)0x00d9){
					// End of Image found (FFD9)
					baos.write(inbyte,0,counter+1);
					counter++;
					skip=counter;
		//			 System.out.println("1skip = "+skip+" count = "+counter+" Bytecount = "+bytecount);
					anzpic++;
		//			 System.out.println(" Draw Pic: "+anzpic);
					// Get the whole image from the output stream baos and draw it.
					StdDraw.picture(0.5,0.5,baos.toByteArray());
					baos.reset();
					clientready=true;
					// Vorspulen bis zum nächsten FF
					//
					while ((counter) <= bytecount) {
						if(inbyte[counter+1] != (byte)0x00FF) {
							counter++;
							skip=counter;
						}
						else
						{
							break;}
					}
				}
				ffFound=false;
			}

			if (inbyte[counter] == (byte)0x00ff){
				ffFound=true;
			}
			counter++;
		}

		// System.out.println("skip = "+skip+" count = "+counter);
//		System.out.println(ffvar+" "+eoi);
//		System.out.println("2skip = "+skip+" count = "+counter+" Bytecount = "+bytecount);


		if (skip < bytecount) {
	        	baos.write(inbyte,skip,bytecount-skip);
		}

//		System.out.println(" size"+baos.size());

		if(!sflag) {break;}
		}

//		ball1.draw();
		//System.out.println(" Draw Ball");

		StdDraw.show(20);
	}
         // Beenden der Kommunikationsverbindung
         socket.close();
	}
      catch (UnknownHostException e)
      {
         // Wenn Rechnername nicht bekannt ist ...
         System.out.println ("Rechnername unbekannt:\n" +
                             e.getMessage());
      }
      catch (IOException e)
      {
         // Wenn die Kommunikation fehlschlaegt
         System.out.println ("Fehler waehrend der Kommunikation:\n" +
                             e.getMessage());
      }

      System.exit(0);
 }
}
