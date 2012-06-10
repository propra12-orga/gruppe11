import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D.*;

public class Spieler extends Ball {
   private double newx, newy;
   private double oldx, oldy;
   private boolean posChanged;
   protected int spielersymbol;
   boolean active=false;
    
    public Spieler() {
        super.radius =  0.01  + Math.random() * 0.01;
	newx=0.0;
	newy=0.0;
	oldx=0.0;
	oldy=0.0;
	posChanged=false;
    } 
	
    public Spieler(double px, double py, double radius, Colour col, int symbol) {
		super(px, py, radius, col);
		super.radius = radius/2.0;
		newx=px;
		newy=py;
		oldx=px;
		oldy=py;
		posChanged=false;
		spielersymbol=symbol;
	}
    
    public void movetoPosition (double newx, double newy){
	        if (newx < 0.0) {newx = 0.0;}
	        if (newy < 0.0) {newy = 0.0;}
	        if (!posChanged){
			oldx=px;oldy=py; posChanged=true;}
	    	this.newx = newx;
		this.newy = newy;
		setPosition (newx, newy);
    }
    
    public void setPosition (double px, double py){
		    	this.px = px;
			this.py = py;
    }
    
    public double getxPosition(){
	    	return px;
    }
    
    public double getyPosition(){
	    	return py;
    }

    public void redraw(Grundflaeche fl){
	   	double xi1 = (px+fl.getdx()/3.0) / fl.getdx();
		double yi1 = py / fl.getdy();
	   	double xi2 = (px+fl.getdx()/1.4) / fl.getdx();
		double yi2 = (py+fl.getdy()/1.4) / fl.getdy();
		
	        int spieleriold=fl.geti(oldy);
	        int spielerjold=fl.getj(oldx);
	        int spieleri=fl.geti(py);
	        int spielerj=fl.getj(px);
		fl.initialposition[spieleriold][spielerjold] = 0;

		if ((fl.initialposition[(int)yi1][(int)xi1] == 0) && (fl.initialposition[(int)yi2][(int)xi2] == 0)) {
			oldx=px;
			oldy=py;
			fl.initialposition[spieleri][spielerj] = spielersymbol;
			posChanged=false;
		}	
		else {
			px=oldx; py=oldy; posChanged=false;
		}
		if (active) {this.draw();}
    }
}
