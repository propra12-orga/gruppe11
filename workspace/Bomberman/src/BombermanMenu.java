import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.Component.*;
import java.awt.event.*;
import javax.swing.*;


public class BombermanMenu extends JFrame implements ActionListener {
	final public String noplayer ="none";
	private MenuBar menubar;
	private Menu file; 
	private Menu help;
	private Menu player;
	private Menu bomberman;
	
	private String choosefname;
	private Panel p;
	private Label level, player1, player2,p1livlab, p2livlab, p1scorlab, p2scorlab;
	public SpielerListe spielerliste = new SpielerListe();
	public TextArea textarea, leveltext, p1text, p2text, p1lives, p2lives, p1score, p2score;
	public boolean neustart=false;
	public Levels defaultlevel = new Levels();
	public Levels playlevel;
	Levels savelevel;

	private Score score = new Score();
	
	public BombermanMenu(String title) {
		super(title);
		this.setLayout(new BorderLayout(15,15));
		addWindowListener(new myWindowListener());
		
		menubar = new MenuBar();
		this.setMenuBar(menubar);

		file = new Menu("File");
		addMenuItem (file, new MenuItem("Open"), "Open");
		addMenuItem (file, new MenuItem("Edit"), "Edit");
		addMenuItem (file, new MenuItem("Save"), "Save");
		addMenuItem (file, new MenuItem("Quit"), "Quit");
		menubar.add(file);
		
		player = new Menu("Player");

		Menu select = new Menu("Select");
		player.add (select);
		addMenuItem (select, new MenuItem("Player1"), "SelectPlayer1");
		addMenuItem (select, new MenuItem("Player2"), "SelectPlayer2");
		addMenuItem (player, new MenuItem("Add"), "Add");
		addMenuItem (player, new MenuItem("Delete"), "Delete");
		menubar.add(player);

		bomberman = new Menu("Bomberman");
		addMenuItem (bomberman, new MenuItem("Start"), "Start");
		addMenuItem (bomberman, new MenuItem("Stop"), "Stop");
		addMenuItem (bomberman, new MenuItem("Neustart"), "Neustart");
		addMenuItem (bomberman, new MenuItem("Level"), "Level");
		menubar.add(bomberman);

		help = new Menu("Help");
		addMenuItem (help, new MenuItem("About"), "About");
		menubar.add(help);
		menubar.setHelpMenu(help);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		Label level = new Label("Level:");
		Label player1 = new Label("Player1:");
		Label player2 = new Label("Player2:");
		Label p1livlab = new Label("Lives:");
		Label p2livlab = new Label("Lives:");
		Label p1scorlab = new Label("Score:");
		Label p2scorlab = new Label("Score:");
		leveltext = new TextArea("1", 1, 3, TextArea.SCROLLBARS_NONE);
		leveltext.setEditable(false);
		p1text = new TextArea(noplayer, 1, 10, TextArea.SCROLLBARS_NONE);
		p1text.setEditable(false);
		p2text = new TextArea(noplayer, 1, 10, TextArea.SCROLLBARS_NONE);
		p2text.setEditable(false);
		p1lives = new TextArea("", 1, 3, TextArea.SCROLLBARS_NONE);
		p1lives.setEditable(false);
		p2lives = new TextArea("", 1, 3, TextArea.SCROLLBARS_NONE);
		p2lives.setEditable(false);
		p1score = new TextArea("", 1, 3, TextArea.SCROLLBARS_NONE);
		p1score.setEditable(false);
		p2score = new TextArea("", 1, 3, TextArea.SCROLLBARS_NONE);
		p2score.setEditable(false);
		p.add(level);
		p.add(leveltext);
		p.add(player1);
		p.add(p1text);
		p.add(p1livlab);
		p.add(p1lives);
		p.add(p1scorlab);
		p.add(p1score);
		p.add(player2);
		p.add(p2text);
		p.add(p2livlab);
		p.add(p2lives);
		p.add(p2scorlab);
		p.add(p2score);

		this.add("Center",p);
		
		textarea = new TextArea("DRISS",2,40);
		textarea.setEditable(false);
		this.add("South",textarea);
		setdefaultLevel();
	}
	
	public void addMenuItem (Menu menu, MenuItem item, String command) {
		item.setActionCommand(command);
		item.addActionListener (this);
		menu.add(item);
	}

	public String selectSpieler (String s1, String s2){
		SpielerListe stemp=this.spielerliste;
		Object [] auswahl = new Object [this.spielerliste.count()];
		for (int i=0; i<this.spielerliste.count(); i++){
			auswahl[i]=stemp.getElement();		
			stemp=stemp.getNext();
		}
		return (String)JOptionPane.showInputDialog(this, s1, s2, JOptionPane.PLAIN_MESSAGE,null,auswahl,"");
	}
	
