import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class Score {
	String spielerName;
	int spielerNummer;
	int spielerScore;
	int level;

	public Score(){};


	public int getLevel() {
		return level;
	}
 
	@XmlAttribute
	public void setLevel(int level) {
		this.level = level;
	}

	public int getspielerNummer() {
		return spielerNummer;
	}
 
	@XmlElement
	public void setspielerNummer(int spielerNummer) {
		this.spielerNummer = spielerNummer;
	}

	public int getspielerScore() {
		return spielerScore;
	}
 
	@XmlElement
	public void setspielerScore(int spielerScore) {
		this.spielerScore = spielerScore;
	}

	public String getspielerName() {
		return spielerName;
	}
 
	@XmlElement
	public void setspielerName(String spielerName) {
		this.spielerName = spielerName;
	}
}
