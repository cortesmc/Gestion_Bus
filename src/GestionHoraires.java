import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.time.LocalTime;
import java.time.Duration;

public class GestionHoraires {

	private Map<String, List<LocalTime>> regularDateGoLigne1;
	private Map<String, List<LocalTime>> regularDateBackLigne1;
    private Map<String, List<LocalTime>> weHolidaysDateGoLigne1;
    private Map<String, List<LocalTime>> weHolidaysDateBackLigne1;
    
    private Map<String, List<LocalTime>> regularDateGoLigne4;
	private Map<String, List<LocalTime>> regularDateBackLigne4;
    private Map<String, List<LocalTime>> weHolidaysDateGoLigne4;
    private Map<String, List<LocalTime>> weHolidaysDateBackLigne4;
    
    private GraphReseauBus graphBus;
    
    
    
    public GestionHoraires () {
    	GetData dataMan = new GetData();
    	ArrayList<Map<String, List<LocalTime>>> tmp1 = dataMan.getInformationTxt(1);
    	ArrayList<Map<String, List<LocalTime>>> tmp4= dataMan.getInformationTxt(2);
    	
    	this.setRegularDateGoLigne1(tmp1.get(0));
    	this.setRegularDateBackLigne1(tmp1.get(1));
    	this.setWeHolidaysDateGoLigne1(tmp1.get(2));
    	this.setWeHolidaysDateBackLigne1(tmp1.get(3));
    	
    	this.setRegularDateGoLigne4(tmp4.get(0));
    	this.setRegularDateBackLigne4(tmp4.get(1));
    	this.setWeHolidaysDateGoLigne4(tmp4.get(2));
    	this.setWeHolidaysDateBackLigne4(tmp4.get(3));
    	
    	graphBus =new GraphReseauBus(this.getRegularDateGoLigne1(),this.getRegularDateGoLigne4());
     	//System.out.println(this.getRegularDateGoLigne1());
	}

	

    //Setter and Getters 
	public void setRegularDateGoLigne1(Map<String, List<LocalTime>> regularDateGo) {this.regularDateGoLigne1 = regularDateGo;}
	public void setRegularDateBackLigne1(Map<String, List<LocalTime>> regularDateBack) {this.regularDateBackLigne1 = regularDateBack;}
	public void setWeHolidaysDateGoLigne1(Map<String, List<LocalTime>> weHolidaysDateGo) {this.weHolidaysDateGoLigne1 = weHolidaysDateGo;}
	public void setWeHolidaysDateBackLigne1(Map<String, List<LocalTime>> weHolidaysDateBack) {this.weHolidaysDateBackLigne1 = weHolidaysDateBack;}
	
	public void setRegularDateGoLigne4(Map<String, List<LocalTime>> regularDateGo) {this.regularDateGoLigne4 = regularDateGo;}
	public void setRegularDateBackLigne4(Map<String, List<LocalTime>> regularDateBack) {this.regularDateBackLigne4 = regularDateBack;}
	public void setWeHolidaysDateGoLigne4(Map<String, List<LocalTime>> weHolidaysDateGo) {this.weHolidaysDateGoLigne4 = weHolidaysDateGo;}
	public void setWeHolidaysDateBackLigne4(Map<String, List<LocalTime>> weHolidaysDateBack) {this.weHolidaysDateBackLigne4 = weHolidaysDateBack;}

	public Map<String, List<LocalTime>> getRegularDateGoLigne1() {return regularDateGoLigne1;}
	public Map<String, List<LocalTime>> getRegularDateBackLigne1() {return regularDateBackLigne1;}
	public Map<String, List<LocalTime>> getWeHolidaysDateGoLigne1() {return weHolidaysDateGoLigne1;}
	public Map<String, List<LocalTime>> getWeHolidaysDateBackLigne1() {return weHolidaysDateBackLigne1;}
	
	public Map<String, List<LocalTime>> getRegularDateGoLigne4() {return regularDateGoLigne4;}
	public Map<String, List<LocalTime>> getRegularDateBackLigne4() {return regularDateBackLigne4;}
	public Map<String, List<LocalTime>> getWeHolidaysDateGoLigne4() {return weHolidaysDateGoLigne4;}
	public Map<String, List<LocalTime>> getWeHolidaysDateBackLigne4() {return weHolidaysDateBackLigne4;}

	//Methods
	public void getShortestPath(LocalTime startTime,String start, String finish,boolean Holiday) {
		List<String> paths = this.shortestPath(start,finish);
		System.out.println("The shortest path between "+start+" and "+finish+":\n");
		LocalTime shortestPathTime = this.getTimeArrive(startTime, paths, false);
		System.out.println("\nWith a travel time: "+Duration.between(startTime, shortestPathTime).toMinutes()+" minutes.");
		System.out.println("\nThe arrival hour is: "+shortestPathTime);
	}
	
