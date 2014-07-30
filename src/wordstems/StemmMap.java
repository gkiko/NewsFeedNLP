package wordstems;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class StemmMap {
	public static HashMap<String, String> stemms = new HashMap<String, String>();
	
	public static void readWordStemms(){
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream("data/stems.txt"), "UTF-8"));
			String line = rd.readLine();
			StringTokenizer tk;
			String stem = "", linkedWords = "";
			while(line != null){
				tk = new StringTokenizer(line, " -[]");
				stem = tk.nextToken();
				while(tk.hasMoreTokens())
					linkedWords += tk.nextToken();
				String [] arr = linkedWords.split(",");
				for(String k : arr){
					if(!stemms.containsKey(k)){
						stemms.put(k, stem);
					}
				}
				linkedWords = "";
				line = rd.readLine();
			}
			rd.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
