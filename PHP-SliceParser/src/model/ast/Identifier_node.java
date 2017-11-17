package model.ast;

public class Identifier_node extends Node {

	private String _name;
	
	public Identifier_node(String kind,String name) {
		super(kind);
		// TODO Auto-generated constructor stub~
		_name = name;
	}
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}
	
	public String toString() {
		return get_name().toString();
	}
}
