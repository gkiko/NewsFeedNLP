package parser2;

public class DataSaver2 {
	
	public static void main(String[] args) {
		DataSaver2 dataSaver = new DataSaver2();
		Downloader downloader = new Downloader();
		FileSaver fileSaver = new BriSaver();
		fileSaver.setDirPath("/Users/gkiko/Workspace/traxan-di-popka/data/bri/");
		Parser p = new BriParser();
		
		dataSaver.downloadParseSave("http://bri.ge/api/getList.aspx?count=20000", downloader, p, fileSaver);
	}
	
	public void downloadParseSave(String url, Downloader d, Parser p, FileSaver fileSaver){
		String jsonData;
		try {
			jsonData = d.readJsonFromUrl(url);
			p.parse(jsonData, fileSaver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
