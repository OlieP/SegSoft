import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.ast.Block_node;
import model.ast.Call_node;
import model.ast.Encapsed_node;
import model.ast.Expression_node;
import model.ast.Node;
import model.ast.OffsetLookup_node;
import model.ast.String_node;
import model.ast.Variable_node;


public class SliceParser {

	ArrayList<String> _variables; 
	List<String> _sanitizationUsed;
	List<String> _sensitive_sinks; 

	public SliceParser() {
		_variables = new ArrayList<String>();
		_sanitizationUsed =  new ArrayList<String>();
		_sensitive_sinks =  new ArrayList<String>();
	}

	public static void main(String[] args) {
		SliceParser parser = new SliceParser();
		parser.mainParser(args[0]);
		PatternScanner scanner = new PatternScanner();
		scanner.readPatterns();
		scanner.printPatterns();
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

				ArrayList<Node> left_childs = new ArrayList<Node>();
				ArrayList<Node> right_childs = new ArrayList<Node>();

				for (int i = 0; i < children.size(); i++) {
					child = children.get(i).getAsJsonObject();
					String child_kind =  child.get("kind").getAsString();

					if(child.has("left")) {
						left_child = child.get("left").getAsJsonObject();
						String left_kind =  left_child.get("kind").getAsString();
						String var_name ="";

						if(left_kind.equals("variable")) {	
							var_name = left_child.get("name").getAsString();
							Variable_node v = new Variable_node(left_kind,var_name);
							left_childs.add(v);
						}
					}
					if(child.has("right")) {

						right_child = child.get("right").getAsJsonObject();
						String right_child_kind = right_child.get("kind").getAsString();
						
						if(right_child_kind.equals("offsetlookup")) {
							JsonObject what = right_child.get("what").getAsJsonObject();
							String what_kind = what.get("kind").getAsString();
							String what_name = what.get("name").getAsString();

							Variable_node what_var = new Variable_node(what_kind, what_name);
							String_node offset_str = null;

							if(right_child.has("offset")) {
								offset = right_child.get("offset").getAsJsonObject();
								String offset_kind = offset.get("kind").getAsString();
								String offset_value = offset.get("value").getAsString();

								offset_str = new String_node(offset_kind,offset_value);
							}

							OffsetLookup_node offLk = new OffsetLookup_node("offsetlookup", what_var, offset_str);
							right_childs.add(offLk);
						}

						if(right_child_kind.equals("encapsed")) {
							String kind =  right_child.get("kind").getAsString();
							JsonArray value = right_child.get("value").getAsJsonArray();
							String type = right_child.get("type").getAsString();

							Encapsed_node enc = new Encapsed_node(kind,type);

							ArrayList<Expression_node> encapsedValues = new ArrayList<Expression_node>();

							for(int j = 0; j < value.size(); j++) {
								JsonObject val1 = (JsonObject) value.get(j);
								String val_kind = val1.get("kind").getAsString();

								if(val_kind.equals("string")) {
									String str_value = val1.get("value").getAsString();
									Expression_node aux =  new String_node(val_kind,str_value);
									encapsedValues.add(aux);

								}else if(val_kind.equals("variable")) {
									String var_name = val1.get("name").getAsString();
									Expression_node aux =  new Variable_node(val_kind,var_name);
									encapsedValues.add(aux);
								}

							}
							enc.setVariables(encapsedValues);
							right_childs.add(enc);
						}

						if(right_child_kind.equals("call"))
						{
							String kind =  right_child.get("kind").getAsString();
							JsonObject what = right_child.get("what").getAsJsonObject();
							String what_kind = what.get("kind").getAsString();
							String what_name = what.get("name").getAsString();

							ArrayList<Expression_node> funcArgsList = new ArrayList<Expression_node>();

							if(right_child.has("arguments")) {

								funcArgs = right_child.get("arguments").getAsJsonArray();

								for(int b =0; b < funcArgs.size(); b++) {

									JsonObject arg = funcArgs.get(b).getAsJsonObject();
									String arg_kind = arg.get("kind").getAsString();
									String arg_name = arg.get("name").getAsString();

									Variable_node var = new Variable_node(arg_kind,arg_name);

									funcArgsList.add(var);
								}
							}

							Call_node call = new Call_node(what_kind, what_name,funcArgsList);
							right_childs.add(call);
						}
					}
					//System.out.println("\n------- Next Child -------\n");
				}
				//coloca os filhos esquerdos e direitos no mesmo array... pode nao ser preciso.
				ArrayList<Node> newListChilds = new ArrayList<Node>(left_childs);
				newListChilds.addAll(right_childs);
				
				block_node.set_cildren(newListChilds); //o bloco tem todos os filhos do programa

				System.out.println(block_node.toString());

			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
