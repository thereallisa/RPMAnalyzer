import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Analyze {
	static Map<String, Integer> countsByMinute = new HashMap<String, Integer>();
	static Map<String, Integer> countsBySecond = new HashMap<String, Integer>();
	
	private static void analyze(String path) throws Exception{
		File d = new File(path);
		
		if(!d.exists())
			throw new FileNotFoundException(path + " was not found");	
		if(!d.isDirectory())
			throw new Exception(path + " is not a directory");
		
		traverseDirectory(path);
		
		printAverage("minute ", countsByMinute);
		printAverage("second ", countsBySecond);		
	}

	/**
	 * Traverse all files in the given directoryPath
	 * @param directoryPath
	 * @throws IOException
	 */
	private static void traverseDirectory(String directoryPath) throws IOException{
		File dir = new File(directoryPath);
		for(int i = 0; i < dir.listFiles().length; i++){
			File f = new File(dir.listFiles()[i].getPath());
			if(f.isDirectory())
				traverseDirectory(f.getPath());
			else if(f.getName().charAt(0) != '.')
				readFile(f);
		}		
	}
	
	/**
	 * Read all lines in the given file and store the number of calls by minute and by second
	 * @param file
	 * @throws IOException
	 */
	private static void readFile(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
		   int colonIndex = line.indexOf(":");
		   increaseCount(line.substring(colonIndex + 1, colonIndex + 6), countsByMinute);
		   increaseCount(line.substring(colonIndex + 1, colonIndex + 9), countsBySecond);
		}
		br.close();
	}

	/**
	 * Print the average number of calls for the given timeUnit and map for that timeUnit
	 * @param timeUnit
	 * @param map
	 */
	private static void printAverage(String timeUnit, Map<String, Integer> map){
		Iterator<String> keys = map.keySet().iterator();
		int total = 0;
		int max = 0;
		while(keys.hasNext()){
			int count = map.get(keys.next());
			total += count;
			if(count > max)
				max = count;
		}
		System.out.println("per " + timeUnit + " max = " + max + "; average = " + total/map.keySet().size());
	}
	
	/**
	 * Increase the value in the map by 1 for the given key
	 * @param key
	 * @param map
	 */
	private static void increaseCount(String key, Map<String,Integer> map){
		Integer count = map.get(key);
		if(count == null)
			count = 0;
		map.put(key, count + 1);		
	}
	
	public static void main(String[] args) throws Exception{
		if(args[0] == null)
			throw new IllegalArgumentException("args[0] not supplied");

		analyze(args[0]);
	}
}
