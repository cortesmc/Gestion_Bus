import java.time.LocalTime;

public class GestionBus {
    
    public static void main(String[] args) {
    	
    	GestionHoraires horairesGestion = new GestionHoraires();
    	
    	boolean Holiday = true;
    	String start = "Vernod";
    	String destination = "Impérial";
    	LocalTime timeStart = LocalTime.parse("07:35");
    	
    	horairesGestion.getShortestPath(timeStart, start, destination, Holiday);
    	System.out.println("\n-----------------------------------\n");
    	horairesGestion.getFastestPath(timeStart, start, destination, Holiday);
    	System.out.println("\n-----------------------------------\n");
    	horairesGestion.getForemostPath(timeStart, start, destination, Holiday);
    }
} 
