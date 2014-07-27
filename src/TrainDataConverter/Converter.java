package TrainDataConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Converter {
	public static void main(String[] args) {
		try {
			StringTokenizer tk;
			BufferedWriter bw = new BufferedWriter(new FileWriter("data/Converted.txt"));
			BufferedReader rd = new BufferedReader(new FileReader("data/1-2marti-guruli"));
			String line = rd.readLine();
			while(line != null){
				tk = new StringTokenizer(line);
				String word = "";
				while(tk.hasMoreTokens()){
					word = tk.nextToken();
					if(word.contains("ტრეინინგ")){
						bw.append('\n' + word + " PERSON");
					}else{
						bw.append('\n' + word + " O");
					}
				}
				line = rd.readLine();
			}
			bw.flush();
			bw.close();
			rd.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
