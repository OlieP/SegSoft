import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.ProgramSlice;
import model.SliceElement.OffsetLookup;
import model.ast.Assign_node;
import model.ast.Block_node;
import model.ast.Call_node;
import model.ast.Encapsed_node;
import model.ast.Expression_node;
import model.ast.Variable_node;
import model.ast.Node;
import model.ast.OffsetLookup_node;
import model.ast.Statement_node;
import model.ast.String_node;


public class SliceParser {

	private Node _sliceTree; 

	ArrayList<String> _variables; 
	List<String> _sanitizationUsed;
	List<String> _sensitive_sinks; 

	public SliceParser() {
		_sliceTree = new Node();
		_variables = new ArrayList<String>();
		_sanitizationUsed =  new ArrayList<String>();
		_sensitive_sinks =  new ArrayList<String>();
	}

	public static void main(String[] args) {
		SliceParser p = new SliceParser();
		p.mainParser(args[0]);
	}
	


	public ArrayList<String> fromJsonArrayToArrayList(JsonArray value) {
		ArrayList<String> list = new ArrayList<String>();     
		JsonArray jsonArray = (JsonArray)value; 
		if (jsonArray != null) { 
			int len = jsonArray.size();
			for (int k=0;k<len;k++){ 
				list.add(jsonArray.get(k).toString());
			} 
		} 
		return list;
	}

