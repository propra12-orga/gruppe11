/**
 * Die Bombe ist ein Bestandteil des Spiels mit eigenen Attributen.
 *
 * Die Bombe wird als Ellipse dargestellt. Sie hat eine Aktivierungszeit, nach der sie erst explodiert. 
 * Die Explosionszeit dient zur Einstellung wie lange die Explosion dargestellt werden soll.
 * Der Explosionsradius gibt an wie viele Felder rund um die Bombe bei der Explosion betroffen sein sollen.
 * Die Explosionsfarbe kann von der Bombenfarbe unterschiedlich sein.
 * Die Bombe wurde von einem Spieler gelegt, dieser erh�lt einen Score wenn die Bombe bestimmte Objekte zerst�rt.
 */
public class Bomb extends Mauerstein {
    
    protected double majorAxis, minorAxis;   
    private int activecount;
    private int explodecount;
    final private int activetime = 50;
    final private int explodetime = 2;
    private Colour excol;
    private int exploderadius=2;
    public int ipos, jpos;
    Spieler spieler;
    
/**
 * Default Konstruktor
 *
 */
    public Bomb() {
        this.majorAxis =  0.01  + Math.random() * 0.01;
        this.minorAxis =  0.01  + Math.random() * 0.01;
	active=false;
	activecount=0;
    } 
	
/**
 * Konstruktor der die Bombe initiiert.
 *
 * @param px X-Position der Bombe
 * @param py Y-Position der Bombe
 * @param major Hauptachse der Bom
 *
 */
    public Bomb(double px, double py, double major, double minor, Colour col, Colour excol, Spieler spieler, int symbol) {
	    	this.px = px;
		this.py = py;
		this.col = col;
		this.majorAxis = major;
		this.minorAxis = minor;
		active=false;
		activecount=0;
		this.excol=excol;
		this.symbol=symbol;
		this.spieler=spieler;
	}
    /**
     * Definition einer weissen Farbe
     *
     * @return RGB f�r Weiss
     */
    private static Colour white(){
		int red = 255;
		int green = 255;
		int blue = 255;
		
		return new Colour(red, green, blue);
    } 

    /**
     * Zeichnen der Bombe
     */
    public void draw() {
        StdDraw.setPenColor(col);
        StdDraw.filledEllipse(px, py, majorAxis, minorAxis);
    }
    
    /**
     * Aktivieren der Bombe
     */
    public void activate() {
	    active=true;
	    activecount=0;
	    explodecount=0;
    }

    /**
     * Explodieren der Bombe
     *
     * @param fl Auf welcher Grundfl�che die Bombe liegt.
     * @param spieler1 Erster Spieler auf der Grundfl�che
     * @param spieler2 Zweiter Spieler auf der Grundfl�che
     * @param bombex Weitere Bombe auf der Grundfl�che (f�r Kettenreaktion)
     *
     */
    public void explode (Grundflaeche fl, Spieler spieler1, Spieler spieler2, Bomb bombex) {
	    int jposition;
	    int iposition;
	    // dx und dy der Grundelemente holen
	    double dx=fl.getdx();
	    double dy=fl.getdy();

	    //Explosionsfarbe setzen
            StdDraw.setPenColor(excol);
	    //Explosion malen (zwei Elipsen die jeweils ein Grundelement breit/hoch und 2x den Explosionsradius abdecken)
	    StdDraw.filledEllipse (px, py, majorAxis, 2*exploderadius*majorAxis);
	    StdDraw.filledEllipse (px, py, 2*exploderadius*majorAxis, majorAxis);

	    //i und j Position auf der Grundfl�che holen
	    iposition=fl.geti(py);
	    jposition=fl.getj(px);

	    // Der Explodecount gibt an wieviele Zyklen die Explosion gezeichnet wird. 
	    // Aber nur beim ersten Zyklus muss berechnet werden was alles kaputtgemacht wird.
	    if (explodecount==1){


            /*
	     * Hier folgen vier Schleifen, die von der Bombenposition auf der Grundfl�che in die jeweilige Richtung schauen ob es im Explosionsradius ein Element zu zerst�re gibt
	     */

	    // Von der aktuellen Position nach unten gehen
            for (int i=Math.max(0, iposition); i>=Math.max(0, iposition-exploderadius); i--){

		 // Wenn eine feste Mauer im Weg liegt, wird dahinter nichts mehr zerst�rt. Die Schleife wird abgebrochen^
		 if (fl.initialposition[i][jposition] == Grundflaeche.festemauer){
			 break;
		 }

		 // Wenn ann der Position eine Losemauer liegt (eventuell �ber einem Ausgang), gibt es etwas zu tun.
		 if (fl.initialposition[i][jposition] % Grundflaeche.ausgang == Grundflaeche.losemauer) {
			// Wenn es ein Ausgang ist, dann setze die Position wieder auf Ausgang, sonst auf Keinemauer.
			if (fl.initialposition[i][jposition] > Grundflaeche.ausgang) {fl.initialposition[i][jposition] = Grundflaeche.ausgang;}
			else { fl.initialposition[i][jposition] = Grundflaeche.keinemauer;}

			this.spieler.score++;

			// Keinemauer muss nicht gezeichnet werden (Zeit sparen)
			fl.mauerstueck[i][jposition].active = false;
		 }

		 // Wenn es sich um einen Spieler handelt (spieler1 oder spieler2) dann wird das Leben des Spielers um 1 reduziert.
		 if (fl.initialposition[i][jposition] == Grundflaeche.spielersym1) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			spieler1.reducelive(fl);
		 }
		 if (fl.initialposition[i][jposition] == Grundflaeche.spielersym2) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			spieler2.reducelive(fl);
		 }

		 // Wenn wir auf eine andere Bombe treffen, lassen wir diese explodieren.
		 if (i==bombex.ipos && jposition == bombex.jpos) {
			System.out.println(" Andere Bombe getroffen");
			bombex.explode(fl, spieler1, spieler2, this);
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
		 }
	    }

