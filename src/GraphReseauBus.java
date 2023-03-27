import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphReseauBus {
	
    private Map<String, List<LocalTime>> infoReseauLigne1;
    private Map<String, List<LocalTime>> infoReseauLigne4;
    private Set<String> Stations1;
    private Set<String> Stations4;
    
    private ArrayList<Node> nodesGraph;
    private ArrayList<Arcs> ArcsGraph;
	
	public GraphReseauBus(Map<String, List<LocalTime>> dataSet1,Map<String, List<LocalTime>> dataSet2) {
		this.setInfoReseauLigne1(dataSet1);
		this.setInfoReseauLigne4(dataSet2);
		this.Stations1 = this.getInfoReseauLigne1().keySet();
		this.Stations4 = this.getInfoReseauLigne4().keySet();

		this.nodesGraph = new ArrayList<Node>();
	    this.ArcsGraph= new ArrayList<Arcs> ();
	    
		this.generateGraph();
		//System.out.println(this.ArcsGraph);
		//System.out.println(this.getNodesGraph());
		/*
		 
		for(int i =0 ; i<this.getNodesGraph().size();i++) {
			System.out.println(this.getNodesGraph().get(i)+" ces rvoisins are : "+this.getNodesGraph().get(i).getVoisins());
		}
		*/
		//DisplayGraph dg = new DisplayGraph(this.getArcsGraph());

	}
	
	public Set<String> getStationL1(){return this.Stations1;}
	public Set<String> getStationL4(){return this.Stations4;}
	public void setInfoReseauLigne1(Map<String, List<LocalTime>> infoReseauLigne1) {this.infoReseauLigne1 = infoReseauLigne1;}
	public void setInfoReseauLigne4(Map<String, List<LocalTime>> infoReseauLigne4) {this.infoReseauLigne4 = infoReseauLigne4;}
	public Map<String, List<LocalTime>> getInfoReseauLigne1() {return infoReseauLigne1;}
	public Map<String, List<LocalTime>> getInfoReseauLigne4() {return infoReseauLigne4;}	
	public ArrayList<Node> getNodesGraph() {return nodesGraph;}
	public ArrayList<Arcs> getArcsGraph() {return ArcsGraph;}
	
	public void addArcGraph(Arcs arcs) {this.getArcsGraph().add(arcs); }
	public void addNodesGraph(Node node) {this.getNodesGraph().add(node);}
	
	public void generateGraph() {		
		this.generateNodes(this.Stations1);
		this.generateNodes(this.Stations4);
		this.generateArcs(this.Stations1);
		this.generateArcs(this.Stations4);
		this.addVoisinsNodes();
	}
	
	public void generateNodes(Set<String> list) {
		String[] listArray =   list.toArray(new String[0]);
		for(String elem:listArray) {
			Node node = new Node(elem);
			boolean existDeja= false;
			for(int j =0;j<this.getNodesGraph().size();j++){
				if(elem.equals(this.getNodesGraph().get(j).getName())) {
					existDeja = true;
					break;
				}
			}
			if(!existDeja) {
				this.addNodesGraph(node);
			}
		}
	}
	
	public void generateArcs(Set<String> list) {
		for (int i =0;i<list.size();i++) {
			try {
				Node father = new Node (list.toArray(new String[0])[i]);
				Node son = new Node(list.toArray(new String[0])[i+1]);
				Arcs arc = new Arcs (father,son);
				this.addArcGraph(arc);
			}catch (Exception e){
				continue;
			}
		}
	}
	
	public void addVoisinsNodes() {
		for (int i =0 ; i<this.getNodesGraph().size();i++) {
			Node node = this.getNodesGraph().get(i);
			for (int j =0 ; j<this.getArcsGraph().size();j++) {
				if (node.getName().equals(this.getArcsGraph().get(j).getSon().getName())) {
					for(int k =0 ; k<this.getNodesGraph().size();k++) {
						if (this.getNodesGraph().get(k).getName().equals(this.getArcsGraph().get(j).getFather().getName())) {
							this.getNodesGraph().get(i).addNodeVoisin(this.getNodesGraph().get(k));
					
						}
					}
				}
			}
		}
		for (int i =0 ; i<this.getNodesGraph().size();i++) {
			Node node = this.getNodesGraph().get(i);
			for (int j =0 ; j<this.getArcsGraph().size();j++) {
				if (node.getName().equals(this.getArcsGraph().get(j).getFather().getName())) {
					for(int k =0 ; k<this.getNodesGraph().size();k++) {
						if (this.getNodesGraph().get(k).getName().equals(this.getArcsGraph().get(j).getSon().getName())) {
							this.getNodesGraph().get(i).addNodeVoisin(this.getNodesGraph().get(k));
					
						}
					}
				}
			}
		}
	}
}