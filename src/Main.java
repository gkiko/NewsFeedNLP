import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class Main {
	static String trainDir = "/Users/gkiko/Documents/NLP/assign3/pa3-sentiment/data/imdb1";
	
	public static void main(String[] args) {
		SentimentAnal anal = new SentimentAnal();

		Main m = new Main();
		List<DirFiles> dirList = m.getFileContents(trainDir);
		for(DirFiles dr : dirList){
			for(List<String> fileContent : dr){
				anal.addInfoToClassifier(dr.getKlass(), fileContent);
			}
		}
	}
	
	private List<DirFiles> getFileContents(String dirPath){
		List<DirFiles> ls = new ArrayList<Main.DirFiles>();
		File trainDir = new File(dirPath);
		for(File f : trainDir.listFiles()){
			if(!f.getName().startsWith(".") && f.isDirectory()){
				
				DirFiles dirFiles = new DirFiles(f.getName());
				List<String> fileContent;
				for(File f1 : f.listFiles()){
					fileContent = getFileContent(f1);
					dirFiles.addFileContent(fileContent);
				}
				ls.add(dirFiles);
			}
		}
		
		return ls;
	}
	
	private List<String> getFileContent(File f){
		try (BufferedReader input = new BufferedReader(new FileReader(f));){
	  		StringBuilder contents = new StringBuilder();

	  		for(String line = input.readLine(); line != null; line = input.readLine()) {
	  			contents.append(line);
	  			contents.append("\n");
	  		}
	  		input.close();

	  		return segmentWords(contents.toString());

	  	} catch(IOException e) {
	  		e.printStackTrace();
	  		System.exit(1);
	  		return null;
	  	} 
	}
	
	private List<String> segmentWords(String fileContent){
		List<String> ret = new ArrayList<String>();

		StringTokenizer tk = new StringTokenizer(fileContent);
		String word;
	  	while(tk.hasMoreTokens()){
	  		word = tk.nextToken();
	  		if(word.length() > 0) {
	  			ret.add(word);
	  		}
	  	}
	  	return ret;
	}
	
	private class DirFiles implements Iterable<List<String>>{
		
		private List<List<String>> fileContents;
		
		private String klass;
		
		public DirFiles(String klass) {
			this.klass = klass;
			fileContents = new ArrayList<List<String>>();
		}
		
		public void addFileContent(List<String> fileContent){
			if(fileContent == null){
				return;
			}
			this.fileContents.add(fileContent);
		}
		
		private String getKlass(){
			return klass;
		}

		@Override
		public Iterator<List<String>> iterator() {
			return fileContents.iterator();
		}
	}
}
