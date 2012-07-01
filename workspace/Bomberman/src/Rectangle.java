/**
 * Rectangle ermöglicht die Darstellung von quadratischen Objekten auf der Grundfläche
 */
public class Rectangle extends Mauerstein {
    
    private double width, height;   


    public Rectangle() {
        this.width =  0.01  + Math.random() * 0.05;
        this.height =  0.01  + Math.random() * 0.05;
    } 
	
    /** 
     * Konstruktor 
     *
     * @param px X-Position des Rectangles
     * @param py Y-Position des Rectangles
     * @param dx Länge des Rectangles
     * @param dy Höhe des Rectanges
     * @param col Farbe des Rectangles
     */ 
	public Rectangle(double px, double py, double dx, double dy, Colour col) {
		super(px, py, col);
		this.width = dx;
		this.height = dy;
	}
    
     /** 
     * Spezifisches Zeichnen des Rectangles.
     *
     */
    public void draw() {
        StdDraw.setPenColor(col);
        StdDraw.filledRectangle(px, py, width/2.0, height/2.0);
    }
}
