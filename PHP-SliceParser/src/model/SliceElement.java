package model;

import java.util.ArrayList;

public class SliceElement {
	private String _kind;
	private String _name;
	private String _value;
	private String _func_name;

	public SliceElement() {
		_kind = "";
		_name = "";
		_value = "";
		_func_name = "";
	}

	public String getKind() {
		return _kind;
	}
	public void setKind(String kind) {
		this._kind = kind;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}

	public String get_value() {
		return _value;
	}

	public void set_value(String _value) {
		this._value = _value;
	}

	public String get_func_name() {
		return _func_name;
	}

	public void set_func_name(String _func_name) {
		this._func_name = _func_name;
	}

	public void print() {
		String s = "{\\kind = " + getKind() + "\\name = "+getName();
		
		if(!(_value.equals(""))) {
			System.out.println(s + "\\value" + get_value());
		}
	}
	
	

	public class OffsetLookup{
		public String _name;
		public String _offsetValue;

		public OffsetLookup(String name, String offsetValue) {
			_name = name;
			_offsetValue = offsetValue;
		}

		public String get_name() {
			return _name;
		}

		public void set_name(String _name) {
			this._name = _name;
		}

		public String get_offsetValue() {
			return _offsetValue;
		}

		public void set_offsetValue(String _offsetValue) {
			this._offsetValue = _offsetValue;
		}

	}
		
}
