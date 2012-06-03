public class Ball extends Mauerstein {
    
    protected double radius;   
    
    public Ball() {
        this.radius =  0.01  + Math.random() * 0.01;
    } 
	
    public Ball(double px, double py, double radius, Colour col) {
		super(px, py, col);
		this.radius = radius/2.0;
	}
    
    public void draw() {
        StdDraw.setPenColor(col);
        StdDraw.filledCircle(px, py, radius);
    }
}