	public void getFastestPath(LocalTime startTime,String start, String finish,boolean Holiday) {
		List<List<String>> paths = this.findPaths(start, finish);
		System.out.println("The fastest path between "+start+" and "+finish+":\n");
		LocalTime fastestPathTime = this.getOnlyTimeArrive(startTime, paths.get(0), false);
		List<String> fastestPath = paths.get(0);
		for (List<String> path:paths) {
			LocalTime fastestPathTime2 = this.getOnlyTimeArrive(startTime, path, false);
			if ((Duration.between(startTime, fastestPathTime2).toMinutes())<(Duration.between(startTime, fastestPathTime2).toMinutes())) {
				fastestPathTime = fastestPathTime2;
				fastestPath = path;	
			}
		}
		
		fastestPathTime = this.getTimeArrive(startTime, fastestPath, false);
		System.out.println("\nWith a travel time: "+Duration.between(startTime, fastestPathTime).toMinutes()+" minutes.");
		System.out.println("\nThe arrival hour is: "+fastestPathTime);
	}
	
	public void getForemostPath(LocalTime startTime,String start, String finish,boolean Holiday) {
		List<List<String>> paths = this.findPaths(start,finish);
		System.out.println("The foremost path between "+start+" and "+finish+":\n");
		LocalTime foremostPathTime = this.getOnlyTimeArrive(startTime, paths.get(0), false);
		List<String> foremostPath = paths.get(0);
		for (List<String> path:paths) {
			LocalTime foremostPathTime2 = this.getOnlyTimeArrive(startTime, path, false);
			if ((foremostPathTime.compareTo(foremostPathTime2))>0) {
				foremostPathTime = foremostPathTime2;
				foremostPath = path;	
			}
		}
				
		foremostPathTime = this.getTimeArrive(startTime, foremostPath, false);
		System.out.println("\nWith a travel time: "+Duration.between(startTime, foremostPathTime).toMinutes()+" minutes.");
		System.out.println("\nThe arrival hour is: "+foremostPathTime);
	}
	
	
	public ArrayList<String> shortestPath(String start, String finish) {
	    ArrayList<Node> nodes = this.graphBus.getNodesGraph();
	    
	    Map<Node, Integer> distance = new HashMap<Node, Integer>();
	    Map<Node, Node> previous = new HashMap<Node, Node>();
	    Set<Node> visited = new HashSet<Node>();
	    Queue<Node> queue = new LinkedList<Node>();

	    Node startNode = null;
	    Node finishNode = null;

	    for (Node node : nodes) {
	        distance.put(node, Integer.MAX_VALUE);
	        previous.put(node, null);
	        if (node.getName().equals(start)) {
	            distance.put(node, 0);
	            startNode = node;
	        }
	        if (node.getName().equals(finish)) {
	            finishNode = node;
	        }
	    }

	    if (startNode == null || finishNode == null) {
	        return null; 
	    }

	    queue.add(startNode);

	    while (!queue.isEmpty()) {
	        Node current = queue.poll();
	        
	        if (visited.contains(current)) {
	            continue;
	        }

	        visited.add(current);

	        for (Node neighbor : current.getVoisins()) {
	            int alt = distance.get(current) +1;
	            Integer neighborDist = distance.get(neighbor);
	            if (neighborDist == null || alt < neighborDist.intValue()) {		          
	                distance.put(neighbor, alt);
	                previous.put(neighbor, current);
	                queue.add(neighbor);
	            }
	        }
	    }

	    ArrayList<String> path = new ArrayList<String>();
	    Node current = finishNode;
	    while (current != null) {
	        path.add(0, current.getName());
	        current = previous.get(current);
	    }

	    return path;
	}

	public  List<List<String>> findPaths(String start, String finish) {
		ArrayList<Node> nodes = this.graphBus.getNodesGraph();
        List<List<String>> paths = new ArrayList<>();
        Map<Node, Boolean> visited = new HashMap<>();
        List<String> path = new ArrayList<>();
        
        Node startNode = null;
	    Node finishNode = null;
	    
        for (Node node : nodes) {
	        if (node.getName().equals(start)) {
	            startNode = node;
	        }
	        if (node.getName().equals(finish)) {
	            finishNode = node;
	        }
	    }
        
        path.add(startNode.getName());
        dfs(startNode, finishNode, visited, paths, path);
        return paths;
    }

    private void dfs(Node current, Node finish, Map<Node, Boolean> visited, List<List<String>> paths, List<String> path) {
        visited.put(current, true);

        if (current == finish) {
            paths.add(new ArrayList<>(path));
        } else {
            for (Node neighbor : current.getVoisins()) {
                if (!visited.containsKey(neighbor) || !visited.get(neighbor)) {
                    path.add(neighbor.getName());
                    dfs(neighbor, finish, visited, paths, path);
                    path.remove(path.size() - 1);
                }
            }
        }

        visited.put(current, false);
    }
    
