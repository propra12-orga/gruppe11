public class SpielerListe {
	private String el;
	private boolean initial;
	private SpielerListe next;
	public SpielerListe(String val, SpielerListe n) {
		el=val; next=n; initial=true; 
	}
	public SpielerListe() {
		next=null; initial=true;
	}
	public void setElement(String val) { el=val; }
	public String getElement() { return el; }
	public void setNext(SpielerListe n) { next=n; }
	public SpielerListe getNext() { return next; }

	public int count(){
		SpielerListe n=this, follow=null; 
		int num=0;
		while (n!=null) {
		  follow=n;
		  n=n.getNext();
		  num++;
		}
		return num;
	}

	public boolean insertSNode(String c) {
		SpielerListe n=this, follow=null;
		while (n!=null) {
		  follow=n;
		  n=n.getNext();
		}
		if (this.initial == true) {
			this.initial=false;
			this.el=c;
			this.next=null;}
		else {
	 		follow.next = new SpielerListe (c,null);
		}
		return true;
       	}

	public SpielerListe deleteSNode(String x) {
		SpielerListe head=this;
		SpielerListe node=this;
		SpielerListe previous=this;
		while(node!=null){
		
			if (node.getElement().equals(x)) {
			    if (node == head) {head=node.getNext();previous=head;node=head;head.initial=false;}
			    else if (node.getNext()==null){ previous.setNext(null);node=node.getNext(); }
			    else {previous.setNext(node.getNext()); node=node.getNext();}
			} 
			else {previous = node; node=node.getNext();}
		}
		System.out.println("head = " + head.getElement() + ", count = " + head.count());
		return head;
	}

	public static void main(String[] args) {
		SpielerListe head, node, tail, einfuegen, previous, current = new SpielerListe("NULL",null);
		String v;
		String x, y;
		
		/* Einfuegen am Anfang */
		head = new SpielerListe("Eins",null);
		node = new SpielerListe("Zwei",head); head=node;
		node = new SpielerListe("Drei",head); head=node;
		einfuegen = new SpielerListe("Dreiunddreissig",null);
		
		/* Ausgabe und Einfuegen am Ende */
		node=head;
		while(node!=null) { /* Ausgabe der Liste */
			v = node.getElement();
			current = node;
			System.out.println("Element="+v);
			node=node.getNext();
		}
		System.out.println("------------------------------");
		
		current.setNext(einfuegen);
		
		/*   Setzen eine Elements (hier 2. Element der Liste) */
		node=head;
		node=node.getNext(); 
		node.setElement("Zwolf");
		
		/*  Einfuegen in der Mitte - hinter Element mit Inhalt 12 */
		x = "Zwolf";
		einfuegen = new SpielerListe("55",null);
		
		node=head;
		while(node!=null){
		
			if (node.getElement().equals( x)) {
				current = node;
				einfuegen.setNext(current.getNext());
				current.setNext(einfuegen);
			}
			node=node.getNext();
		}
			   /* Ausgabe */
		node=head;
		while(node!=null) { /* Ausgabe der Liste */
			v = node.getElement();
			System.out.println("Element="+v);
			node=node.getNext();
		}
		System.out.println("------------------------------");		
	
		/*  Loeschen Element 12 */

		x = "Zwolf";
		node=head;
		previous=head;
		while(node!=null){
		
			if (node.getElement().equals(x)) {
			    if (node == head) {head=node.getNext();}
				else {previous.setNext(node.getNext());}
			} 
			previous = node;
			node=node.getNext();
		}
		
		   /* Ausgabe */
		node=head;
		while(node!=null) { /* Ausgabe der Liste */
			v = node.getElement();
			System.out.println("Element="+v);
			node=node.getNext();
		}
		System.out.println("------------------------------");		
	}
}
