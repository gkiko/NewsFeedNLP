package parser2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class BriParser implements Parser {
	JsonParser parser = new JsonParser();

	@Override
	public void parse(String data, FileSaver fileSaver) throws Exception {
		JsonObject baseObj = parser.parse(data).getAsJsonObject();
		JsonArray baseArr = baseObj.getAsJsonArray("data");
		
		String description, title;
		JsonObject obj;
		for(JsonElement elem : baseArr){
			obj = elem.getAsJsonObject();
			description = obj.get("description").getAsString();
			title = obj.get("statID").getAsString();
			fileSaver.saveContentToFile(description, title);
		}
	}

}
