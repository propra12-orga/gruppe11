/**
 * Die Bombe ist ein Bestandteil des Spiels mit eigenen Attributen.
 *
 * Die Bombe wird als Ellipse dargestellt. Sie hat eine Aktivierungszeit, nach der sie erst explodiert. 
 * Die Explosionszeit dient zur Einstellung wie lange die Explosion dargestellt werden soll.
 * Der Explosionsradius gibt an wie viele Felder rund um die Bombe bei der Explosion betroffen sein sollen.
 * Die Explosionsfarbe kann von der Bombenfarbe unterschiedlich sein.
 * Die Bombe wurde von einem Spieler gelegt, dieser erhält einen Score wenn die Bombe bestimmte Objekte zerstört.
 */
public class Bomb extends Mauerstein {
        
    private String filename1 = ".\\GIFS\\ICON137.GIF";   
    private String filename2 = ".\\GIFS\\ICON022.JPG";   
    private String filename3 = ".\\GIFS\\ICON021.JPG";   
    private String explodfile = ".\\GIFS\\explosion3.gif";   
    private String handschuh = ".\\GIFS\\Torwarthandschuh.gif";   
    private String filename=filename1;
    private String exfilename=explodfile;
    private int typ;

    final private int bombtyp1=0;
    final private int bombtyp2=1;
    final private int bombtyp3=2;
    
    final private int exploder1=3;
    final private int exploder2=2;
    final private int exploder3=1;

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
		active=false; StdDraw.picture(px, py, filename);
		activecount=0;
		this.excol=excol;
		this.symbol=symbol;
		this.spieler=spieler;
	}
    /**
     * Definition einer weissen Farbe
     *
     * @return RGB für Weiss
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
	    /*
        StdDraw.setPenColor(col);
        StdDraw.filledEllipse(px, py, majorAxis, minorAxis);
	*/
	StdDraw.picture(px, py, filename);
    }
    
    /**
     * Aktivieren der Bombe
     */
    public void activate() {
	    if (spieler.active){
		int factor=10;
		int randomactivate=(int)(Math.random()*factor)+1;
	    	active=true;
	    	activecount=0;
	    	explodecount=0;
		if (randomactivate/3 == 1) {
			filename=filename1;
			typ=bombtyp1;
			exploderadius=exploder1;
		} else if  (randomactivate/3 == 2) {
			filename=filename2;
			typ=bombtyp2;
			exploderadius=exploder2;
		}
		 else if  (randomactivate/3 == 3) {
			filename=filename3;
			typ=bombtyp3;
			exploderadius=exploder3;
		}
	    }
    }

    /**
     * Explodieren der Bombe
     *
     * @param fl Auf welcher Grundfläche die Bombe liegt.
     * @param spieler1 Erster Spieler auf der Grundfläche
     * @param spieler2 Zweiter Spieler auf der Grundfläche
     * @param bombex Weitere Bombe auf der Grundfläche (für Kettenreaktion)
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

	    //i und j Position auf der Grundfläche holen
	    iposition=fl.geti(py);
	    jposition=fl.getj(px);

	    // Der Explodecount gibt an wieviele Zyklen die Explosion gezeichnet wird. 
	    // Aber nur beim ersten Zyklus muss berechnet werden was alles kaputtgemacht wird.
	    if (explodecount==1){
		StdAudio.play(".\\Sound\\Ton2.wav");

            /*
	     * Hier folgen vier Schleifen, die von der Bombenposition auf der Grundfläche in die jeweilige Richtung schauen ob es im Explosionsradius ein Element zu zerstöre gibt
	     */

	    // Von der aktuellen Position nach unten gehen
            for (int i=Math.max(0, iposition); i>=Math.max(0, iposition-exploderadius); i--){

		 // Wenn eine feste Mauer im Weg liegt, wird dahinter nichts mehr zerstört. Die Schleife wird abgebrochen^
		 if (fl.initialposition[i][jposition] == Grundflaeche.festemauer){
			 break;
		 }

		 // Wenn ann der Position eine Losemauer liegt (eventuell über einem Ausgang), gibt es etwas zu tun.
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
			if ((i == iposition) && (typ==bombtyp3) && spieler1.superman){
				spieler1.score++;
			}
			else {
				spieler1.reducelive(fl);
			}
		 }
		 if (fl.initialposition[i][jposition] == Grundflaeche.spielersym2) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			if ((i == iposition) && (typ==bombtyp3) && spieler2.superman){
				spieler2.score++;
			}
			else {
				spieler2.reducelive(fl);
			}
		 }

		 // Wenn wir auf eine andere Bombe treffen, lassen wir diese explodieren.
		 if (i==bombex.ipos && jposition == bombex.jpos && bombex.active) {
			System.out.println(" Andere Bombe getroffen");
			bombex.explode(fl, spieler1, spieler2, this);
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
		 }
	    }

	    // hier gehen wir von der aktuellen Bombenposition nach oben ... die Aktivitäten sind die Gleichen.
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
		 if (i==bombex.ipos && jposition == bombex.jpos && bombex.active) {
			System.out.println(" Andere Bombe getroffen");
			bombex.explode(fl, spieler1, spieler2, this);
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
		 }
	    }

	    // hier gehen wir von der aktuellen Bombenposition nach links ... die Aktivitäten sind die Gleichen.
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
		 if (iposition==bombex.ipos && j == bombex.jpos && bombex.active) {
			bombex.explode(fl, spieler1, spieler2, this);
			System.out.println(" Andere Bombe getroffen");
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
		 }
	    }
	    
	    // hier gehen wir von der aktuellen Bombenposition nach rechts ... die Aktivitäten sind die Gleichen.
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
		 if (iposition==bombex.ipos && j == bombex.jpos && bombex.active) {
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
     * @param fl Grundfläche auf der die Bombe neu gezeichnet werden soll
     * @param spieler1 Übergabe des ersten Spielers auf der Grundfläche StdDraw.picture(px, py, filename);
     * @param spieler2 Übergabe des zweiten Spielers auf der grundfläche
     * @param bombex Übergabe der zweiten Bombe für die Kettenreaktion
     */
    public void redraw(Grundflaeche fl, Spieler spieler1, Spieler spieler2, Bomb bombex){
	        ipos=fl.geti(py);
		jpos=fl.getj(px);
		// Aktionen nur, wenn die Bombe Aktiv ist.
		if (active) {
			if (activecount < activetime) {
				// Zeichnen der Bombe solange sie Aktiv ist
				activecount ++;
				if (activecount == activetime) {
					filename=explodfile;
				}
				this.draw();
			}
			else if (explodecount < explodetime) {
				// Explodieren der Bombe solange sie Aktiv ist.
				explodecount ++;
				this.explode(fl, spieler1, spieler2, bombex);
				this.draw();
			}
			else {active = false; 
			   // Zurücksetzen der Position auf Keinemauer.
			   filename=filename1;
			}
		}
    }
}
