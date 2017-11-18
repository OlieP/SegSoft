package model.ast;

import java.util.ArrayList;

public class Block_node extends Statement_node {

	private ArrayList<Node> _children;

	public Block_node(String kind) {
		super(kind);
		_children = new ArrayList<Node>();
	}

	public ArrayList<Node> get_cildren() {
		return _children;
	}

	public void set_cildren(ArrayList<Node> _cildren) {
		this._children = _cildren;
	}

	public ArrayList<Statement_node> get_funcCall_child() {
		ArrayList<Statement_node> var = new ArrayList<Statement_node>();
		for(int i = 0; i< _children.size();i++) {
			if(_children.get(i).get_kind().equals("call")){
				Statement_node v = (Call_node) _children.get(i);
				var.add(v);
			}
		}
		return var;
	}

	public ArrayList<Expression_node> get_variable_child() {
		ArrayList<Expression_node> var = new ArrayList<Expression_node>();
		for(int i = 0; i< _children.size();i++) {
			if(_children.get(i).get_kind().equals("variable")  ){
				Variable_node v = (Variable_node) _children.get(i);
				//System.out.println("Variable: "+v.getName() + " added....");
				var.add(v);
			}
			if( _children.get(i).get_kind().equals("offsetlookup")) {
				OffsetLookup_node v = (OffsetLookup_node) _children.get(i);
				//System.out.println("Variable: "+ v.get_what().toString() + " added....");
				var.add(v);
			}
		}
		return var;
	}

	public ArrayList<Expression_node> get_offsetlookup_child() {
		ArrayList<Expression_node> var = new ArrayList<Expression_node>();
		for(int i = 0; i< _children.size();i++) {
			if(_children.get(i).get_kind().equals("offsetlookup")){
				Expression_node v = (Lookup_node) _children.get(i);
				var.add(v);
			}
		}
		return var;
	}




	public String toString() {
		String s = "Block: \n";
		for(int i = 0; i< _children.size();i++) {
			s +=  _children.get(i).toString() ;
		}
		return s; 
	}

}
