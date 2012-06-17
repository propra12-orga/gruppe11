/**
 * Colour ist eigentlich �berfl�ssig und ein �berbleibsel aus 
 * FlyingObject. Im Grunde kann Colour gegen java.awt.Color in den restlichen 
 * Klassen ersetzte werden.
 */
public class Colour extends java.awt.Color {
	/**
	 * Konstruktor
 	 *
 	 * @param r Rot-Anteil
 	 * @param g Gr�n-Anteil
	 * @param b Blau-Anteil
	 */
    Colour(int r, int g, int b) {
        super(r,g,b);    
    }
    
	/**
	 * Konstruktor
 	 *
 	 * @param rgb Farbcodierung
	 */
    Colour(int rgb) {
        super(rgb);    
    }
}
