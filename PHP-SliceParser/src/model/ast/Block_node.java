package model.ast;

import java.util.ArrayList;

public class Block_node extends Statement_node {

	private ArrayList<Node> _cildren;
	
	public Block_node(String kind) {
		super(kind);
		_cildren = null;
	}
	
	public ArrayList<Node> get_cildren() {
		return _cildren;
	}

	public void set_cildren(ArrayList<Node> _cildren) {
		this._cildren = _cildren;
	}


}
