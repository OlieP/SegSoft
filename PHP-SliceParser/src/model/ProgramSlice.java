package model;

import java.util.ArrayList;

public class ProgramSlice {
	private String _kind;
	private String _operator;
	private ArrayList<SliceElement> _children;
	
	public ProgramSlice(String kind, String operator) {
		// TODO Auto-generated constructor stub
	}

	public String get_kind() {
		return _kind;
	}

	public void set_kind(String _kind) {
		this._kind = _kind;
	}

	public ArrayList<SliceElement> getChildren() {
		return _children;
	}

	public void setChildren(ArrayList<SliceElement> children) {
		this._children = children;
	}

	public String get_operator() {
		return _operator;
	}

	public void set_operator(String _operator) {
		this._operator = _operator;
	}
	
}
