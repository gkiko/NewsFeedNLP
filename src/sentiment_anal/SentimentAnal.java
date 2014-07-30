package sentiment_anal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import wordstems.StemmMap;


public class SentimentAnal {
	private double positiveClasses = 0, negativeClasses = 0;
	private HashMap<String, Double> posWords = new HashMap<String, Double>();
	private HashMap<String, Double> negWords = new HashMap<String, Double>();
	private HashMap<String, Double> totalClassWords = new HashMap<String, Double>();
	private HashSet<String> tempSet;
	
	public void addInfoToClassifier(String classType, List<String> words){
		tempSet = new HashSet<String>();
		if("pos".equals(classType))
			positiveClasses++;
		else
			negativeClasses++;
		for(int i = 0; i < words.size(); i++){
			String word = words.get(i);
			word = removeNonCharacterSymbols(word);
			word = getWordStem(word);
			saveFreq(word, totalClassWords);
			if("pos".equals(classType))
				saveFreqBinorized(word, posWords, tempSet);
			else
				saveFreqBinorized(word, negWords, tempSet);
		}
	}
	
	private String getWordStem(String word){
		String stem = "";
		if(StemmMap.stemms.containsKey(word)){
			stem = StemmMap.stemms.get(word);
		} else 
			stem = word;
		return stem;
	}
	
	private String removeNonCharacterSymbols(String word) {
		word = word.replaceAll("!", "");
		word = word.replaceAll("\\?", "");
		word = word.replaceAll("-", "");
		word = word.replaceAll("_", "");
		word = word.replaceAll("\\.", "");
		word = word.replaceAll(",", "");
		word = word.replaceAll(";", "");
		word = word.replaceAll(":", "");
		char br = '*';
		word = word.replaceAll("\\"+br, "");
		word = word.replaceAll("'", "");
		return word;
	}

	
	private void saveFreq(String word, HashMap<String, Double> map){
		if(map.containsKey(word)){
			double count = map.get(word);
			count++;
			map.put(word, count);
		}else
			map.put(word,  1.0);
	}
	
	/**
	 * Boolean Multinomial Bayes -istvis
	 * 
	 * @param word
	 * @param map
	 * @param set
	 */
	private void saveFreqBinorized(String word, HashMap<String, Double> map, HashSet<String> set){
		if(!set.contains(word)){
			if(map.containsKey(word)){
				double count = map.get(word);
				count++;
				map.put(word, count);
			}else{
				map.put(word,  1.0);
			}
			set.add(word);
		}
	}
	
	/**
	 * Counting Probabilities and classifying
	 * @param words
	 * @return
	 */
	public String classifyInput(List<String> words){
		double posProbability = countProbability("pos", words);
		double negProbability = countProbability("neg", words);
		if(posProbability >= negProbability)
			return "pos";
		else
			return "neg";
	}
	
	/**
	 *  Mtavari metodi romelic itvlis klasistvis "argMax" albatobas (Naive Bayes)
	 *  
	 * @param classType
	 * @param words
	 * @return
	 */
	private double countProbability(String classType, List<String> words){
		double finalProb = 0;
		double classProbability = 0, wordProbabilities = 0;
		if("pos".equals(classType)){
			classProbability = positiveClasses / (positiveClasses + negativeClasses);
			wordProbabilities = countWordProbs(posWords, words);
			finalProb += Math.log(classProbability) + wordProbabilities;
		} else {
			classProbability = negativeClasses / (positiveClasses + negativeClasses);
			wordProbabilities = countWordProbs(negWords, words);
			finalProb += Math.log(classProbability) + wordProbabilities;
		}
		return finalProb;
	}
	
	/**
	 * aq vitvli Laplace Smoothing-is gamoyenebit titoeuli sityvis albatobas da
	 * vabrune albatobebis namravls
	 * 
	 * @param map
	 * @param words
	 * @return
	 */
	private double countWordProbs(HashMap<String, Double> map, List<String> words){
		double result = 0;
		double vocabulary = map.keySet().size();
		for(int i = 0; i < words.size(); i++){
			String word = words.get(i);
			double wordCountInClass = getCount(map, word);
			double wordCountInTotal = getCount(totalClassWords, word);
			result += Math.log((wordCountInClass + 1.0) / (wordCountInTotal + vocabulary));
		}
		return result;
	}
	
	private double getCount(HashMap<String, Double> map, String word){
		if(map.containsKey(word))
			return map.get(word);
		else
			return 0.0;
	}
	

	public static void main(String[] args) {
		System.out.println("opa");
	}
}
