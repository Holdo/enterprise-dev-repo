package cz.muni.fi.pb138.entity.metadata.xsdfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Michal Holic on 21/06/2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute {

	@XmlValue
	private String attribute;

	public Attribute() {
	}

	public Attribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
