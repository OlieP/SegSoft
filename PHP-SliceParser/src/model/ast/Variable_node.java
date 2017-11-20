package model.ast;

public class Variable_node extends Expression_node{

	private String _name;
	private Boolean vulnerable;
	
	public Variable_node(String kind,String name) {
		super(kind);
		_name = name;
		vulnerable= false;
	}
	
	public Boolean getVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(Boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public String toString() {
		return "variable: \n kind: "+_kind +"\n name: " +_name +"\n";
	}

}
