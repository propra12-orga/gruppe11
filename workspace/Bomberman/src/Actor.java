public class Actor extends Mauerstein {
    
    private String filename1 = ".\\GIFS\\ghost_0.gif";   
    private String filename2 = ".\\GIFS\\ghost_1.gif";   
    private String filename=filename1;
    private int typ;

    final static int addleben=0;
    final static int addscore=1;

    
    public Actor() {
	    super();
    } 
	
    public Actor (int actortyp) {
		super();
		this.typ=actortyp;
		if (this.typ == addscore){
		this.filename=filename1;
		}else {
		this.filename=filename2;
		}
	}
    
    public Actor (double px, double py, String filename, int actortyp) {
		super(px, py);
		this.filename=filename;
		this.typ=actortyp;
	}
    
    public void setActorType (int actortyp) {
		this.typ=actortyp;
		if (this.typ == addscore){
		this.filename=filename1;
		}else {
		this.filename=filename2;
		}
	}

    public void redraw(Grundflaeche fl, Spieler spieler1, Spieler spieler2) {
	int i0 = fl.geti(px);
	int j0 = fl.getj(py); 
	int i1 = fl.geti(spieler1.getxPosition());
	int j1 = fl.getj(spieler1.getyPosition());
	int i2 = fl.geti(spieler2.getxPosition());
	int j2 = fl.getj(spieler2.getyPosition());
        StdDraw.picture(px, py, filename);
	if ((i0 == i1) && (j0==j1) && spieler1.active){
		StdAudio.play(".\\Sound\\Ton1.wav");
		if (typ == addleben){spieler1.addlife();}
		if (typ == addscore){spieler1.score++;}
		active=false;
	}	
	if ((i0 == i2) && (j0==j2) && spieler2.active){
		StdAudio.play(".\\Sound\\Ton1.wav");
		if (typ == addleben){spieler2.addlife();}
		if (typ == addscore){spieler2.score++;}
		active=false;
	}	
	
    }

    public void draw() {
        StdDraw.picture(px, py, filename);
    }
}
