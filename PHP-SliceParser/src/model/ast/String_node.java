package model.ast;

public class String_node extends Expression_node{

	private String _value;
	
	public String_node(String kind,String value) {
		super(kind);
		_value = value;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		this._value = value;
	}

}
