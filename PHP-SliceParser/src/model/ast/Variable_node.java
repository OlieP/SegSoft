package model.ast;

public class Variable_node extends Expression_node{

	private String _name;
	
	public Variable_node(String kind,String name) {
		super(kind);
		_name = name;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	

}
