package model.ast;

public class Node {
 
	protected String _kind;

	public Node(){}
	
	public Node(String kind) {
		_kind = kind;
	}
	
	public String get_kind() {
		return _kind;
	}

	public void set_kind(String _kind) {
		this._kind = _kind;
	}

}
