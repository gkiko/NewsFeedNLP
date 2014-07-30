import java.util.Arrays;
import java.util.List;

import NER.Datum;


public class FeatureMatcher {
	
	public String match(List<Datum> datumList){
		double score1 = 0, score2 = 0;
		Datum datum, prevDatum = null;
		String datumWord;
		for(int i=0;i<datumList.size();i++){
			datum = datumList.get(i);
			datumWord = datum.word;
			if(personLabel(datum)){
				if(datumWord.contains("ბიძ")) score1 += 1;
				if(datumWord.contains("ივანიშვილ")){
					score1 += 1.5;
					if(prevDatum!=null && prevDatum.word.contains("ბიძ")) score1 += 1.5;
					if(personLabel(prevDatum) && (prevDatum!=null && !prevDatum.word.contains("ბიძ"))) score1 -= 2;
				}
				
				if(datumWord.contains("მიშა") || datumWord.contains("მიხეილ") || datumWord.contains("მაღალი")) score2 += 1;
				if(datumWord.contains("სააკაშვილ")){
					score2 += 1.2;
					if(prevDatum!=null && prevDatum.word.contains("მიშა")) score2 += 1.5;
					if(personLabel(prevDatum) && (prevDatum!=null && !prevDatum.word.contains("მიშა"))) score2 -= 2;
				}
			}
			
			prevDatum = datum;
		}
		
		System.out.println(score1+" "+score2);
		return "";
	}
	
	private boolean personLabel(Datum datum){
		return datum.label!=null && datum.label.equals("person");
	}
	
	public static void main(String[] args) {
		List<Datum> ls = Arrays.asList(new Datum[]{new Datum("საქართველოს", "O"), new Datum("პრემიერი", "O"), new Datum("არის", "O"), new Datum("ბიძო", "person"), new Datum("სააკაშვილი", "person")});
		fillDatum(ls);
		
		List<Datum> ls1 = Arrays.asList(new Datum[]{new Datum("ჩვენი", "O"), new Datum("მაღალი", "person"), new Datum("დაკითხვაზე", "O"), new Datum("დაიბარეს", "O")});
		fillDatum(ls1);
		
		FeatureMatcher fm = new FeatureMatcher();
		fm.match(ls);
		fm.match(ls1);
	}
	
	public static void fillDatum(List<Datum> ls){
		for(int i=1;i<ls.size();i++){
			Datum d = ls.get(i);
			d.previousLabel = ls.get(i-1).label;
		}
	}
}
