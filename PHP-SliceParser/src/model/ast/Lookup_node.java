package model.ast;

import model.ast.Expression_node;

public class Lookup_node extends Expression_node {

	private Expression_node _what;
	private Expression_node _offset;
	
	public Lookup_node(String kind, Expression_node what, Expression_node offset) {
		super(kind);
		_what = what;
		_offset = offset;
	}

	public Expression_node get_what() {
		return _what;
	}

	public void set_what(Expression_node _what) {
		this._what = _what;
	}

	public Expression_node get_offset() {
		return _offset;
	}

	public void set_offset(Expression_node _offset) {
		this._offset = _offset;
	}

}
