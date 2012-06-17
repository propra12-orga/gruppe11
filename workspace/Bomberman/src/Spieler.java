/**
 * Spieler beschreibt den Spieler auf der Grundfläche
 */
public class Spieler extends Ball {

    public boolean dead=false;
    public int lives = 3;
    public int score = 0;

    final private int scoreout = 100;

    /**
     * Default Konstruktor
     */
    public Spieler() {
        super.radius =  0.01  + Math.random() * 0.01;
	newx=0.0;
	newy=0.0;
	oldx=0.0;
	oldy=0.0;
	posChanged=false;
    } 
	
    /**
     * Konstruktor
     *
     * @param px X-Position
     * @param py Y-Position
     * @param radius Radius des Spielers
     * @param col Farbe des Spielers
     * @param symbol Symbol des Spielers
     */
    public Spieler(double px, double py, double radius, Colour col, int symbol) {
		super(px, py, radius, col);
		super.radius = radius/2.0;
		newx=px;
		newy=py;
		oldx=px;
		oldy=py;
		posChanged=false;
		this.symbol=symbol;
	}
    

    /**
     * Reduziere die Anzahl Leben des Spielers
     * Wenn die Anzahl der Leben 0 ist, wird der Spieler auf der Grundflaeche deaktiviert.
     *
     * @param fl Spielfeld / Grundfläche
     */
    public void reducelive(Grundflaeche fl){
	    lives--;
	    if (lives == 0) {dead=true;deactivate(fl);}
    }

    /** 
     * Ermittelung, ob der Spieler noch lebt.
     *
     * @return True wenn die Anzahl der Leben groesser 0 sind
     */
    public boolean alive(){
	    return (lives > 0);
    }

    /**
     * Deaktivierung des Spielers auf der Grundfläche
     *
     * @param fl Grundfläche auf der der Spieler deaktiviert werden soll.
     */
    public void deactivate (Grundflaeche fl){
	        int spieleri=fl.geti(py);
	        int spielerj=fl.getj(px);
		fl.initialposition[spieleri][spielerj] =- symbol;
		active=false;
    }

    /**
     * Setzen der alten Position bei Bewegungen, Initialisierung der Position eines Spielers Alte Position.
     * @param x X-Position
     * @param y Y-Position
     */
    public void setoldPosition(double x, double y){ oldx = x; oldy =y;}

    /**
     * Neuzeichnen des Spielers auf der Grundfläche
     *
     * @param fl Grundfläche
     */
    public void redraw(Grundflaeche fl){
	   	double xi1 = (px+fl.getdx()/3.0) / fl.getdx();
		double yi1 = py / fl.getdy();
	   	double xi2 = (px+fl.getdx()/1.4) / fl.getdx();
		double yi2 = (py+fl.getdy()/1.4) / fl.getdy();
		
	        int spieleriold=fl.geti(oldy);
	        int spielerjold=fl.getj(oldx);
	        int spieleri=fl.geti(py);
	        int spielerj=fl.getj(px);
		fl.initialposition[spieleriold][spielerjold] = Grundflaeche.keinemauer;

		if ((fl.initialposition[(int)yi1][(int)xi1] % Grundflaeche.ausgang == Grundflaeche.keinemauer) && (fl.initialposition[(int)yi2][(int)xi2] % Grundflaeche.ausgang == Grundflaeche.keinemauer)) {
			oldx=px;
			oldy=py;
			if (fl.initialposition[spieleri][spielerj] == Grundflaeche.ausgang) {
				fl.initialposition[spieleri][spielerj]=Grundflaeche.ausgang+symbol;}
			else {
				fl.initialposition[spieleri][spielerj] = symbol;}
			posChanged=false;
		}	
		else {
			px=oldx; py=oldy; posChanged=false;
			fl.initialposition[spieleriold][spielerjold] = symbol;
		}
		if (active) {
			this.draw();
			spieleri=fl.geti(py);
			spielerj=fl.getj(px);
			if ( fl.initialposition[spieleri][spielerj] >= Grundflaeche.ausgang) {
				score=score+scoreout; 
				deactivate(fl); 
				lives=0;
				fl.initialposition[spieleri][spielerj] = Grundflaeche.ausgang;
			}
		} 
    }
}
