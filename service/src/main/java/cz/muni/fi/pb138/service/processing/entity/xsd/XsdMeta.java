/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.xsd;

import cz.muni.fi.pb138.service.processing.entity.NameVersionPair;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "xsdMeta")
public class XsdMeta {
    
    
    private final NameVersionPair nameVersionPair;
    private final List<String> elements;
    private final List<String> attributes;
    private final List<String> simpleTypes;
    private final List<String> complexTypes;

    public XsdMeta(NameVersionPair nameVersionPair, List<String> elements, List<String> attributes, List<String> simpleTypes, List<String> complexTypes) {
        this.nameVersionPair = nameVersionPair;
        this.elements = elements;
        this.attributes = attributes;
        this.simpleTypes = simpleTypes;
        this.complexTypes = complexTypes;
    }

    public NameVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public List<String> getElements() {
        return elements;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<String> getSimpleTypes() {
        return simpleTypes;
    }

    public List<String> getComplexTypes() {
        return complexTypes;
    }

    
    
}
