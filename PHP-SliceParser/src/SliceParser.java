import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


import model.ProgramSlice;
import patterns.VulnPattern;


public class SliceParser {

	public SliceParser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		//try {
			PatternScanner parser = new PatternScanner("C:\\Users\\pemol\\git\\SegSoft1718\\PHP-SliceParser\\slices\\Patterns.txt");
			parser.readPatterns();
			parser.printPatterns();
			//parser.processLineByLine();
			//parser.printPatterns();
			
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		//parseFile();
		//mainParser(args[0]);
		/*try {
			parseFile("C:\\Users\\pemol\\eclipse-workspace\\PHP-SliceParser\\slices\\slice1.json");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}*/


	}

	public static void mainParser(String filePath) {

		try {
			JsonParser parser = new JsonParser();
			//String filePath = "C:\\Users\\pemol\\eclipse-workspace\\PHP-Slice-Parser\\slices\\slice1.json";
			List<String> calledFunctions = new ArrayList<String>();
			String usedEntry = "";
			String _entryPoint = "";
			String _entryPointArg = "";
			ArrayList<String> _vars = new ArrayList<String>();

			FileReader f = new FileReader(filePath);

			JsonElement element = parser.parse(f);

			if (element.isJsonObject()) {
				JsonObject program = element.getAsJsonObject();
				String progKind = program.get("kind").getAsString();
				System.out.println(progKind);

				JsonArray children = program.getAsJsonArray("children");
				System.out.println("There are "+ children.size() + " children");

				for (int i = 0; i < children.size(); i++) {

					System.out.println("------- Child: "+(i+1)+" -------");

					JsonObject child = children.get(i).getAsJsonObject();

					String kind = child.get("kind").getAsString();
					System.out.println("kind: "+ kind);

					if(kind.equals("assign")) {

						System.out.println("----- LEFT -----");
						JsonObject left = child.getAsJsonObject("left");
						String left_kind = left.get("kind").getAsString();
						String left_name = left.get("name").getAsString();
						System.out.println("left_kind: " + left_kind);
						System.out.println("left_name: " + left_name);

						_vars.add(left_name);

						System.out.println("----- RIGHT -----");
						JsonObject right = child.getAsJsonObject("right");


						if(right.get("kind").getAsString().equals("offsetlookup")) {

							String rKind = right.get("kind").getAsString();
							System.out.println("right_kind: " + rKind);

							if(right.has("what")) {
								System.out.println("---- WHAT ----");
								JsonObject rightElem = right.get("what").getAsJsonObject();
								System.out.println("what_kind: " + rightElem.get("kind").getAsString());
								System.out.println("what_name: " + rightElem.get("name").getAsString());

								String temp = rightElem.get("name").getAsString();

								if( temp.equals("_POST") || temp.equals("_GET") ||temp.equals("_COOKIE")) {
									_entryPoint = temp;
									usedEntry = left.get("name").getAsString();
									System.out.println("_entryPointAfter: " + _entryPoint );
								}		
							}
							if(right.has("offset")) {
								System.out.println("---- OFFSET ----");
								JsonObject offset = right.get("offset").getAsJsonObject();
								_entryPointArg = offset.get("value").getAsString();
								System.out.println("_entryPointArg: " +_entryPointArg );
							}
						}

						if(right.get("kind").getAsString().equals("call")) {
							System.out.println("---- CALL ----");

							JsonObject rightElem = right.get("what").getAsJsonObject();
							String fName = rightElem.get("name").getAsString();
							System.out.println("call_func: "+ fName);

							if(right.has("arguments")) {
								JsonArray call_args = right.get("arguments").getAsJsonArray();
								for(int l = 0; l < call_args.size();l++) {
									JsonObject arg = call_args.get(l).getAsJsonObject();
									System.out.println("call_args["+l+"]: "+ arg.get("name"));
								}
							}
							calledFunctions.add(fName);
						}
						if(right.get("kind").getAsString().equals("encapsed")) {
							System.out.println("----- Encapsed -----");
							if(right.has("type")){
								String type = right.get("type").getAsString();
								System.out.println("type: "+ type);

							}
							if(right.has("value")) {
								System.out.println("----- value vector: ------");
								JsonArray encapsed_values = right.get("value").getAsJsonArray();
								for(int l = 0; l < encapsed_values.size();l++) {
									JsonObject arg = encapsed_values.get(l).getAsJsonObject();

									if(arg.get("kind").getAsString().equals("variable")){
										System.out.println("encapsed_variable_name["+l+"]: "+ arg.get("name"));
									}else if(arg.get("kind").getAsString().equals("string")) {
										System.out.println("encapsed_string_value["+l+"]: "+ arg.get("value"));
									}
								}
							}
						}

					}
				}
			}
			System.out.println("The variable used for user input is: "+"["+ _entryPoint+"]");
			System.out.println("The entry used is: " +"["+usedEntry+"]");
			System.out.println("The called functions are: " + calledFunctions);
			System.out.print("The used variables are: ");
			for(int b = 0 ; b < _vars.size() ; b++) {
				System.out.print("[" + _vars.get(b).toString() + "]");
			}
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

	public static void readDom(String patternsFilePath) 
	{	
		BufferedReader buffReader = null;
		List<VulnPattern> pattern = new ArrayList<>();
		try {
			int lineNum = 0;
			buffReader = new BufferedReader(new FileReader(patternsFilePath));
			String fileRead = buffReader.readLine();

			// loop until all lines are read
			while (fileRead != null)
			{
				// use string.split to load a string array with the values from each line of
				// the file, using a comma as the delimiter
				String[] tokenize = fileRead.split(",");

				// assume file is made correctly
				// and make temporary variables for the three types of data
				for(int i= 0; i < tokenize.length ; i++) {
					System.out.println(tokenize[i]);
				}

				// creat temporary instance of Inventory object
				// and load with three data values
				//VulnPattern tempObj = new VulnPattern();

				// add to array list
				//invItem.add(tempObj);

				// read next line before looping
				// if end of file reached 
				fileRead = buffReader.readLine();
			}

			// close file stream
			buffReader.close();
		} catch (IOException ex ) {
			System.out.println(ex.getMessage());
		} finally {
		}
	}

	public static void parseFile() {


		/*try {

			InputStream is = JsonParser.class.getResourceAsStream( "C:\\Users\\pemol\\eclipse-workspace\\PHP-SliceParser\\slices\\slice1.json");


			JsonReader reader = new JsonReader(new InputStreamReader(is));
	        Gson gson = new GsonBuilder().create();

	        // Read file in stream mode
	        reader.beginArray();
	        while (reader.hasNext()) {
	            // Read data into object model
	            ProgramSlice progSlc = gson.fromJson(reader,  ProgramSlice.class);
	            if (progSlc.get_kind() == "program" ) {
	                System.out.println("Stream mode: " + progSlc);
	            }
	            break;
	        }
	        reader.close();
	    } catch (UnsupportedEncodingException ex) {
	        System.out.println(ex.getMessage());
	    } catch (IOException ex) {
	    	 System.out.println(ex.getMessage());
	    }*/

		try {

			String filename = "C:\\Users\\pemol\\eclipse-workspace\\PHP-Slice-Parser\\slices\\slice1.json";
			Gson gson = new Gson();
			JsonReader reader;	

			reader = new JsonReader(new FileReader(filename));
			ProgramSlice data = gson.fromJson(reader, ProgramSlice.class);

			System.out.println(data.get_kind());



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
