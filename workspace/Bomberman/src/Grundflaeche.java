public class Grundflaeche {
	private double dx;
	private double dy;
	private int nx, ny;
	public Colour col;
	
	int initialposition[][] = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
			{1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			} ;
	Mauerstein mauerstueck[][];
	
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
	
	public Grundflaeche(){
		this.dx = 0.1;
		this.dy = 0.1;
		this.nx = 11;
		this.ny = 11;
		this.mauerstueck = new Mauerstein[this.nx] [this.ny];
	}
	
	public void draw(){
		
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
				mauerstueck[i][j].draw();
			}
		}
	}				
}