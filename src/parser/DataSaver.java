package parser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class DataSaver {
	public static void main(String[] args) throws Exception {
		DataSaver s = new DataSaver();
		s.downloadParseSave("http://forum.ge/?f=29&showtopic=34665804&st=0", new BasicParser(), "/Users/gkiko/Workspace/ForumParser/politics/");
	}
	
	public void downloadParseSave(String url, Parser p, String filePath) throws Exception{
		String nextPageUrl = url;
		Map<String, List<String>> mainMap = new HashMap<>();
		while(nextPageUrl != null){
			PageDataContainer data = p.parse(nextPageUrl);
			mergeMap(mainMap, data.getPageContent());
			
			nextPageUrl = data.getNextPageUrl();
		}
		
		saveToFile(mainMap, filePath);
	}
	
	private void mergeMap(Map<String, List<String>> map1, Map<String, List<String>> map2){
		for(String key2 : map2.keySet()){
			List<String> val2 = map2.get(key2);
			
			List<String> val1;
			if(map1.containsKey(key2)){
				val1 = map1.get(key2);
				val2.addAll(val1);
			}
			map1.put(key2, val2);
		}
	}
	
	private void saveToFile(Map<String, List<String>> map, String path){
		File f;
		FileWriter fw = null;
		for(String key : map.keySet()){
			f = new File(path+key);
			
			try {
				fw = new FileWriter(f);
				for(String txt : map.get(key)){
					fw.write(txt);
					fw.write("\n");
				}
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
