abstract class Mauerstein implements Drawable {
    
    public Colour col;
    public double px, py;  
    public double vx, vy;  
    
    private static Colour randCol() {
        int red   = (int)(Math.random()*255.0);
        int green = (int)(Math.random()*255.0);
        int blue  = (int)(Math.random()*255.0);

        return new Colour(red,green,blue);
    }
	
	
    
    public Mauerstein() {
        px = py = 0.5;
        col     = randCol();    
        vx      = 0.015 - Math.random() * 0.03;
        vy      = 0.015 - Math.random() * 0.03;
    } 
	
    public Mauerstein(double px, double py, Colour col) {
		this.px = px;
		this.py = py;
		this.col = col;
        	vx      = 0.015 - Math.random() * 0.03;
        	vy      = 0.015 - Math.random() * 0.03;
	}
	
    public void move() {
        if ((px + vx > 1.0) || (px + vx < 0.0)) vx = -vx;
        if ((py + vy > 1.0) || (py + vy < 0.0)) vy = -vy;
        px = px + vx;
        py = py + vy;
    }

}
