package cz.muni.fi.pb138.entity.metadata.xsdfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Michal Holic on 21/06/2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ComplexType {

	@XmlValue
	private String complexType;

	public ComplexType() {
	}

	public ComplexType(String complexType) {
		this.complexType = complexType;
	}

	public String getComplexType() {
		return complexType;
	}

	public void setComplexType(String complexType) {
		this.complexType = complexType;
	}
}
