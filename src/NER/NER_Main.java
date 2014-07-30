package NER;

import java.io.IOException;
import java.util.List;

public class NER_Main {
	public static void main(String[] args) throws IOException {
		launchNER("data/train", "data/dev2");
	}
	
	public static void launchNER(String trainFile, String testFile) throws IOException{
		System.out.println("Shemovida");
		
		String print = "";
		FeatureFactory ff = new FeatureFactory();
		// read the train and test data
		List<Datum> trainData = ff.readData(trainFile);
		List<Datum> testData = ff.readData(testFile);
		
		// add the features
		List<Datum> trainDataWithFeatures = ff.setFeaturesTrain(trainData);
		List<Datum> testDataWithFeatures = ff.setFeaturesTest(testData);

		// write the data with the features into JSON files
		ff.writeData(trainDataWithFeatures, "trainWithFeatures");
		ff.writeData(testDataWithFeatures, "testWithFeatures");

		System.out.println("Yvelaferi kargadaa Vushvebt MEMM-s");
		
		MEMM.MEMM("trainWithFeatures.json", "testWithFeatures.json", testData);
		
		// run MEMM
//	        ProcessBuilder pb =
//		    new ProcessBuilder("java", "-cp", "classes", "-Xmx1G", "MEMM", "trainWithFeatures.json", "testWithFeatures.json", print);
//	        pb.redirectErrorStream(true);
//	        Process proc = pb.start();

//		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//		String line = br.readLine();
//		while (line != null) {
//		    System.out.println(line);
//		    line = br.readLine();
//		}
	}
}
