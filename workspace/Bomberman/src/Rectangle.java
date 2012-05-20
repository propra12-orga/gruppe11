public class Rectangle extends Mauerstein {
    
    private double width, height;   
    
    public Rectangle() {
        this.width =  0.01  + Math.random() * 0.05;
        this.height =  0.01  + Math.random() * 0.05;
    } 
	
	public Rectangle(double px, double py, double dx, double dy, Colour col) {
		super(px, py, col);
		this.width = dx;
		this.height = dy;
	}
    
    public void draw() {
        StdDraw.setPenColor(col);
        StdDraw.filledRectangle(px, py, width/2.0, height/2.0);
    }
}
