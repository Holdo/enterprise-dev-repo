package cz.muni.fi.pb138.entity.metadata.xsdfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Michal Holic on 21/06/2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Element {

	@XmlValue
	private String element;

	public Element() {
	}

	public Element(String element) {
		this.element = element;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}
}