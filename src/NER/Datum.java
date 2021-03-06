package NER;
import java.util.List;

public class Datum {

  public final String word;
  public final String label;
  public List<String> features;
  public String guessLabel;
  public String previousLabel;
  
  public Datum(String word, String label) {
    this.word = word;
    this.label = label;
  }
  
  @Override
	public String toString() {
	  	String result = "";
	  	result += word + " " + label + " " + guessLabel;
		return result;
	}
}