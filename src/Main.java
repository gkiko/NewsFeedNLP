import java.io.IOException;

import sentiment_anal.AnalRunner;
import wordstems.StemmMap;
import NER.NER_Main;


public class Main {
	public static void main(String[] args) {
		StemmMap.readWordStemms();
		
		try {
			NER_Main.launchNER("data/train", "data/dev2");
			AnalRunner.launchSentimentAnal("data/sentiment/", "data/sentiment/neg/cv0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
