
public class Arcs {

	private Node father;
	private Node son;
	public Arcs(Node father,Node son) {
		this.setFather(father);
		this.setSon(son);
	}
	public Node getFather() {return father;}
	public Node getSon() {return son;}
	public void setFather(Node father) {this.father = father;}
	public void setSon(Node son) {this.son = son;}
	
	public String toString () {
		String text = this.getFather().getName()+"-->"+this.getSon().getName();
		return text; 
	}
}
