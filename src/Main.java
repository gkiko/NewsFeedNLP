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

import NER.NER_Main;
import sentiment_anal.AnalRunner;
import sentiment_anal.SentimentAnal;
import wordstems.StemmMap;


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
