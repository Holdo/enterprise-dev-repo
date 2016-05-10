/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import java.util.HashMap;
import java.util.List;

/**
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam elementů a atributů
 * @author gasior
 */
public class XsdFile {
    
    private String fileName;
    private String fileText;
    private List<String> simpleTypes;
    private List<String> complexTypes;
    private HashMap<String, List<String>> elementAttributesMap;

    public XsdFile() {
    }

    public XsdFile(String fileName, String fileText, List<String> simpleTypse, List<String> complexTypes, HashMap<String, List<String>> elementAttributesMap) {
        this.fileName = fileName;
        this.fileText = fileText;
        this.simpleTypes = simpleTypse;
        this.complexTypes = complexTypes;
        this.elementAttributesMap = elementAttributesMap;
    }
    
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public List<String> getSimpleTypse() {
        return simpleTypes;
    }

    public void setSimpleTypes(List<String> simpleTypse) {
        this.simpleTypes = simpleTypse;
    }

    public List<String> getComplexTypes() {
        return complexTypes;
    }

    public void setComplexTypes(List<String> complexTypes) {
        this.complexTypes = complexTypes;
    }

    public HashMap<String, List<String>> getElementAttributesMap() {
        return elementAttributesMap;
    }

    public void setElementAttributesMap(HashMap<String, List<String>> elementAttributesMap) {
        this.elementAttributesMap = elementAttributesMap;
    }

}
