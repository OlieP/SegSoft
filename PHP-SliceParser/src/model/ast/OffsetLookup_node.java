package model.ast;

public class OffsetLookup_node extends Lookup_node {

	private Expression_node _what;
	private Expression_node _offset;
	
	public OffsetLookup_node(String kind,Expression_node what, Expression_node offset) {
		super(kind,what,offset);
		this._what = what;
		this._offset = offset;
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

	public String toString() {
		String s = "what: \n" + _what.toString() + "offset: " + _offset.toString();
		return "offsetlookup: \n" + s;
	}

}
