package parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BasicParser implements Parser{

	@Override
	public PageDataContainer parse(String url) throws IOException {
		Map<String, List<String>> pageContent = new HashMap<String, List<String>>(); 
		String urlNext = null;
		Document doc = Jsoup.connect(url).get();
		
		doc.select(".edit").remove();
		
		// Get post author and content
		Elements authorElements = doc.select(".normalname");
		for(Element auth : authorElements){
			Element postData = auth.parent().parent();
			
//			System.out.print("{"+auth.text()+"} ");
			Elements posts = postData.select(".postcolor");
			posts.select("a").remove();
			
			StringBuilder sb = new StringBuilder();
			for(Element postElem : posts){
				String txt = postElem.text();
				if(!txt.isEmpty()){
					sb.append(txt);
//					System.out.println(txt);
				}
			}
			saveToMap(pageContent, auth.text(), sb.toString());
//			pageContent.put(auth.text(), sb.toString());
		}
		
		// Get next page links
		Element page = doc.select("[title=Jump to page...]").first();
		Element pageParent = page.parent();

		Elements allElements = pageParent.select("*");
		for(int i=0;i<allElements.size();i++){
			Element a = allElements.get(i);
			if(a.tagName().equals("b")){
				Element nextPageTag = a.nextElementSibling();
				if(i<allElements.size()-2){
					urlNext = nextPageTag.attr("href");
//					System.out.println(nextPageTag.attr("href"));
				}
			}
		}
		return new PageDataContainer(pageContent, urlNext);
	}
	
	private void saveToMap(Map<String, List<String>> map, String auth, String txt){
		List<String> txtList = map.get(auth);
		if(txtList == null){
			txtList = new ArrayList<>();
		}
		txtList.add(txt);
		map.put(auth, txtList);
	}

}
