/**
 * 
 */
package servlets.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Roland
 *
 */
public class Utils {

	public static ArrayList<String> getStreamList(String schemaPath){
		ArrayList<String> result = new ArrayList<>();
		File schemaFolder = new File(schemaPath);
		File[] schemas = schemaFolder.listFiles();
		if(schemas != null){
			for(File schema : schemas){
				String schemaName = schema.getName().split("Schema")[0];
				result.add(schemaName);
			}
		}
		return result;
	}
	
	public static ArrayList<Long> normalizedTimestamps(HashMap<Long, Integer> rawData){
		ArrayList<Long> result = new ArrayList<>();
		int nbTimestamp = rawData.keySet().size();
		Long start = Long.MAX_VALUE;
		for(Long timestamp: rawData.keySet()){
			if(timestamp < start){
				start = timestamp;
			}
			result.add(timestamp);
		}
		for(int i = 0; i < nbTimestamp; i++){
			result.set(i, result.get(i) - start);
		}
		Collections.sort(result);
		return result;
	}
	
	
	public static ArrayList<Integer> rateValues(HashMap<Long, Integer> rawData){
		int nbTimestamp = rawData.keySet().size();
		ArrayList<Integer> result = new ArrayList<>();
		ArrayList<Long> rawTimestamps = new ArrayList<>();
		for(Long timestamp : rawData.keySet()){
			rawTimestamps.add(timestamp);
		}
		Collections.sort(rawTimestamps);
		for(int i = 0; i< nbTimestamp; i++){
			result.add(rawData.get(rawTimestamps.get(i)));
		}
		return result;
	}
	
}