    public List<List<String>> SeparatePath(List<String> pathInitial) {
    	List<List<String>> pathsSep =  new ArrayList<List<String>>();
    	List<String> pathunit =  new ArrayList<String>();
    	ArrayList<String> intersection = this.getIntersection();
    	
    	for (String station : pathInitial) {
    		pathunit.add(station);
    		if (intersection.contains(station)) {
    			@SuppressWarnings("unchecked")
				ArrayList<String> pathunitcopy = (ArrayList<String>) ((ArrayList<String>) pathunit).clone(); ;
    			pathsSep.add(pathunitcopy);
    			pathunit.clear();
    			pathunit.add(station);
    		} 
    	}
		pathsSep.add(pathunit);
    	return pathsSep;
    }
    
    public LocalTime getTimeArrive(LocalTime startTime,List<String> path,boolean Holiday) {
		List<List<String>> pathsSep = this.SeparatePath(path);
		LocalTime tempArrive = startTime;
		for (List<String> listPath : pathsSep) { 
			if(listPath.size()<2) {
				continue;
			}
			tempArrive = this.getTimeSimple(tempArrive,listPath, Holiday);
		}
		return tempArrive;
	}
    
    public LocalTime getTimeSimple(LocalTime startTime,List<String> path,boolean Holiday) {
    	Set<String> StationsL1 = this.graphBus.getStationL1();
    	Set<String> StationsL4 = this.graphBus.getStationL4();
    	
    	Map<String, List<LocalTime>> DateLigne1GO = this.getRegularDateGoLigne1();
    	Map<String, List<LocalTime>> DateLigne1Back = this.getRegularDateBackLigne1();
    	Map<String, List<LocalTime>> DateLigne4GO = this.getRegularDateGoLigne4();
    	Map<String, List<LocalTime>> DateLigne4Back = this.getRegularDateBackLigne4();
    	
    	String[] keySet1 =   DateLigne1GO.keySet().toArray(new String[0]);
    	String[] keySet4 =   DateLigne4GO.keySet().toArray(new String[0]);
    	
    	String firstStation = path.get(0);
		String lastStation = path.get(path.size()-1);
		
    	if (Holiday) {
    		DateLigne1GO = this.getWeHolidaysDateGoLigne1();
    		DateLigne1Back = this.getWeHolidaysDateBackLigne1();
        	DateLigne4GO = this.getWeHolidaysDateGoLigne4();
        	DateLigne4Back = this.getWeHolidaysDateBackLigne4();
    	}
    	if(StationsL1.contains(firstStation) && StationsL1.contains(lastStation)) {
    		for(String elemStation:keySet1) {
    			if(elemStation.equals(firstStation)) {
	    			System.out.println("Take the line 1 direction: "+keySet1[0]);
	    			break;
    			} else if (elemStation.equals(lastStation)) {
    				System.out.println("Take the line 1 direction: "+keySet1[keySet1.length-1]);
	    			break;
    			}
    		}
    	} else {
    		for(String elemStation:keySet4) {
    			if(elemStation.equals(firstStation)) {
	    			System.out.println("Take the line 4 direction: "+keySet4[0]);
	    			break;
    			} else if (elemStation.equals(lastStation)) {
    				System.out.println("Take the line 4 direction: "+keySet4[keySet4.length-1]);
	    			break;
    			}
    		}
    	}
    	LocalTime finishTime = startTime;
    	for (int i = 0;i<path.size();i++) {
    		String currentStation = path.get(i);
    		if(StationsL1.contains(firstStation) && StationsL1.contains(lastStation)) {
    			for (String elem:keySet1) {
    				if (elem.equals(firstStation)) {
    					//Go
    					finishTime = this.calculTimeToNextStation(DateLigne1GO,currentStation,finishTime);
    					break;
    				} else if (elem.equals(lastStation)) {
    					//Back
    					finishTime = this.calculTimeToNextStation(DateLigne1Back,currentStation,finishTime);
    					break;
    				}
    			}
    		}else {
    			for (String elem:keySet4) {
    				if (elem.equals(firstStation)) {
    					//Go
    					finishTime = this.calculTimeToNextStation(DateLigne4GO,currentStation,finishTime);
    					break;
    				} else if (elem.equals(lastStation)) {
    					//Back
    					finishTime = this.calculTimeToNextStation(DateLigne4Back,currentStation,finishTime);
    					break;
    				}
    			}
    			
    		}
    	}
    	return finishTime;
    }
    
