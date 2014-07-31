package custom_features;
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
				if(containsBidzo(normalDatum)) score1 += 1;
				if(datumWord.contains("ივანიშვილ")){
					score1 += 1.5;
					if(prevDatum != null && containsBidzo(normalPrevDatum)) score1 += 1.5;
					if(personLabel(prevDatum) && (prevDatum != null && !containsBidzo(normalPrevDatum))) score1 -= 2;
				}
				
				if(containtsMisha(normalDatum)) score2 += 1;
				if(datumWord.contains("სააკაშვილ")){
					score2 += 1.5;
					if(prevDatum != null && containtsMisha(normalPrevDatum)) score2 += 1.5;
					if(personLabel(prevDatum) && (prevDatum != null && !containtsMisha(normalPrevDatum))) score2 -= 2;
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
	
	private boolean containsBidzo(Datum datum){
		return datum.word.contains("ბიძ") || datum.word.contains("ექსპრემიერი") || datum.word.contains("ჰაკიმ");
	}
	
	private boolean containtsMisha(Datum datum){
		return datum.word.contains("მიშა") || datum.word.contains("მიხეილ") || datum.word.contains("მაღალი") || datum.word.contains("ექსპრეზიდენტი");
	}
	
	public static void main(String[] args) {
//		List<Datum> ls = Arrays.asList(new Datum[]{new Datum("ჩვენ", "O"), new Datum("ჰაკიმ", "PERSON"), new Datum("ფაშას", "O"), new Datum("ვეძახით", "O"), new Datum("ირაკლი", "PERSON"), new Datum("ღარიბაშვილს", "PERSON")});
//		fillDatum(ls);
		
//		List<Datum> ls1 = Arrays.asList(new Datum[]{new Datum("���������������", "O"), new Datum("������������������", "person"), new Datum("������������������������������", "O"), new Datum("������������������������", "O")});
//		fillDatum(ls1);
		
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