	public void actionPerformed( ActionEvent e ) {
		boolean inserted;
		String s;
		String cmd=e.getActionCommand();
	    	if (cmd.equals( "Open" )){
			s = (String)JOptionPane.showInputDialog(this, "Level einlesen:\n", JOptionPane.PLAIN_MESSAGE);
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				File file = new File(".\\"+s+"_spiellevel.xml");
				if (file.isFile()){
					playlevel=readlevels(file);	
					Levels.setEditMode(true);
					Levels.setplaylevel(playlevel);
					leveltext.setText(Integer.toString(playlevel.getLevel()));
					p1text.setText("Edit1");
					p2text.setText("Edit2");
					neustart=true;
				} else {
					JOptionPane.showMessageDialog(this, file.toString()+" existiert nicht!");
				}
			}
	    	} 
		else if (cmd.equals( "Edit" )){
	    	} 
		else if (cmd.equals( "Save" )){
			savelevel = Levels.getplaylevel();
			s = (String)JOptionPane.showInputDialog(this, "Level speichern:\n", JOptionPane.PLAIN_MESSAGE);
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				writeLevels(savelevel,".\\"+s+"_spiellevel.xml");
				Levels.setEditMode(false);
				neustart=true;
			}
	    	} 
		else if (cmd.equals( "Quit" )){
			textarea.setText("Quit found");
			saveScore();
			System.exit(0);
	    	} 
		else if (cmd.equals( "SelectPlayer1" )){
			textarea.setText("Select Player 1 found");
			s = selectSpieler( "Spieler 1 hinzufuegen:\n", "Spielerauswahl");
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				p1text.setText(s);
			}

	    	} 
		else if (cmd.equals( "SelectPlayer2" )){
			textarea.setText("Select Player 2 found");
			s = selectSpieler( "Spieler 2 hinzufuegen:\n", "Spielerauswahl");
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				p2text.setText(s);
			}
	    	} 
		else if (cmd.equals( "Add" )){
			s = (String)JOptionPane.showInputDialog(this, "Spieler hinzufuegen:\n", JOptionPane.PLAIN_MESSAGE);
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				inserted=this.spielerliste.insertSNode(s);
			}
		} 
		else if (cmd.equals( "Delete" )){
			textarea.setText("Delete found");
			s = selectSpieler("Spieler loeschen:\n", "Spieler loeschen");
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				if (p2text.getText().equals (s)) {p2text.setText(noplayer);}
				if (p1text.getText().equals (s)) {p1text.setText(noplayer);}
				this.spielerliste=this.spielerliste.deleteSNode(s);
				}
			}
		else if (cmd.equals( "Start" )){
		    	FileDialog chooser = new FileDialog(this, "Datei (.pnt) auswaehlen", FileDialog.LOAD);
        		chooser.setVisible(true);
        		choosefname = chooser.getFile();
        		if (choosefname != null) {
				File f1 = new File(chooser.getDirectory()+ "P1_L" +leveltext.getText()+ "_" + p1text.getText() + ".pnt");
            			score=readScore(f1);
				p1text.setText(score.getspielerName());
				inserted=this.spielerliste.insertSNode(score.getspielerName());
				File f2 = new File(chooser.getDirectory()+ "P2_L" +leveltext.getText()+ "_" + p2text.getText() + ".pnt");
            			score=readScore(f2);
				p2text.setText(score.getspielerName());
				inserted=this.spielerliste.insertSNode(score.getspielerName());
			}
		} 
		else if (cmd.equals( "Stop" )){
		    	FileDialog chooser = new FileDialog(this, "Verzeichnis auswaehlen (.pnt)", FileDialog.SAVE);
        		chooser.setVisible(true);
        		choosefname = chooser.getFile();
        		if (choosefname != null) {
				score.setLevel(Integer.parseInt(leveltext.getText()));
				score.setspielerNummer(1);
				score.setspielerScore(Integer.parseInt(p1score.getText()));
				score.setspielerName(p1text.getText());
            			writeScore(score,chooser.getDirectory()+ "P1_L" +leveltext.getText()+ "_" + p1text.getText() + ".pnt");
				score.setLevel(Integer.parseInt(leveltext.getText()));
				score.setspielerNummer(2);
				score.setspielerScore(Integer.parseInt(p2score.getText()));
				score.setspielerName(p2text.getText());
            			writeScore(score, chooser.getDirectory()+ "P2_L" +leveltext.getText()+ "_" + p2text.getText() + ".pnt");
        		}
		}
		else if (cmd.equals( "Neustart" )){
			textarea.setText("Neustart found");
			s=leveltext.getText();
			File file = new File(".\\"+s+"_spiellevel.xml");
			playlevel=readlevels(file);	
			Levels.setplaylevel(playlevel);
			neustart=true;
		} 
		else if (cmd.equals( "Level" )){
			s = (String)JOptionPane.showInputDialog(this, "Level einlesen:\n", JOptionPane.PLAIN_MESSAGE);
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				File file = new File(".\\"+s+"_spiellevel.xml");
				if (file.isFile()){
					playlevel=readlevels(file);	
					Levels.setplaylevel(playlevel);
					leveltext.setText(Integer.toString(playlevel.getLevel()));
					neustart=true;
					Levels.setEditMode(false);
				} else {
					JOptionPane.showMessageDialog(this, file.toString()+" existiert nicht!");
				}
			}
		} 
		else if (cmd.equals( "About" )){
		textarea.setText("About found");
		}
	    	else {
		System.out.println( "default" );
	    	}
  	}
	
	public void playerstatus (Grundflaeche spielfeld, Spieler spieler, String sname){
		if (sname.equals(noplayer)) {
			spieler.deactivate(spielfeld);
		} else if (spieler.alive()){
			spieler.active=true;
		}
	}

	public void setdefaultLevel (){
		int anzahlleben = 3;
		int level = 1;
		double dx=0.1;
		double dy=0.1;
		int nx=11;
		int ny=11;
			
	/* initialposition legt die Anfangsbedingung fest, wird aber auch im Laufe des Spiels immer wieder genutzt um festzustellen welche Art von
	 * Elementen auf der jeweiligen Position liegen. Wenn ein "loser Mauerstein" durch eine Bombe zerst�rt wird, wird die Position durch 
	 * "Keinemauer" ersetzt ... Spieler k�nnen nur auf Positionen gehen, die "Keinemauer" beinhalten.
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
			};
		defaultlevel.setLevel(level);
		defaultlevel.setAnzahlleben(anzahlleben);
		defaultlevel.setNx(nx);
		defaultlevel.setNy(ny);
		defaultlevel.setDx(dx);
		defaultlevel.setDy(dy);
		defaultlevel.setInitialposition(initialposition);
		Levels.setplaylevel(defaultlevel);
		writeLevels(defaultlevel, ".\\0_spiellevel.xml");
	}


	public static void writeScore(Score score, String filename){

		try {
 
		File file = new File(filename);
		JAXBContext jaxbContext = JAXBContext.newInstance(Score.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(score, file);
//		jaxbMarshaller.marshal(level, System.out);
 
	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
 
	}

	public static Score readScore(File file){

	  Score score=new Score();
	  try {
 
			JAXBContext jaxbContext = JAXBContext.newInstance(Score.class);
 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	  		score= (Score) jaxbUnmarshaller.unmarshal(file);
 
	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
 
	  return score;
	}

	public static void writeLevels(Levels level, String filename){

		try {
 
		File file = new File(filename);
		JAXBContext jaxbContext = JAXBContext.newInstance(Levels.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(level, file);
//		jaxbMarshaller.marshal(level, System.out);
 
	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
 
	}

	public static Levels readlevels(File file){

	  Levels level=new Levels();
	  try {
 
			JAXBContext jaxbContext = JAXBContext.newInstance(Levels.class);
 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	  		level= (Levels) jaxbUnmarshaller.unmarshal(file);
 
	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
 
	  return level;
	}

	public void saveScore(){
		    	FileDialog chooser = new FileDialog(this, "Verzeichnis auswaehlen (.pnt)", FileDialog.SAVE);
        		chooser.setVisible(true);
        		choosefname = chooser.getFile();
        		if (choosefname != null) {
				score.setLevel(Integer.parseInt(leveltext.getText()));
				score.setspielerNummer(1);
				score.setspielerScore(Integer.parseInt(p1score.getText()));
				score.setspielerName(p1text.getText());
            			writeScore(score,chooser.getDirectory()+ "P1_L" +leveltext.getText()+ "_" + p1text.getText() + ".pnt");
				score.setLevel(Integer.parseInt(leveltext.getText()));
				score.setspielerNummer(2);
				score.setspielerScore(Integer.parseInt(p2score.getText()));
				score.setspielerName(p2text.getText());
            			writeScore(score, chooser.getDirectory()+ "P2_L"  +leveltext.getText()+ "_"+ p2text.getText() + ".pnt");
        		}
	}


	class myWindowListener extends WindowAdapter {
	    public void windowClosing(WindowEvent e){
	      	e.getWindow().dispose();                   // Fenster "killen"
      		System.exit(0);                            // VM "killen" 
    		}           
  	}

	public static void main(String[] args){
		JFrame f = new BombermanMenu("Bomberman Menue");
		f.setSize(300,300);

		f.pack();
		f.setVisible(true);
	}
}