	    // hier gehen wir von der aktuellen Bombenposition nach oben ... die Aktivit�ten sind die Gleichen.
            for (int i=Math.min(iposition+1, fl.getny()); i<=Math.min(fl.getny(), iposition+exploderadius); i++){
		 if (fl.initialposition[i][jposition] == Grundflaeche.festemauer){
			 break;
		 }
		 if (fl.initialposition[i][jposition] % Grundflaeche.ausgang == Grundflaeche.losemauer) {
			if (fl.initialposition[i][jposition] > Grundflaeche.ausgang) {fl.initialposition[i][jposition] = Grundflaeche.ausgang;}
			else {fl.initialposition[i][jposition] = Grundflaeche.keinemauer;}

			this.spieler.score++;

			fl.mauerstueck[i][jposition].active=false;
		 }
		 if (fl.initialposition[i][jposition] == Grundflaeche.spielersym1) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			spieler1.reducelive(fl);
		 }
		 if (fl.initialposition[i][jposition] == Grundflaeche.spielersym2) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			spieler2.reducelive(fl);
		 }
		 if (i==bombex.ipos && jposition == bombex.jpos) {
			System.out.println(" Andere Bombe getroffen");
			bombex.explode(fl, spieler1, spieler2, this);
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
		 }
	    }

	    // hier gehen wir von der aktuellen Bombenposition nach links ... die Aktivit�ten sind die Gleichen.
            for (int j=Math.max(0, jposition-1); j>=Math.max(0, jposition-exploderadius); j--){
		 if (fl.initialposition[iposition][j] == Grundflaeche.festemauer){
			 break;
		 }
		 if (fl.initialposition[iposition][j] % Grundflaeche.ausgang == Grundflaeche.losemauer) {
			 if (fl.initialposition[iposition][j] > Grundflaeche.ausgang) {fl.initialposition[iposition][j] = Grundflaeche.ausgang;}
			 else {fl.initialposition[iposition][j] = Grundflaeche.keinemauer;}

			this.spieler.score++;

			fl.mauerstueck[iposition][j].active = false;
		 }
		 if (fl.initialposition[iposition][j] == Grundflaeche.spielersym1) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			spieler1.reducelive(fl);
		 }
		 if (fl.initialposition[iposition][j] == Grundflaeche.spielersym2) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			spieler2.reducelive(fl);
		 }
		 if (iposition==bombex.ipos && j == bombex.jpos) {
			bombex.explode(fl, spieler1, spieler2, this);
			System.out.println(" Andere Bombe getroffen");
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
		 }
	    }
	    
	    // hier gehen wir von der aktuellen Bombenposition nach rechts ... die Aktivit�ten sind die Gleichen.
            for (int j=Math.min(jposition+1, fl.getnx()); j<=Math.min(fl.getnx(), jposition+exploderadius); j++){
		 if (fl.initialposition[iposition][j] == Grundflaeche.festemauer){
			 break;
		 }
		 if (fl.initialposition[iposition][j] % Grundflaeche.ausgang == Grundflaeche.losemauer) {
			if (fl.initialposition[iposition][j] > Grundflaeche.ausgang) {fl.initialposition[iposition][j] = Grundflaeche.ausgang;}
			else {fl.initialposition[iposition][j] = Grundflaeche.keinemauer;}

			this.spieler.score++;

			fl.mauerstueck[iposition][j].active = false;
		 }
		 if (fl.initialposition[iposition][j] == Grundflaeche.spielersym1) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			spieler1.reducelive(fl);
		 }
		 if (fl.initialposition[iposition][j] == Grundflaeche.spielersym2) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			spieler2.reducelive(fl);
		 }
		 if (iposition==bombex.ipos && j == bombex.jpos) {
			bombex.explode(fl, spieler1, spieler2, this);
			System.out.println(" Andere Bombe getroffen");
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
		 }
	    }
	    }
    }

    /**
     * Neuzeichnen der Bombe 
     *
     * @param fl Grundfl�che auf der die Bombe neu gezeichnet werden soll
     * @param spieler1 �bergabe des ersten Spielers auf der Grundfl�che
     * @param spieler2 �bergabe des zweiten Spielers auf der grundfl�che
     * @param bombex �bergabe der zweiten Bombe f�r die Kettenreaktion
     */
    public void redraw(Grundflaeche fl, Spieler spieler1, Spieler spieler2, Bomb bombex){
	        ipos=fl.geti(py);
		jpos=fl.getj(px);
		// Aktionen nur, wenn die Bombe Aktiv ist.
		if (active) {
			if (activecount < activetime) {
				// Zeichnen der Bombe solange sie Aktiv ist
				activecount ++;
				this.draw();
			}
			else if (explodecount < explodetime) {
				// Explodieren der Bombe solange sie Aktiv ist.
				explodecount ++;
				this.explode(fl, spieler1, spieler2, bombex);
			}
			else {active = false; 
			   // Zur�cksetzen der Position auf Keinemauer.
			}
		}
    }
}
