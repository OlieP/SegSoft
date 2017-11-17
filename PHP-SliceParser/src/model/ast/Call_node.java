package model.ast;

import java.util.ArrayList;

public class Call_node extends Statement_node {

	public String _funcName;
	public ArrayList<String> _args;

	public Call_node(String kind, String funcName, ArrayList<String> args) {
		super(kind);
		_funcName = funcName;
		_args = args;
	}

	public String get_funcName() {
		return _funcName;
	}

	public void set_funcName(String _funcName) {
		this._funcName = _funcName;
	}

	public ArrayList<String> get_args() {
		return _args;
	}

	public void set_args(ArrayList<String> _args) {
		this._args = _args;
	}

}


