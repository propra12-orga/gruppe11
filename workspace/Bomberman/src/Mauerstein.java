abstract class Mauerstein implements Drawable {
    
    public Colour col;
    public double px, py;  
    
    private static Colour randCol() {
        int red   = (int)(Math.random()*255.0);
        int green = (int)(Math.random()*255.0);
        int blue  = (int)(Math.random()*255.0);

        return new Colour(red,green,blue);
    }
	
	
    
    public Mauerstein() {
        px = py = 0.5;
        col     = randCol();    
    } 
	
	public Mauerstein(double px, double py, Colour col) {
		this.px = px;
		this.py = py;
		this.col = col;
	}
}