	public void mainParser(String filePath) {

		try {
			JsonParser parser = new JsonParser();
			//String_node filePath = "C:\\Users\\pemol\\eclipse-workspace\\PHP-Slice-Parser\\slices\\slice1.json";

			JsonArray children = null;
			JsonObject child = null;
			JsonObject left_child = null; 
			JsonObject right_child = null;
			JsonObject offset = null;
			JsonArray funcArgs = null;

			FileReader f = new FileReader(filePath);
			JsonElement element = parser.parse(f);
			
			Block_node block_node;

			if (element.isJsonObject()) {

				JsonObject program = element.getAsJsonObject();
				children = program.getAsJsonArray("children");
				String progKind = program.get("kind").getAsString();

				block_node = new Block_node(progKind);

				ArrayList<Node> childs = block_node.get_cildren();
				
				ArrayList<Node> left_childs = new ArrayList<Node>();
				ArrayList<Node> right_childs = new ArrayList<Node>();


				for (int i = 0; i < children.size(); i++) {
					child = children.get(i).getAsJsonObject();
					String child_kind =  child.get("kind").getAsString();
					String child_operator =  child.get("operator").getAsString();

					System.out.println(child);
					System.out.println("child_kind: "+ child_kind +", child_operator: " + child_operator);

					if(child.has("left")) {
						left_child = child.get("left").getAsJsonObject();
						String left_kind =  left_child.get("kind").getAsString();
						String var_name ="";
						
						if(left_kind.equals("variable")) {	
							var_name = left_child.get("name").getAsString();
							Variable_node v = new Variable_node(left_kind,var_name);
							left_childs.add(v);
						}
						//System.out.println("");
						//System.out.println("----Left----");
						//System.out.println(left_child);
						//System.out.println("left_kind: " + left_kind +",\nleft_name: "+ var_name);
					}
					if(child.has("right")) {

						JsonObject what = new JsonObject();

						right_child = child.get("right").getAsJsonObject();
						System.out.println("----Right----");
						System.out.println(right_child);
						String right_child_kind = right_child.get("kind").getAsString();
						String right_child_type = "";

						if(right_child.has("type")) {
							right_child_type = right_child.get("type").getAsString();
						}

						System.out.println("right_kind: "+ right_child_kind +", right_type: " +right_child_type);

						if(right_child_kind.equals("offsetlookup")) {
							what = right_child.get("what").getAsJsonObject();
							String what_kind = what.get("kind").getAsString();
							String what_name = what.get("name").getAsString();
							System.out.println("----offsetlookup/what---");
							System.out.println(what);
							
							Variable_node what_var = new Variable_node(what_kind, what_name);
							String_node offset_str = null;

							if(right_child.has("offset")) {
								offset = right_child.get("offset").getAsJsonObject();
								String offset_kind = offset.get("kind").getAsString();
								String offset_value = offset.get("value").getAsString();

								offset_str = new String_node(offset_kind,offset_value);
								
								System.out.println("----offset/offsetlookup----");
								System.out.println(offset);
							}
							
							OffsetLookup_node offLk = new OffsetLookup_node("offsetlookup", what_var, offset_str);
							right_childs.add(offLk);
						}

						if(right_child_kind.equals("encapsed")) {
							String kind =  right_child.get("kind").getAsString();
							JsonArray value = right_child.get("value").getAsJsonArray();
							ArrayList<String> encapsedValues = fromJsonArrayToArrayList(value);  
							
							Encapsed_node enc = new Encapsed_node(kind,right_child.get("type").getAsString());
							
							System.out.println("----encapsed----");
							System.out.println(value);

		
							System.out.println("Printing encapsed values");
							for(int j = 0; j < encapsedValues.size(); j++) {
								//System.out.println(encapsedValues.get(j));
							}
							for(int j = 0; j < value.size(); j++) {
								JsonObject val1 = (JsonObject) value.get(j);
								String val_kind = val1.get("kind").getAsString();
								String var_name="";
								String str_value="";
								
								Expression_node aux = null;

								if(val_kind.equals("string")) {
									str_value = val1.get("value").getAsString();
									aux =  new String_node(val_kind,str_value);
									
								}else if(val_kind.equals("variable")) {
									var_name = val1.get("name").getAsString();
									aux =  new Variable_node(val_kind,var_name);
								}
								
								right_childs.add(aux);
								
								System.out.println("kind: " + val_kind);
								System.out.println("value: "+ var_name);
								System.out.println("name: "+ str_value);
							}

						}
						
						if(right_child_kind.equals("call"))
						{
							String kind =  right_child.get("kind").getAsString();
							what = right_child.get("what").getAsJsonObject();
							String what_kind = what.get("kind").getAsString();
							String what_name = what.get("name").getAsString();
							System.out.println("----call/what---");
							System.out.println(what);

							
							
							ArrayList<Expression_node> funcArgsList = new ArrayList<Expression_node>();

							if(right_child.has("arguments")) {
								System.out.println("----call/arguments---");
								
								funcArgs = right_child.get("arguments").getAsJsonArray();
							
								for(int b =0; b < funcArgs.size(); b++) {
									
									JsonObject arg = funcArgs.get(b).getAsJsonObject();
									String arg_kind = arg.get("kind").getAsString();
									String arg_name = arg.get("name").getAsString();
									
									Variable_node var = new Variable_node(arg_kind,arg_name);
									
									funcArgsList.add(var);
									
									System.out.println(var);
								}
							}
							
							Call_node call = new Call_node(what_kind, what_name,funcArgsList);
							right_childs.add(call);
						}
					}
					System.out.println("\n------- Next Child -------\n");
				}
				
				System.out.println(right_childs);
				/*for (int i = 0; i < children.size(); i++) {

					System.out.println("------- Child: "+(i+1)+" -------");
					JsonObject child = children.get(i).getAsJsonObject();
					String kind = child.get("kind").getAsString();

					if(kind.equals("assign")) {

						System.out.println("----- LEFT -----");
						JsonObject left = child.getAsJsonObject("left");

						String left_kind = left.get("kind").getAsString();
						String left_name = left.get("name").getAsString();

						Assign_node newAssign = new Assign_node(left_kind);
						newAssign.setLeft_var_name(left_name);

						//sliceElements.add(newAssign);

						//_vars.add(left_name);

						//System.out.println("----- RIGHT -----");
						JsonObject right = child.getAsJsonObject("right");


						if(right.get("kind").getAsString().equals("offsetlookup")) {

							String rKind = right.get("kind").getAsString();
							//System.out.println("right_kind: " + rKind);

							if(right.has("what")) {
								//System.out.println("---- WHAT ----");
								JsonObject rightElem = right.get("what").getAsJsonObject();
								//System.out.println("what_kind: " + rightElem.get("kind").getAsString());
								//System.out.println("what_name: " + rightElem.get("name").getAsString());

								String temp = rightElem.get("name").getAsString();

								if( temp.equals("_POST") || temp.equals("_GET") ||temp.equals("_COOKIE")) {
									_entryPoint = temp;
									usedEntry = left.get("name").getAsString();
									//System.out.println("_entryPointAfter: " + _entryPoint );
								}		
							}
							if(right.has("offset")) {
								//System.out.println("---- OFFSET ----");
								JsonObject offset = right.get("offset").getAsJsonObject();
								_entryPointArg = offset.get("value").getAsString();
								//System.out.println("_entryPointArg: " +_entryPointArg );
							}
						}

						if(right.get("kind").getAsString().equals("call")) {
							//System.out.println("---- CALL ----");

							JsonObject rightElem = right.get("what").getAsJsonObject();
							String fName = rightElem.get("name").getAsString();
							//System.out.println("call_func: "+ fName);

							if(right.has("arguments")) {
								JsonArray call_args = right.get("arguments").getAsJsonArray();
								for(int l = 0; l < call_args.size();l++) {
									JsonObject arg = call_args.get(l).getAsJsonObject();
									//System.out.println("call_args["+l+"]: "+ arg.get("name"));
								}
							}
							calledFunctions.add(fName);
						}
						if(right.get("kind").getAsString().equals("encapsed")) {
							//System.out.println("----- Encapsed_node -----");

							Encapsed_node newEncapsed = new Encapsed_node(right.get("kind").getAsString());

							if(right.has("type")){
								String type = right.get("type").getAsString();
								newEncapsed.setType(type);
								//System.out.println("type: "+ type);

							}
							if(right.has("value")) {
								//System.out.println("----- value vector: ------");
								JsonArray encapsed_values = right.get("value").getAsJsonArray();
								ArrayList<String> vars = new ArrayList<String>();
								for(int l = 0; l < encapsed_values.size();l++) {

									JsonObject arg = encapsed_values.get(l).getAsJsonObject();

									if(arg.get("kind").getAsString().equals("Variable_node")){


										//System.out.println("encapsed_variable_name["+l+"]: "+ arg.get("name"));
									}else if(arg.get("kind").getAsString().equals("string")) {

										//System.out.println("encapsed_string_value["+l+"]: "+ arg.get("value"));
									}
								}
							}
						}

					}
				}*/
			}
			//System.out.println("The Variable_node used for user input is: "+"["+ _entryPoint+"]");
			//System.out.println("The entry used is: " +"["+usedEntry+"]");
			//System.out.println("The called functions are: " + calledFunctions);
			//System.out.print("The used variables are: ");
			
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




}
