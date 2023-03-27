import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private String name;
	private List<Node> voisins;
	
	public Node (String name) {
		this.setName(name);	
		this.voisins = new ArrayList<Node> ();
	}
	
	
	public String getName() {return name;}
	public List<Node> getVoisins() {return voisins;	}
	public void setName(String name) {this.name = name;}
	
	public void addNodeVoisin (Node node) {
		this.getVoisins().add(node);
	}
	
	public String toString() {
		String text = this.getName();
		return text;
	}
}
 