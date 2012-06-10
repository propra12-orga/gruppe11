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
		SpielerListe head=new SpielerListe();
		SpielerListe node, tail, einfuegen, previous;
		SpielerListe current=new SpielerListe();
		String v;
		String x, y;
		boolean inserted;
		
		/* Einfuegen am Anfang */
		inserted=head.insertSNode("1");
		inserted=head.insertSNode("2");
		inserted=head.insertSNode("3");
		inserted=head.insertSNode("4");
		inserted=head.insertSNode("5");
		/* Ausgabe */
		node=head;
		while(node!=null) { /* Ausgabe der Liste */
			v = node.getElement();
			current = node;
			System.out.println("Element="+v);
			node=node.getNext();
		}
		System.out.println(" Anzahl = " + head.count());
		
		/* Löschen und Einfügen */
		head=head.deleteSNode("1");
		System.out.println(" Anzahl = " + head.count());
		inserted=head.insertSNode("6");
		System.out.println(" Anzahl = " + head.count());

		head=head.deleteSNode("3");
		System.out.println(" Anzahl = " + head.count());
		inserted=head.insertSNode("7");
		System.out.println(" Anzahl = " + head.count());
		
		head=head.deleteSNode("7");
		System.out.println(" Anzahl = " + head.count());
		inserted=head.insertSNode("8");
		System.out.println(" Anzahl = " + head.count());

		/* Ausgabe */
		node=head;
		while(node!=null) { /* Ausgabe der Liste */
			v = node.getElement();
			current = node;
			System.out.println("Element="+v);
			node=node.getNext();
		}
		System.out.println(" Anzahl = " + head.count());
	}
}
