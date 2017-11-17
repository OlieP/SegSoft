package model.ast;

import java.util.ArrayList;

public class Encapsed_node extends Literal_node{

	private String _type;
	private ArrayList<Expression_node> _variables;


	public Encapsed_node(String kind,String type) {
		super(kind);
		this._type = type;

	}

	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}
	public ArrayList<Expression_node> getVariables() {
		return _variables;
	}
	public void setVariables(ArrayList<Expression_node> variables) {
		this._variables = variables;
	}
}
