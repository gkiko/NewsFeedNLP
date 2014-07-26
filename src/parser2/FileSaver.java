package parser2;

public abstract class FileSaver {
	protected String path;
	
	public void setDirPath(String path){
		this.path = path;
	}
	
	public abstract void saveContentToFile(String content, String fileName) throws Exception;
}
