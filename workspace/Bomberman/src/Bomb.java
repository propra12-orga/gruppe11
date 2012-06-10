public class Bomb extends Spieler {
    
    protected double majorAxis, minorAxis;   
    private boolean active;
    private int activecount;
    private int explodecount;
    final private int activetime = 50;
    final private int explodetime = 2;
    private Colour excol;
    private int exploderadius=2;
    
    public Bomb() {
        this.majorAxis =  0.01  + Math.random() * 0.01;
        this.minorAxis =  0.01  + Math.random() * 0.01;
	active=false;
	activecount=0;
    } 
	
    public Bomb(double px, double py, double major, double minor, Colour col, Colour excol, int symbol) {
	    	this.px = px;
		this.py = py;
		this.col = col;
		this.majorAxis = major;
		this.minorAxis = minor;
		active=false;
		activecount=0;
		this.excol=excol;
		spielersymbol=symbol;
	}
    private static Colour white(){
		int red = 255;
		int green = 255;
		int blue = 255;
		
		return new Colour(red, green, blue);
    } 
    public void draw() {
        StdDraw.setPenColor(col);
        StdDraw.filledEllipse(px, py, majorAxis, minorAxis);
    }
    
    public void activate() {
	    active=true;
	    activecount=0;
	    explodecount=0;
    }

    public void explode (Grundflaeche fl, Spieler spieler1, Spieler spieler2, Bomb bombex) {
	    int jposition;
	    int iposition;
	    double dx=fl.getdx();
	    double dy=fl.getdy();

            StdDraw.setPenColor(excol);
	    StdDraw.filledEllipse (px, py, majorAxis, 2*exploderadius*majorAxis);
	    StdDraw.filledEllipse (px, py, 2*exploderadius*majorAxis, majorAxis);
	    iposition=fl.geti(py);
	    jposition=fl.getj(px);
//	    System.out.println("------------------------------------");
            for (int i=Math.max(0, iposition); i>=Math.max(0, iposition-exploderadius); i--){
//	         System.out.println( "i down =" + i + ", jpos= " + jposition + ", ipos= " + iposition );
//		 System.out.println(fl.initialposition[i][jposition]);
		 if (fl.initialposition[i][jposition] == Grundflaeche.festemauer){
			 break;
		 }
		 else if (fl.initialposition[i][jposition] == Grundflaeche.losemauer) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			fl.mauerstueck[i][jposition]= new Rectangle(jposition*dx, i*dy, dx, dy, white());
		 }
		 else if (fl.initialposition[i][jposition] == Grundflaeche.spielersym1) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			fl.mauerstueck[i][jposition]= new Rectangle(jposition*dx, i*dy, dx, dy, white());
			spieler1.active=false;
		 }
		 else if (fl.initialposition[i][jposition] == Grundflaeche.spielersym2) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			fl.mauerstueck[i][jposition]= new Rectangle(jposition*dx, i*dy, dx, dy, white());
			spieler2.active=false;
		 }
		 else if (fl.initialposition[i][jposition] == bombex.spielersymbol) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			System.out.println(" Andere Bombe getroffen");
			bombex.explode(fl, spieler1, spieler2, this);
		 }
	    }

            for (int i=Math.min(iposition+1, fl.getny()); i<=Math.min(fl.getny(), iposition+exploderadius); i++){
//	         System.out.println( "i up =" + i + ", jpos= " + jposition + ", ipos= " + iposition );
//		 System.out.println(fl.initialposition[i][jposition]);
		 if (fl.initialposition[i][jposition] == Grundflaeche.festemauer){
			 break;
		 }
		 else if (fl.initialposition[i][jposition] == Grundflaeche.losemauer) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			fl.mauerstueck[i][jposition]= new Rectangle(jposition*dx, i*dy, dx, dy, white());
		 }
		 else if (fl.initialposition[i][jposition] == Grundflaeche.spielersym1) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			fl.mauerstueck[i][jposition]= new Rectangle(jposition*dx, i*dy, dx, dy, white());
			spieler1.active=false;
		 }
		 else if (fl.initialposition[i][jposition] == Grundflaeche.spielersym2) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			fl.mauerstueck[i][jposition]= new Rectangle(jposition*dx, i*dy, dx, dy, white());
			spieler2.active=false;
		 }
		 else if (fl.initialposition[i][jposition] == bombex.spielersymbol) {
			fl.initialposition[i][jposition] = Grundflaeche.keinemauer;
			System.out.println(" Andere Bombe getroffen");
			bombex.explode(fl, spieler1, spieler2, this);
		 }
	    }

            for (int j=Math.max(0, jposition-1); j>=Math.max(0, jposition-exploderadius); j--){
//	         System.out.println( "j left =" + j + ", jpos= " + jposition + ", ipos= " + iposition );
//		 System.out.println(fl.initialposition[iposition][j]);
		 if (fl.initialposition[iposition][j] == Grundflaeche.festemauer){
			 break;
		 }
		 else if (fl.initialposition[iposition][j] == Grundflaeche.losemauer) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			fl.mauerstueck[iposition][j]= new Rectangle(j*dx, iposition*dy, dx, dy, white());
		 }
		 else if (fl.initialposition[iposition][j] == Grundflaeche.spielersym1) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			fl.mauerstueck[iposition][j]= new Rectangle(j*dx, iposition*dy, dx, dy, white());
			spieler1.active=false;
		 }
		 else if (fl.initialposition[iposition][j] == Grundflaeche.spielersym2) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			fl.mauerstueck[iposition][j]= new Rectangle(j*dx, iposition*dy, dx, dy, white());
			spieler2.active=false;
		 }
		 else if (fl.initialposition[iposition][j] == bombex.spielersymbol) {
			bombex.explode(fl, spieler1, spieler2, this);
			System.out.println(" Andere Bombe getroffen");
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
		 }
	    }
            for (int j=Math.min(jposition+1, fl.getnx()); j<=Math.min(fl.getnx(), jposition+exploderadius); j++){
//	         System.out.println( "j right =" + j + ", jpos= " + jposition + ", ipos= " + iposition );
//		 System.out.println(fl.initialposition[iposition][j]);
		 if (fl.initialposition[iposition][j] == Grundflaeche.festemauer){
			 break;
		 }
		 else if (fl.initialposition[iposition][j] == Grundflaeche.losemauer) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			fl.mauerstueck[iposition][j]= new Rectangle(j*dx, iposition*dy, dx, dy, white());
		 }
		 else if (fl.initialposition[iposition][j] == Grundflaeche.spielersym1) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			fl.mauerstueck[iposition][j]= new Rectangle(j*dx, iposition*dy, dx, dy, white());
			spieler1.active=false;
		 }
		 else if (fl.initialposition[iposition][j] == Grundflaeche.spielersym2) {
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
			fl.mauerstueck[iposition][j]= new Rectangle(j*dx, iposition*dy, dx, dy, white());
			spieler2.active=false;
		 }
		 else if (fl.initialposition[iposition][j] == bombex.spielersymbol) {
			bombex.explode(fl, spieler1, spieler2, this);
			System.out.println(" Andere Bombe getroffen");
			fl.initialposition[iposition][j] = Grundflaeche.keinemauer;
		 }
	    }
    }
    public void redraw(Grundflaeche fl, Spieler spieler1, Spieler spieler2, Bomb bombex){
	        int bombei=fl.geti(py);
		int bombej=fl.getj(px);
		if (active) {
			fl.initialposition[bombej][bombei] = spielersymbol;
			if (activecount < activetime) {
				activecount ++;
				this.draw();
			}
			else if (explodecount < explodetime) {
				explodecount ++;
				this.explode(fl, spieler1, spieler2, bombex);
			}
			else {active = false; 
			   fl.initialposition[bombej][bombei] = Grundflaeche.keinemauer;
			}
		}
    }
}
