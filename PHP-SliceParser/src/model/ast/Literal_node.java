package model.ast;

import java.util.ArrayList;

public class Literal_node extends Expression_node {

	private ArrayList<Node> _nodeValues;

	public Literal_node(String kind) {
		super(kind);
		_nodeValues = new ArrayList<Node>();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Node> get_nodeValues() {
		return _nodeValues;
	}

	public void set_nodeValues(ArrayList<Node> _nodeValues) {
		this._nodeValues = _nodeValues;
	}

	public String toString() {
		String s = "";
		for(int i = 0; i< _nodeValues.size(); i++) {
			s += _nodeValues.get(i).toString()+"\n";
		}
		return s;
	}
}
