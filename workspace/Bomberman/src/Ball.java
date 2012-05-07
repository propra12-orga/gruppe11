import java.applet.*;
import java.awt.*;
import java.util.*;
import java.net.*;

public class Ball
{
	

	// Deklaration der Variablen
	private int pos_x;				// Variable für die X - Position des Balles
	private int pos_y; 				// Variable für die Y - Position des Balles
	private int x_speed;			// Geschwindigkeit in x - Richtung
	private int y_speed;			// Geschwindigkeit in y - Richtung
	private int radius;				// Radius des Balles

	private int first_x;			// Start x - Position
	private int first_y;			// Start y - Position

	private int maxspeed;			// Gibt den Maximalen Speed des Balles an

	// Deklaration der Konstanten (Grenzen des Applets bei einer Gesamtgröße von 380 x 380)
	private final int x_leftout = 10;
	private final int x_rightout = 370;
	private final int y_upout = 45;
	private final int y_downout = 370;

	// Farbe des Balles
	Color color;


	// Refferenz auf das Playerobjekt des Spiels
	Spieler spieler;

	// Erzeugen des zum Erzeugen von Zufallszahlen nötigen Objektes
	Random rnd = new Random ();

	// Construktor
	public Ball (int radius, int x, int y, int vx, int vy, int ms, Color color, AudioClip out, Spieler spieler)
	{
		// Initialisierung der Variablen
		this.radius = radius;

		pos_x = x;
		pos_y = y;

		first_x = x;
		first_y = y;

		x_speed = vx;
		y_speed = vy;

		maxspeed = ms;

		this.color = color;


		this.spieler = spieler;

	}

	// Move - Methode, berechnet die Bewegung des Balls
	public void move ()
	{
		pos_x += x_speed;
		pos_y += y_speed;
	}


	// Diese Methode zeichnet den Ball in das Spielfeld
	public void DrawBall (Graphics g)
	{
		g.setColor (color);
		g.fillOval (pos_x - radius, pos_y - radius, 2 * radius, 2 * radius);
	}

}
