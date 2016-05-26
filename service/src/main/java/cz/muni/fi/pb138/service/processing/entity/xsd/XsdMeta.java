/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.xsd;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "xsdmeta")
public class XsdMeta {
    
    
    private PathVersionPair pathVersionPair;
    private List<String> element;
    private List<String> attribute;
    private List<String> simpleType;
    private List<String> complexType;
    private FileType type;

    public XsdMeta() {
    }

    public XsdMeta(PathVersionPair pathVersionPair, List<String> elements, List<String> attributes, List<String> simpleTypes, List<String> complexTypes) {
        this.pathVersionPair = pathVersionPair;
        this.element = elements;
        this.attribute = attributes;
        this.simpleType = simpleTypes;
        this.complexType = complexTypes;
        type = FileType.XSD;
    }

    public PathVersionPair getPathVersionPair() {
        return pathVersionPair;
    }

    public void setPathVersionPair(PathVersionPair pathVersionPair) {
        this.pathVersionPair = pathVersionPair;
    }

    public List<String> getElement() {
        return element;
    }

    public void setElement(List<String> element) {
        this.element = element;
    }

    public List<String> getComplexType() {
        return complexType;
    }

    public void setComplexType(List<String> complexType) {
        this.complexType = complexType;
    }

    public List<String> getSimpleType() {
        return simpleType;
    }

    public void setSimpleType(List<String> simpleType) {
        this.simpleType = simpleType;
    }

    public List<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<String> attribute) {
        this.attribute = attribute;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
