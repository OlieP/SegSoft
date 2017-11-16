package patterns;

import java.util.ArrayList;

public class VulnPattern {
	private String _vulnerabilityName;
	private ArrayList<String> _entryPoints;
	private ArrayList<String> _sanitizationFuncs;
	private ArrayList<String> _sensitiveSinks;
	
	public VulnPattern() {
		_vulnerabilityName = new String();
		_entryPoints = new ArrayList<String>();
		_sanitizationFuncs = new ArrayList<String>();
		_sensitiveSinks = new ArrayList<String>();
	}

	public String get_vulnerabilityName() {
		return _vulnerabilityName;
	}
	public void set_vulnerabilityName(String _vulnerabilityName) {
		this._vulnerabilityName = _vulnerabilityName;
	}

	public ArrayList<String> get_entryPoints() {
		return _entryPoints;
	}

	public void set_entryPoints(ArrayList<String> _entryPoints) {
		this._entryPoints = _entryPoints;
	}
	public void add_entryPoint(String s) {
		this._entryPoints.add(s);
	}

	public ArrayList<String> get_saninitizationFuncs() {
		return _sanitizationFuncs;
	}
	public void add_sanitization(String s) {
		this._sanitizationFuncs.add(s);
	
	}
	public void set_saninitizationFuncs(ArrayList<String> _sanitizationFuncs) {
		this._sanitizationFuncs = _sanitizationFuncs;
	}
	public ArrayList<String> get_sensitiveSinks() {
		return _sensitiveSinks;
	}
	public void set_sensitiveSinks(ArrayList<String> _sensitiveSinks) {
		this._sensitiveSinks = _sensitiveSinks;
	}
	public void add_sensitiveSink(String s) {
		this._sensitiveSinks.add(s);
	}

	

}
