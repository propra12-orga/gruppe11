import java.awt.*;
import java.awt.Component.*;
import java.awt.event.*;
import javax.swing.*;


public class BombermanMenu extends JFrame implements ActionListener {
	SpielerListe spielerliste = new SpielerListe();
	MenuBar menubar;
	Menu file; 
	Menu help;
	Menu player;
	Menu bomberman;
	
	Panel p;
	Label level, player1, player2;
	TextArea textarea, leveltext, p1text, p2text;
	
	public BombermanMenu(String title) {
		super(title);
		this.setLayout(new BorderLayout(15,15));
		addWindowListener(new myWindowListener());
		
		menubar = new MenuBar();
		this.setMenuBar(menubar);

		file = new Menu("File");
		addMenuItem (file, new MenuItem("Open"), "Open");
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
		leveltext = new TextArea("1", 1, 3, TextArea.SCROLLBARS_NONE);
		leveltext.setEditable(false);
		p1text = new TextArea("----------", 1, 10, TextArea.SCROLLBARS_NONE);
		p1text.setEditable(false);
		p2text = new TextArea("----------", 1, 10, TextArea.SCROLLBARS_NONE);
		p2text.setEditable(false);
		p.add(level);
		p.add(leveltext);
		p.add(player1);
		p.add(p1text);
		p.add(player2);
		p.add(p2text);

		this.add("Center",p);
		
		textarea = new TextArea("DRISS",2,40);
		textarea.setEditable(false);
		this.add("South",textarea);
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
		textarea.setText("Open found");
	    	} 
		else if (cmd.equals( "Quit" )){
		textarea.setText("Quit found");
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
				System.out.println(this.spielerliste.count());
			}
		} 
		else if (cmd.equals( "Delete" )){
			textarea.setText("Delete found");
			s = selectSpieler("Spieler loeschen:\n", "Spieler loeschen");
			textarea.setText(s);
			if ((s != null) && (s.length() > 0)) {
				if (p2text.getText().equals (s)) {p2text.setText("----------");}
				if (p1text.getText().equals (s)) {p1text.setText("----------");}
				this.spielerliste=this.spielerliste.deleteSNode(s);
				}
			}
		else if (cmd.equals( "Start" )){
		textarea.setText("Start found");
		} 
		else if (cmd.equals( "Stop" )){
		textarea.setText("Stop found");
		}
		else if (cmd.equals( "Neustart" )){
		textarea.setText("Neustart found");
		} 
		else if (cmd.equals( "Level" )){
		textarea.setText("Level found");
		} 
		else if (cmd.equals( "About" )){
		textarea.setText("About found");
		}
	    	else {
		System.out.println( "default" );
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