    @SuppressWarnings("unchecked")
	private LocalTime calculTimeToNextStation(Map<String, List<LocalTime>> dataLigne,String currentStation,LocalTime currentTime) {
    	LocalTime time = currentTime;
    	boolean entry = false;
    	String key = "";
    	for(Map.Entry mapentry : dataLigne.entrySet()) {
    		if (mapentry.getKey().equals(currentStation) || entry) {
    			key = (String) mapentry.getKey();
    			entry = true;
    			break;
    		}
    	}
		for(LocalTime time1:(ArrayList<LocalTime>)dataLigne.get(key)) {
			int value = time1.compareTo(time);
			if (value>=0) {
				time = time1;
				break;
			}
		}
		System.out.println(" - "+key+" :"+time);
		return time;
	}
    
    public LocalTime getOnlyTimeArrive(LocalTime startTime,List<String> path,boolean Holiday) {
		List<List<String>> pathsSep = this.SeparatePath(path);
		LocalTime tempArrive = startTime;
		for (List<String> listPath : pathsSep) { 
			if(listPath.size()<2) {
				continue;
			}
			tempArrive = this.getOnlyTimeSimple(tempArrive,listPath, Holiday);
		}
		return tempArrive;
	}
    
    public LocalTime getOnlyTimeSimple(LocalTime startTime,List<String> path,boolean Holiday) {
    	Set<String> StationsL1 = this.graphBus.getStationL1();
    	Set<String> StationsL4 = this.graphBus.getStationL4();
    	
    	Map<String, List<LocalTime>> DateLigne1GO = this.getRegularDateGoLigne1();
    	Map<String, List<LocalTime>> DateLigne1Back = this.getRegularDateBackLigne1();
    	Map<String, List<LocalTime>> DateLigne4GO = this.getRegularDateGoLigne4();
    	Map<String, List<LocalTime>> DateLigne4Back = this.getRegularDateBackLigne4();
    	
    	String[] keySet1 =   DateLigne1GO.keySet().toArray(new String[0]);
    	String[] keySet4 =   DateLigne4GO.keySet().toArray(new String[0]);
    	
    	String firstStation = path.get(0);
		String lastStation = path.get(path.size()-1);
		
    	if (Holiday) {
    		DateLigne1GO = this.getWeHolidaysDateGoLigne1();
    		DateLigne1Back = this.getWeHolidaysDateBackLigne1();
        	DateLigne4GO = this.getWeHolidaysDateGoLigne4();
        	DateLigne4Back = this.getWeHolidaysDateBackLigne4();
    	}

    	LocalTime finishTime = startTime;
    	for (int i = 0;i<path.size();i++) {
    		String currentStation = path.get(i);
    		if(StationsL1.contains(firstStation) && StationsL1.contains(lastStation)) {
    			for (String elem:keySet1) {
    				if (elem.equals(firstStation)) {
    					//Go
    					finishTime = this.calculOnlyTimeToNextStation(DateLigne1GO,currentStation,finishTime);
    					break;
    				} else if (elem.equals(lastStation)) {
    					//Back
    					finishTime = this.calculOnlyTimeToNextStation(DateLigne1Back,currentStation,finishTime);
    					break;
    				}
    			}
    		}else {
    			for (String elem:keySet4) {
    				if (elem.equals(firstStation)) {
    					//Go
    					finishTime = this.calculOnlyTimeToNextStation(DateLigne4GO,currentStation,finishTime);
    					break;
    				} else if (elem.equals(lastStation)) {
    					//Back
    					finishTime = this.calculOnlyTimeToNextStation(DateLigne4Back,currentStation,finishTime);
    					break;
    				}
    			}
    			
    		}
    	}
    	return finishTime;
    }
    
    @SuppressWarnings("unchecked")
	private LocalTime calculOnlyTimeToNextStation(Map<String, List<LocalTime>> dataLigne,String currentStation,LocalTime currentTime) {
    	LocalTime time = currentTime;
    	boolean entry = false;
    	String key = "";
    	for(Map.Entry mapentry : dataLigne.entrySet()) {
    		if (mapentry.getKey().equals(currentStation) || entry) {
    			key = (String) mapentry.getKey();
    			entry = true;
    			break;
    		}
    	}
		for(LocalTime time1:(ArrayList<LocalTime>)dataLigne.get(key)) {
			int value = time1.compareTo(time);
			if (value>=0) {
				time = time1;
				break;
			}
		}
		return time;
	}

	public ArrayList<String> getIntersection(){
		ArrayList<Node> nodes = this.graphBus.getNodesGraph();
    	ArrayList<String> stationIntersection = new ArrayList<String> ();
    	for	(Node elem:nodes) {
    		if (elem.getVoisins().size()>2) {
    			stationIntersection.add(elem.getName());
    		}
    	}
		return stationIntersection;
    }
}