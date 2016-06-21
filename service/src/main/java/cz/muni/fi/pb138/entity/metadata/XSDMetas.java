package cz.muni.fi.pb138.entity.metadata;

import cz.muni.fi.pb138.entity.metadata.xsdfield.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Michal Holic on 21/06/2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xsdmeta")
public class XSDMetas extends Metas {

	@XmlElement(name = "attribute")
	private Attribute[] attributes;

	@XmlElement(name = "element")
	private Element[] elements;

	@XmlElement(name = "complexType")
	private ComplexType[] complexTypes;

	@XmlElement(name = "simpleType")
	private SimpleType[] simpleTypes;

	public XSDMetas() {
	}

	public XSDMetas(Attribute[] attributes, Element[] elements, ComplexType[] complexTypes, SimpleType[] simpleTypes) {
		this.attributes = attributes;
		this.elements = elements;
		this.complexTypes = complexTypes;
		this.simpleTypes = simpleTypes;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	public void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}

	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element[] elements) {
		this.elements = elements;
	}

	public ComplexType[] getComplexTypes() {
		return complexTypes;
	}

	public void setComplexTypes(ComplexType[] complexTypes) {
		this.complexTypes = complexTypes;
	}

	public SimpleType[] getSimpleTypes() {
		return simpleTypes;
	}

	public void setSimpleTypes(SimpleType[] simpleTypes) {
		this.simpleTypes = simpleTypes;
	}
}
