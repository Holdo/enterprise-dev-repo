/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.service.processing.entity.xsd.ComplexType;
import java.util.List;

/**
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam elementů a atributů
 * @author gasior
 */
public class XsdFile {
    
    private NameVersionPair nameVersionPair;
    private String fileText;
    private List<String> elements;
    private List<String> attributes;
    private List<String> simpleTypes;
    private List<ComplexType> complexTypes;

    public XsdFile() {
    }

    public XsdFile(NameVersionPair nameVersionPair, String fileText, List<String> elements, List<String> attributes, List<String> simpleTypes, List<ComplexType> complexTypes) {
        this.nameVersionPair = nameVersionPair;
        this.fileText = fileText;
        this.elements = elements;
        this.attributes = attributes;
        this.simpleTypes = simpleTypes;
        this.complexTypes = complexTypes;
    }

    
    
    public NameVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(NameVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<String> getSimpleTypes() {
        return simpleTypes;
    }

    public void setSimpleTypes(List<String> simpleTypes) {
        this.simpleTypes = simpleTypes;
    }

    public List<ComplexType> getComplexTypes() {
        return complexTypes;
    }

    public void setComplexTypes(List<ComplexType> complexTypes) {
        this.complexTypes = complexTypes;
    }

   

}
