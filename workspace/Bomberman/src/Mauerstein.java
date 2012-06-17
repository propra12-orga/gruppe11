/**
 *  <i>Mauerstein</i>. Diese Klasse ist die Basis für ein Objekt auf der Grundfläche. Also beispielsweise ein Mauerstein oder ein Spieler.
 *  Auch eine Bombe ist ein Grundflächenobjekt.
 *  <p>
 *  Die Idee wurde aus der Klasse "Flying Object" aus dem 1. Semester entnommen.
 */
abstract class Mauerstein implements Drawable {
    
    protected Colour col;
    protected double px, py;  
    protected double vx, vy;  
    protected boolean active=false;
    protected int symbol;
    protected boolean posChanged;
    protected double newx, newy;
    protected double oldx, oldy;

    
    /**
     * Erzeugung einer Zufallsfarbe
     */
    private static Colour randCol() {
        int red   = (int)(Math.random()*255.0);
        int green = (int)(Math.random()*255.0);
        int blue  = (int)(Math.random()*255.0);

        return new Colour(red,green,blue);
    }
	
    /**
     * Konstructor für Mauerstein
     */
    public Mauerstein() {
        px = py = 0.5;
        col     = randCol();    
        vx      = 0.015 - Math.random() * 0.03;
        vy      = 0.015 - Math.random() * 0.03;
    } 
	
    /**
     * Konstructor für Mauerstein
     *
     * @param px die X-Position des Mauersteins
     * @param py die Y-Position des Mauersteins
     * @param col die Farbe Mauersteins
     */
    public Mauerstein(double px, double py, Colour col) {
		this.px = px;
		this.py = py;
		this.col = col;
        	vx      = 0.015 - Math.random() * 0.03;
        	vy      = 0.015 - Math.random() * 0.03;
	}
	
    /**
     * Verschiebung mit einer Geschwindigkeit vx / vy
     * Richtungswechsel bei Erreichen des Randes.
     */
    public void move() {
        if ((px + vx > 1.0) || (px + vx < 0.0)) vx = -vx;
        if ((py + vy > 1.0) || (py + vy < 0.0)) vy = -vy;
        px = px + vx;
        py = py + vy;
    }


    /**
     * Verschiebung an eine definierte Position
     *
     * @param newx die neue X-Position des Mauersteins
     * @param newy die neue Y-Position des Mauersteins
     */
    public void movetoPosition (double newx, double newy){
	        if (newx < 0.0) {newx = 0.0;}
	        if (newy < 0.0) {newy = 0.0;}
	        if (newx > 1.0) {newx = 1.0;}
	        if (newy > 1.0) {newy = 1.0;}
	        if (!posChanged){
			oldx=px;oldy=py; posChanged=true;}
	    	this.newx = newx;
		this.newy = newy;
		setPosition (newx, newy);
    }

    /**
     * Setzen an eine definierte Position
     *
     * @param px die X-Position des Mauersteins
     * @param py die Y-Position des Mauersteins
     */
    public void setPosition (double px, double py){
		    	this.px = px;
			this.py = py;
    }
    
    /**
     * Rückgabe des aktuellen X Wertes
     *
     * @return Rückgabe der X Position
     */
    public double getxPosition(){
	    	return px;
    }
    
    /**
     * Rückgabe des aktuellen Y Wertes
     *
     * @return Rückgabe der Y Position
     */
    public double getyPosition(){
	    	return py;
    }

}
