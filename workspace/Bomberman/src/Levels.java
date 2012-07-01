import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class Levels {
	int initialposition[][];
	int nx, ny;
	double dx, dy;
	int level;
	int anzahlleben;
	protected static boolean editmode=false;

	static Levels playlevel=new Levels();

	public Levels(){};

	public static Levels getplaylevel(){
		return playlevel;
	}


	public static void setplaylevel(Levels level){
		playlevel=level;
	}

	public int getLevel() {
		return level;
	}
 
	@XmlAttribute
	public void setLevel(int level) {
		this.level = level;
	}

	public int getNx() {
		return nx;
	}
 
	@XmlElement
	public void setNx(int nx) {
		this.nx = nx;
	}

	public int getNy() {
		return ny;
	}
 
	@XmlElement
	public void setNy(int ny) {
		this.ny = ny;
	}

	public double getDx() {
		return dx;
	}
 
	@XmlElement
	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}
 
	@XmlElement
	public void setDy(double dy) {
		this.dy = dy;
	}

	public int[][] getInitialposition() {
		return initialposition;
	}
 
	@XmlElement
	public void setInitialposition(int [][] initialposition) {
		this.initialposition = initialposition;
	}

	public int getAnzahlleben() {
		return anzahlleben;
	}
 
	@XmlElement
	public void setAnzahlleben(int anzahlleben) {
		this.anzahlleben = anzahlleben;
	}

	public static boolean getEditMode() {
		return editmode;
	}
 
	public static void setEditMode(boolean mode) {
		editmode = mode;
	}


}
