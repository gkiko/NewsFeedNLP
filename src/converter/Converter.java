package converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Converter {
	final static String dirPath = "data/sentiment/neg";
	final static String writeFilePath = "data/Converted.txt";
	
	public static void main(String[] args) {
		Converter c = new Converter();
		
		File fOut = new File(writeFilePath);
		fOut.delete();

		File dir = new File(dirPath);
		for(File f : dir.listFiles()){
			if(!f.getName().startsWith(".")){
				c.convertToFile(fOut, f);
			}
		}
	}
	
	public void convertToFile(File fileOut, File fileIn){
		String line;
		StringTokenizer tk;
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut, true), StandardCharsets.UTF_8));
			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn), StandardCharsets.UTF_8));){
			while((line = rd.readLine()) != null){
				tk = new StringTokenizer(line);
				String word = "";
				while(tk.hasMoreTokens()){
					word = tk.nextToken();
					if(word.contains("ივანიშვილ")){
						bw.append('\n' + word + " PERSON");
					}else{
						bw.append('\n' + word + " O");
					}
				}
			}
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
