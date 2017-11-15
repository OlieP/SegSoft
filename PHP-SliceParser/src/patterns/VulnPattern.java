package patterns;

import java.util.ArrayList;

public class VulnPattern {
	private String _vulnerabilityName;
	private ArrayList<String> _entryPoints;
	private ArrayList<String> _saninitizationFuncs;
	private ArrayList<String> _sensitiveSinks;
	
	public VulnPattern() {
		// TODO Auto-generated constructor stub
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

	public ArrayList<String> get_saninitizationFuncs() {
		return _saninitizationFuncs;
	}

	public void set_saninitizationFuncs(ArrayList<String> _saninitizationFuncs) {
		this._saninitizationFuncs = _saninitizationFuncs;
	}

	public ArrayList<String> get_sensitiveSinks() {
		return _sensitiveSinks;
	}

	public void set_sensitiveSinks(ArrayList<String> _sensitiveSinks) {
		this._sensitiveSinks = _sensitiveSinks;
	}

}
