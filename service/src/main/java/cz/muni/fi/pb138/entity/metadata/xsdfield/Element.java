package cz.muni.fi.pb138.entity.metadata.xsdfield;

import cz.muni.fi.pb138.entity.metadata.Name;
import cz.muni.fi.pb138.entity.metadata.Parent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Michal Holič
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Element {

	private Name name;
	private Parent parent;

	public Element(Name name, Parent parent) {
		this.name = name;
		this.parent = parent;
	}

	public Element() {
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
}
