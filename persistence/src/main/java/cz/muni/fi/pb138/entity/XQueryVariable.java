package cz.muni.fi.pb138.entity;

/**
 * Created by Michal Holic on 15.05.2016
 */
public class XQueryVariable {

	private String name = null;
	private String value = null;
	private XQueryType type = null;

	public XQueryVariable(String name, String value, XQueryType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public boolean isContext() {
		return this.name == null;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public XQueryType getType() {
		return type;
	}
}