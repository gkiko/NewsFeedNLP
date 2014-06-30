package parser;
import java.util.List;
import java.util.Map;


public class PageDataContainer{
	Map<String, List<String>> pageContent;
	String nextPageUrl;

	public Map<String, List<String>> getPageContent() {
		return pageContent;
	}

	public String getNextPageUrl() {
		return nextPageUrl;
	}
	
	public PageDataContainer(Map<String, List<String>> map, String url){
		pageContent = map;
		nextPageUrl = url;
	}
}