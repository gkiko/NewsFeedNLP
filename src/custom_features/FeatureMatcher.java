package custom_features;
import java.util.Arrays;
import java.util.List;

import NER.Datum;


public class FeatureMatcher {
	
	public String match(List<Datum> datumList, List<Datum> normalDatumList){
		double score1 = 0, score2 = 0;
		Datum datum, normalDatum, prevDatum = null, normalPrevDatum = null;
		String datumWord;
		for(int i = 0; i < datumList.size(); i++){
			datum = datumList.get(i);
			normalDatum = normalDatumList.get(i);
			datumWord = normalDatum.word;
			if(personLabel(datum)){
				if(datumWord.contains("ბიძ")) score1 += 1;
				if(datumWord.contains("ივანიშვილ")){
					score1 += 1.5;
					if(prevDatum != null && normalPrevDatum.word.contains("ბიძ")) score1 += 1.5;
					if(personLabel(prevDatum) && (prevDatum != null && !normalPrevDatum.word.contains("ბიძ"))) score1 -= 2;
				}
				
				if(datumWord.contains("მიშა") || datumWord.contains("მიხეილ") || datumWord.contains("მაღალი")) score2 += 1;
				if(datumWord.contains("სააკაშვილ")){
					score2 += 1.2;
					if(prevDatum != null && normalPrevDatum.word.contains("მიშა")) score2 += 1.5;
					if(personLabel(prevDatum) && (prevDatum != null && !normalPrevDatum.word.contains("მიშა"))) score2 -= 2;
				}
			}
			
			normalPrevDatum = normalDatum;
			prevDatum = datum;
		}
		
		System.out.println(score1+" "+score2);
		String result = "";
		if(score1 > score2)
			result = "ბიძინა ივანიშვილი";
		else if(score1 < score2)
			result = "მიშა სააკაშვილი";
		else
			result = "შალვა ნათელაშვილი";
		
		return result;
	}
	
	private boolean personLabel(Datum datum){
		return datum.label!=null && datum.label.equals("PERSON");
	}
	
	public static void main(String[] args) {
//		List<Datum> ls = Arrays.asList(new Datum[]{new Datum("საქართველოს", "O"), new Datum("პრემიერი", "O"), new Datum("არის", "O"), new Datum("ბიძო", "PERSON"), new Datum("სააკაშვილი", "PERSON")});
//		fillDatum(ls);
//		
//		List<Datum> ls1 = Arrays.asList(new Datum[]{new Datum("ჩვენი", "O"), new Datum("მაღალი", "person"), new Datum("დაკითხვაზე", "O"), new Datum("დაიბარეს", "O")});
//		fillDatum(ls1);
//		
//		FeatureMatcher fm = new FeatureMatcher();
//		fm.match(ls);
//		fm.match(ls1);
	}
	
	public static void fillDatum(List<Datum> ls){
		for(int i=1;i<ls.size();i++){
			Datum d = ls.get(i);
			d.previousLabel = ls.get(i-1).label;
		}
	}
}
