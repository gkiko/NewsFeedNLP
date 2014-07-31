package sentiment_anal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;


public class AnalRunner {
	static String testFile2 = "data/dev2";
	static String trainDirectory = "data/sentiment/";
	static String testFile = "data/sentiment/pos/1-2marti-guruli";
	static String stopWords = "data/stopwords.txt";
	static boolean filterStopWords = true;
	
	public static void main(String[] args) {
		AnalRunner.launchSentimentAnal("data/sentiment/", "data/sentiment/neg/cv2");
//		SentimentAnal anal = new SentimentAnal();
//		
//		AnalRunner m = new AnalRunner();
//		List<DirFiles> dirList = m.getDirWithFiles(trainDirectory);
//		for(DirFiles dr : dirList){
//			for(File file : dr){
//				List<String> fileContent = m.segmentFile(file);
//				anal.addInfoToClassifier(dr.getKlass(), fileContent);
//			}
//		}
//		String res = anal.classifyInput(m.segmentFile(new File(testFile)));
//		System.out.println(res);
	}
	
	public static void launchSentimentAnal(String trainDir, String fileName){
		SentimentAnal anal = new SentimentAnal();
		
		AnalRunner m = new AnalRunner();
		List<DirFiles> dirList = m.getDirWithFiles(trainDir);
		for(DirFiles dr : dirList){
			for(File file : dr){
				List<String> fileContent = m.segmentFile(file);
				anal.addInfoToClassifier(dr.getKlass(), fileContent);
			}
		}	
		String res = anal.classifyInput(m.segmentFile(new File(fileName)));
		System.out.println(res);
	}
	
	private List<DirFiles> getDirWithFiles(String dirPath){
		List<DirFiles> ls = new ArrayList<AnalRunner.DirFiles>();
		for(File f : getDirList(dirPath)){
			DirFiles dirFiles = new DirFiles(f.getName());
			dirFiles.addFileList(getFileList(f.getAbsolutePath()));
			ls.add(dirFiles);
		}
		
		return ls;
	}
	
	public List<File> getDirList(String dirPath){
		File dir = new File(dirPath);
		List<File> list = new ArrayList<>();
		for(File f : Arrays.asList(dir.listFiles())){
			if(!f.getName().startsWith(".") && f.isDirectory()){
				list.add(f);
			}
		}
		return list;
	}
	
	private List<File> getFileList(String dirPath){
		File dir = new File(dirPath);
		List<File> list = new ArrayList<>();
		for(File f : Arrays.asList(dir.listFiles())){
			if(!f.getName().startsWith(".") && !f.isDirectory()){
				list.add(f);
			}
		}
		return list;
	}
	
	public List<String> segmentFile(File f){
		Set<String> filter = readStopWords(stopWords);
		String content = getFileContent(f);
//		content = NegationAppender.negativeAppender(content);
		return segmentWords(content, filter);
	}
	
	private Set<String> readStopWords(String fileName){
		Set<String> set = new HashSet<String>();
		try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-8")))){

	  		for(String line = input.readLine(); line != null; line = input.readLine()) {
	  			set.add(line);
	  		}
	  		
	  	} catch(IOException e) {
	  		e.printStackTrace();
	  	}
		return set;
	}
	
	private String getFileContent(File f){
		try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(f), Charset.forName("UTF-8")))){
	  		StringBuilder contents = new StringBuilder();

	  		for(String line = input.readLine(); line != null; line = input.readLine()) {
	  			contents.append(line);
	  			contents.append("\n");
	  		}
	  		
	  		return contents.toString();

	  	} catch(IOException e) {
	  		e.printStackTrace();
	  		System.exit(1);
	  		return null;
	  	} 
	}
	
	private List<String> segmentWords(String fileContent, Set<String> filter){
		List<String> ret = new ArrayList<String>();

		StringTokenizer tk = new StringTokenizer(fileContent);
		String word;
	  	while(tk.hasMoreTokens()){
	  		word = tk.nextToken();
	  		if(word.length() > 0 && !filter.contains(word)) {
	  			ret.add(word);
	  		}
	  	}
	  	return ret;
	}
	
	private class DirFiles implements Iterable<File>{
		
		private List<File> files;
		
		private String klass;
		
		public DirFiles(String klass) {
			this.klass = klass;
			files = new ArrayList<File>();
		}
		
		public void addFileList(List<File> files){
			if(files == null){
				return;
			}
			this.files.addAll(files);
		}
		
		private String getKlass(){
			return klass;
		}

		@Override
		public Iterator<File> iterator() {
			return files.iterator();
		}
	}
}
