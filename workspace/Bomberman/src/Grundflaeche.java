public class Grundflaeche {
	private double dx;
	private double dy;
	private int nx, ny;
	public Colour col;
	final static int keinemauer=0;
	final static int festemauer=1;
	final static int losemauer=2;
	final static int spielersym1=3;
	final static int spielersym2=4;
	final static int bombesym1=5;
	final static int bombesym2=6;
	
	int initialposition[][] = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 4, 0, 0, 0, 0, 0, 0, 2, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 1, 1, 2, 1},
			{1, 0, 1, 0, 1, 0, 0, 2, 1, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 0, 1, 2, 0},
			{1, 0, 2, 0, 2, 0, 0, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 1, 1, 2, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
			{1, 0, 1, 2, 1, 0, 1, 1, 1, 2, 1},
			{3, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			} ;
	Mauerstein mauerstueck[][];
	Spieler spieler1, spieler2;
	
	public Grundflaeche(){
		this.dx = 0.1;
		this.dy = 0.1;
		this.nx = 11;
		this.ny = 11;
		this.mauerstueck = new Mauerstein[this.nx] [this.ny];
		this.spieler1 = new Spieler(dx, dy, dx/2, yellow(), spielersym1);
	}
	
	public Grundflaeche(Spieler spieler1, Spieler spieler2, double dx, double dy){
		this.dx = dx;
		this.dy = dy;
		this.nx = 11;
		this.ny = 11;
		this.mauerstueck = new Mauerstein[this.nx] [this.ny];
		this.spieler1 = spieler1;
		this.spieler2 = spieler2;
	}

	private static Colour blue(){
		int red = 0;
		int green = 0;
		int blue = 255;
		
		return new Colour(red, green, blue);
	}
	public double getdx(){ return dx;}
	public double getdy(){ return dy;}
	public int getnx(){ return nx-1;}
	public int getny(){ return ny-1;}
	
	public int getj(double py){
		double yi1 = py / dy;
		return (int)Math.round(yi1);
	}
	
	public int geti(double px){
	   	double xi1 = px / dx;
		return (int)Math.round(xi1);
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

	public void redraw(){
		
		for (int i=0; i<ny; i++){
			for (int j=0; j<nx; j++){
				mauerstueck[i][j].draw();
			}
		}
	}				


	public void draw(boolean visible){
		
		for (int i=0; i<ny; i++){
			for (int j=0; j<nx; j++){
				if (initialposition[i][j]==0){
					mauerstueck[i][j] = new Rectangle(j*dx, i*dy, dx, dy, white()); 
				}
				if (initialposition[i][j]==1){
					mauerstueck[i][j] = new Rectangle(j*dx,i*dy, dx, dy, blue());
				}
				if (initialposition[i][j]==2){
					mauerstueck[i][j] = new Rectangle(j*dx, i*dy, dx, dy, green());
				}
				if (initialposition[i][j]==3){
					spieler1.setPosition(j*dx, i*dy);
					mauerstueck[i][j] = spieler1;
					initialposition[i][j] = 0;
				}
				if (initialposition[i][j]==4){
					spieler1.setPosition(j*dx, i*dy);
					mauerstueck[i][j] = spieler2;
					initialposition[i][j] = 0;
				}
				if (visible) {mauerstueck[i][j].draw();}
			}
		}
	}				

}
