package model;

import java.util.ArrayList;

import model.ast.Node;

public class ProgramSlice {
	private String _kind;
	private ArrayList<Node> _children;
	
	public ProgramSlice() {
		_kind = "";
		_children = new ArrayList<Node>();
	}

	public String get_kind() {
		return _kind;
	}

	public void set_kind(String _kind) {
		this._kind = _kind;
	}

	public ArrayList<Node> getChildren() {
		return _children;
	}
	public void addChild(Node child) {
		this._children.add(child);
	}
	public void setChildren(ArrayList<Node> children) {
		this._children = children;
	}

}
