/**
 * Grundflaeche stellt die Spielebene dar. Die Elemente sind auf der Grundfläche angelegt und werden
 * auf dieser bewegt.
 *
 * Die Grundfläche setzt sich aus quadratischen Elementen zusammen.
 */
public class Grundflaeche {
	private double dx;
	private double dy;
	private int nx, ny;
	public Colour col;
	final static int keinemauer=0;
	final static int festemauer=1;
	final static int losemauer=2;
	final static int spielersym1=10;
	final static int spielersym2=11;
	final static int bombesym1=21;
	final static int bombesym2=22;
	final static int ausgang=100;
	
	/* initialposition legt die Anfangsbedingung fest, wird aber auch im Laufe des Spiels immer wieder genutzt um festzustellen welche Art von
	 * Elementen auf der jeweiligen Position liegen. Wenn ein "loser Mauerstein" durch eine Bombe zerstört wird, wird die Position durch 
	 * "Keinemauer" ersetzt ... Spieler können nur auf Positionen gehen, die "Keinemauer" beinhalten.
	 */
	int initialposition[][] = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 11, 0, 0, 0, 0, 0, 0, 2, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 1, 1, 2, 1},
			{1, 0, 1, 0, 1, 0, 0, 2, 1, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 0, 1, 2, 102},
			{1, 0, 2, 0, 2, 0, 0, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 2, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
			{1, 0, 1, 2, 1, 0, 1, 1, 1, 2, 1},
			{10, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			} ;
	Mauerstein mauerstueck[][];
	Spieler spieler1, spieler2;
	
	/**
	 * Defaultkonstruktor
	 */
	public Grundflaeche(){
		this.dx = 0.1;
		this.dy = 0.1;
		this.nx = 11;
		this.ny = 11;
		this.mauerstueck = new Mauerstein[this.nx] [this.ny];
		this.spieler1 = new Spieler(dx, dy, dx/2, yellow(), spielersym1);
		this.spieler2 = new Spieler(2*dx, 2*dy, dx/2, pink(), spielersym2);
	}
	
	/**
	 * Konstruktor
	 *
	 * @param spieler1 Erster Spieler
	 * @param spieler2 Zweiter Spieler
	 * @param dx Breite x eines Grundflächenelements
	 * @param dy Höhe y eines Grundflächenelements
	 */
	public Grundflaeche(Spieler spieler1, Spieler spieler2, double dx, double dy){
		this.dx = dx;
		this.dy = dy;
		this.nx = 11;
		this.ny = 11;
		this.mauerstueck = new Mauerstein[this.nx] [this.ny];
		this.spieler1 = spieler1;
		this.spieler2 = spieler2;
	}

	/**
	 * Definition der Farben Blau, Grün, Gelb, Weiss .....
	 */
	private static Colour blue(){
		int red = 0;
		int green = 0;
		int blue = 255;
		
		return new Colour(red, green, blue);
	}
	
	private static Colour green(){
		int red = 0;
		int green = 255;
		int blue = 0;
		
		return new Colour(red, green, blue);
	}
	
	private static Colour white(){
		int red = 255;
		int green = 255;
		int blue = 255;
		
		return new Colour(red, green, blue);
	}
	
	private static Colour yellow(){
		int red = 255;
		int green = 255;
		int blue = 200;
		
		return new Colour(red, green, blue);
	}

	
	private static Colour pink(){
		int red = 255;
		int green = 50;
		int blue = 255;
		
		return new Colour(red, green, blue);
	}


	/**
	 * @return Rückgabe der Breite eines Grundflächenelements
	 */
	public double getdx(){ return dx;}

	/**
	 * @return Rückgabe der Höhe eines Grundflächenelements
	 */
	public double getdy(){ return dy;}

	/**
	 * @return Rückgabe der Anzahl der X Elemente
	 */
	public int getnx(){ return nx-1;}

	/**
	 * @return Rückgabe der Anzahl der Y Elemente
	 */
	public int getny(){ return ny-1;}
	
	/**
	 * @param py Y-Position die abgefragt wird
	 * @return Rückgabe des Höhenelementes 
	 */
	public int getj(double py){
		double yi1 = py / dy;
		return (int)Math.round(yi1);
	}
	
	/**
	 * @param px X-Position die abgefragt wird
	 * @return Rückgabe des Breitenelements 
	 */
	public int geti(double px){
	   	double xi1 = px / dx;
		return (int)Math.round(xi1);
	}

	/**
	 * Zeichnen der Grundflächenelemente (Mauerstücke) in Abhängigkeit Ihrer "Aktivität = Sichtbarkeit"
	 */
	public void redraw(){
		
		for (int i=0; i<ny; i++){
			for (int j=0; j<nx; j++){
				if (mauerstueck[i][j].active) {
					mauerstueck[i][j].draw();
				}
			}
		}
	}				


	/**
	 * Zeichnen der kompletten Grundfläche mit der Anlage der Elemente
	 * in Abhängigkeit der "Visibility" der Grundfläche
	 */

	public void draw(boolean visible){
		
		for (int i=0; i<ny; i++){
			for (int j=0; j<nx; j++){
				if (initialposition[i][j] % Grundflaeche.ausgang ==Grundflaeche.keinemauer){
					mauerstueck[i][j] = new Rectangle(j*dx, i*dy, dx, dy, white()); 
					mauerstueck[i][j].active = false;
				}
				if (initialposition[i][j] % Grundflaeche.ausgang ==Grundflaeche.festemauer){
					mauerstueck[i][j] = new Rectangle(j*dx,i*dy, dx, dy, blue());
					mauerstueck[i][j].active = true;
				}
				if (initialposition[i][j] % Grundflaeche.ausgang ==Grundflaeche.losemauer){
					mauerstueck[i][j] = new Rectangle(j*dx, i*dy, dx, dy, green());
					mauerstueck[i][j].active = true;
				}
				if (initialposition[i][j] % Grundflaeche.ausgang ==Grundflaeche.spielersym1){
					spieler1.setPosition(j*dx, i*dy);
					mauerstueck[i][j] = spieler1;
					mauerstueck[i][j].active = false;
					initialposition[i][j] = Grundflaeche.keinemauer;
				}
				if (initialposition[i][j] % Grundflaeche.ausgang ==Grundflaeche.spielersym2){
					spieler2.setPosition(j*dx, i*dy);
					mauerstueck[i][j] = spieler2;
					mauerstueck[i][j].active = false;
					initialposition[i][j] = Grundflaeche.keinemauer;
				}
				if (visible) {mauerstueck[i][j].draw();}
			}
		}
	}				

}
