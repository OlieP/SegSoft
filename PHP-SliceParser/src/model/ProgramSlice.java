package model;

import java.util.ArrayList;

public class ProgramSlice {
	private String _kind;
	private String _operator;
	private ArrayList<Children> _children;
	
	public ProgramSlice() {
		// TODO Auto-generated constructor stub
	}

	public String get_kind() {
		return _kind;
	}

	public void set_kind(String _kind) {
		this._kind = _kind;
	}

	public ArrayList<Children> getChildren() {
		return _children;
	}

	public void setChildren(ArrayList<Children> children) {
		this._children = children;
	}

	public String get_operator() {
		return _operator;
	}

	public void set_operator(String _operator) {
		this._operator = _operator;
	}

	public class Children{
		public int n_elements;
		public int getN_elements() {
			return n_elements;
		}
		public void setN_elements(int n_elements) {
			this.n_elements = n_elements;
		}
		public String getKind() {
			return kind;
		}
		public void setKind(String kind) {
			this.kind = kind;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String kind;
		public String name;
		
	}
	
}
