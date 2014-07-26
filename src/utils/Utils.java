package utils;

import java.util.StringTokenizer;

public class Utils {
	public static String negativeAppender(String str){
		StringBuilder sb = new StringBuilder();
		StringTokenizer tk = new StringTokenizer(str);
		
		boolean withinNegation = false;
		String word;
		while(tk.hasMoreTokens()){
			word = tk.nextToken();
			if(withinNegation){
				sb.append("არ_");
			}
			
			if(isNegativeKeyword(word)){
				withinNegation = true;
			}

			sb.append(word);
			sb.append(" ");
			if(endOfSentence(word)){
				withinNegation = false;
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	private static boolean isNegativeKeyword(String str){
		return str.equals("არა") || str.equals("არ");
	}
	
	private static boolean endOfSentence(String str){
		return str.endsWith(".") || str.endsWith(",") || str.equals(".") || str.equals(",") || str.equals(" - ");
	}
	
}