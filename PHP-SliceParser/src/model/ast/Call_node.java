package model.ast;

import java.util.ArrayList;

public class Call_node extends Statement_node {

	public String _funcName;
	public ArrayList<Expression_node> _args;

	public Call_node(String kind, String funcName, ArrayList<Expression_node> args) {
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

	public ArrayList<Expression_node> get_args() {
		return _args;
	}

	public void set_args(ArrayList<Expression_node> _args) {
		this._args = _args;
	}
	
	public String toString() {
		String s = "\nCall: " + _funcName;
		String v = "";
		for(int i = 0; i< _args.size();i++) {
		   v +=  _args.get(i).toString();
		}
		return s +"\n"+v; 
	}
}


