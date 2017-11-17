package model.ast;

import java.util.ArrayList;

public class Block_node extends Statement_node {

	private ArrayList<Node> _children;
	
	public Block_node(String kind) {
		super(kind);
		_children = null;
	}
	
	public ArrayList<Node> get_cildren() {
		return _children;
	}

	public void set_cildren(ArrayList<Node> _cildren) {
		this._children = _cildren;
	}

	public String toString() {
		String s = "Block: \n";
		for(int i = 0; i< _children.size();i++) {
			 s +=  _children.get(i).toString() ;
		}
		return s; 
	}
	
}
