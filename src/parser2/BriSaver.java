package parser2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class BriSaver extends FileSaver{

	@Override
	public void saveContentToFile(String content, String fileName) throws Exception {
		if(path == null){
			throw new Exception("no path set");
		}
		
		File f = new File(path+fileName);
		if(f.exists()){
			return;
		}
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))){
			bw.write(content);
			bw.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
