/**
 * Ball ermöglicht die Darstellung von runden Objekten auf der Grundfläche
 */
public class Ball extends Mauerstein {
    
    protected double radius;   
    
    public Ball() {
        this.radius =  0.01  + Math.random() * 0.01;
    } 
	
    /** 
     * Konstruktor 
     *
     * @param px X-Position des Balls
     * @param py Y-Position des Balls
     * @param radius Radius des Balls
     * @param col Farbe des Balls
     */
    public Ball(double px, double py, double radius, Colour col) {
		super(px, py, col);
		this.radius = radius/2.0;
	}
    
    /** 
     * Spezifisches Zeichnen des Balls.
     *
     */
    public void draw() {
        StdDraw.setPenColor(col);
        StdDraw.filledCircle(px, py, radius);
    }
}
