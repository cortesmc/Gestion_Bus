import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;
import java.time.Duration;

public class GetData {
	
	private static final String DATA_FILE_NAME1 = "data/1_Poisy-ParcDesGlaisins.txt";
    private static final String DATA_FILE_NAME2 = "data/2_Piscine-Patinoire_Campus.txt";
	private BufferedReader reader;
	
    public GetData() {
    	
    }
     
    public ArrayList<Map<String, List<LocalTime>>> getInformationTxt(int nro) {
    	ArrayList<Map<String, List<LocalTime>>> info = new ArrayList<Map<String, List<LocalTime>>>();
    	String content = "";
        try {
        	if (nro == 1) {
        		this.reader = new BufferedReader(new FileReader(DATA_FILE_NAME1));
        	} else if (nro == 2) {
        		this.reader = new BufferedReader(new FileReader(DATA_FILE_NAME2));
        	}
    		String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }

        List<String> splittedContent = List.of(content.split("\n\n"));
        String regularPath = splittedContent.get(0);
        Map<String, List<LocalTime>> regularDateGo = datesToMap(splittedContent.get(1));
        Map<String, List<LocalTime>> regularDateBack = null;
        String weHolidaysPath = null;
        Map<String, List<LocalTime>> weHolidaysDateGo = null;
        Map<String, List<LocalTime>> weHolidaysDateBack = null;
        if (splittedContent.size() >= 3) {
            regularDateBack = datesToMap(splittedContent.get(2));
        }
        if (splittedContent.size() >= 4) {
            weHolidaysPath = splittedContent.get(3);
        }
        if (splittedContent.size() >= 5) {
            weHolidaysDateGo = datesToMap(splittedContent.get(4));
        }
        if (splittedContent.size() >= 6) {
            weHolidaysDateBack = datesToMap(splittedContent.get(5));
        }
        info.add(regularDateGo);
        info.add(regularDateBack);
        info.add(weHolidaysDateGo);
        info.add(weHolidaysDateBack);
        
        return info;
    }
    
    public Map<String, List<LocalTime>> datesToMap(String dates) {
        Map<String, List<LocalTime>> map = new LinkedHashMap<>();
        String[] splittedDates = dates.split("\n");
        for (String stopDates : splittedDates) {
            String[] tmp = stopDates.split(" ");
            List<LocalTime> values = new ArrayList<>();
            for (int i = 1; i < tmp.length; i++) {
            	int cpt = 0;
            	int time1 = 0;
            	int time2 = 0;
            	//Ajouter Try catch 
            	for (String elem:tmp[i].split("\\:")){
            		try {
	            		if(cpt ==0) {
	            			time1= Integer.parseInt(elem);
	            			cpt++;
	            		} else {
	            			time2= Integer.parseInt(elem);
	            		}
            		} catch (Exception e) {
            			continue;
            		}
            	}
            	LocalTime timeTmp = LocalTime.of(time1,time2); 
            	values.add(timeTmp);
            }
            map.put(tmp[0], values);
        }
        return map;
    }
}
 