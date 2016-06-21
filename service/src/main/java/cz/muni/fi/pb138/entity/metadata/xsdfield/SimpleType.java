package cz.muni.fi.pb138.entity.metadata.xsdfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Michal Holic on 21/06/2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleType {

	@XmlValue
	private String simpleType;

	public SimpleType() {
	}

	public SimpleType(String simpleType) {
		this.simpleType = simpleType;
	}

	public String getSimpleType() {
		return simpleType;
	}

	public void setSimpleType(String simpleType) {
		this.simpleType = simpleType;
	}
}
