import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import model.ProgramSlice;


public class SliceParser {

	public SliceParser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		/*readDom();*/
		/*parseFile();*/
		
		try {
			parseFile("C:\\Users\\pemol\\eclipse-workspace\\PHP-SliceParser\\slices\\slice1.json");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}


	public static void parseFile(String filePath) throws FileNotFoundException {
		JsonParser parser = new JsonParser();

		FileReader f = new FileReader(filePath);

		JsonElement element = parser.parse(f);

		if (element.isJsonObject()) {
			JsonObject program = element.getAsJsonObject();
			System.out.println(program.get("kind").getAsString());
			System.out.println("\n");
			JsonArray children = program.getAsJsonArray("children");

			for (int i = 0; i < children.size(); i++) {
				System.out.println("Children:\n");
				JsonObject childs = children.get(i).getAsJsonObject();
				System.out.println("kind: " + childs.get("kind").getAsString());
				if(childs.has("operator")) {
					System.out.println("operator: " + childs.get("operator").getAsString());
				}
				System.out.println("\n");

				JsonObject left = childs.getAsJsonObject("left");
				System.out.println("left_kind: " + left.get("kind"));
				System.out.println("left_name: " + left.get("name"));
				System.out.println("\n");

				JsonObject right = childs.getAsJsonObject("right");
				System.out.println("right_kind: " + right.get("kind"));
				if(right.has("name")) {
					System.out.println("right_name: " + right.get("name"));
				}
				System.out.println("\n");

				if(right.has("value")) {
					System.out.println("list of values of right member of = ");
					JsonArray rightVal = right.getAsJsonArray("value");
					for(int j = 0 ; j < rightVal.size() ; j++) {
						JsonObject listElem = rightVal.get(j).getAsJsonObject();
						System.out.println("ListElement["+ j +"]: kind: "+ listElem.get("kind").getAsString());
						if(listElem.has("name")) {
							System.out.println("ListElement["+ j +"]: name: " + listElem.get("name"));
						}
						if(listElem.has("value")) {
							System.out.println("ListElement["+ j +"]: value: "+ listElem.get("value").getAsString());
						}

					}
				}

				System.out.println("\n");
			}
		}
	}
	
	public static void readDom() {
		BufferedReader reader = null;
		System.out.println("readDom()");

		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\pemol\\eclipse-workspace\\PHP-SliceParser\\slices\\slice1.json"));
			Gson gson = new GsonBuilder().create();
			ProgramSlice[] people = gson.fromJson(reader, ProgramSlice[].class);

			System.out.println(people.toString());

			System.out.println("Object mode: " + people[0]);

		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} finally {
		}
	}

	public static void parseFile() {


		JsonReader jsonReader = new JsonReader(new StringReader("C:\\Users\\pemol\\eclipse-workspace\\SliceParser\\slices\\slice1.json"));
		jsonReader.setLenient(true);
		try {
			while(jsonReader.hasNext()) {

				JsonToken nextToken = jsonReader.peek();
			
				System.out.println(nextToken.toString());
				
				if(JsonToken.BEGIN_OBJECT.equals(nextToken)){

					jsonReader.beginObject();
					System.out.println(jsonReader.toString());
					


				} 
				
				if(JsonToken.END_OBJECT.equals(nextToken)){

					jsonReader.close();

				} 

			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
