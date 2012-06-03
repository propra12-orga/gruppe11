import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D.*;

public class Spieler extends Ball {
   Ellipse2D.Double ellipse;
    
    public Spieler() {
        super.radius =  0.01  + Math.random() * 0.01;
    } 
	
    public Spieler(double px, double py, double radius, Colour col) {
		super(px, py, radius, col);
		super.radius = radius/2.0;
	}
    
    public void setPosition (double px, double py){
	    	super.px = px;
		super.py = py;
    }
    
    public double getxPosition(){
	    	return super.px;
    }
    
    public double getyPosition(){
	    	return super.py;
    }
    public void draw () {
        if (super.radius < 0) throw new RuntimeException("circle radius can't be negative");
	StdDraw.setPenColor(col);
        double xs = StdDraw.scaleX(super.px);
        double ys = StdDraw.scaleY(super.py);
        double ws = StdDraw.factorX(2*super.radius);
        double hs = StdDraw.factorY(2*super.radius);
        if (ws <= 1 && hs <= 1) StdDraw.pixel(super.px, super.py);
        else { ellipse = new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs);
		StdDraw.offscreen.fill(ellipse);
    	}
    }
}
