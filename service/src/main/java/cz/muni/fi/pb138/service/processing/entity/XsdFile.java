/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.service.processing.entity.xsd.ComplexType;
import java.util.HashMap;
import java.util.List;

/**
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam elementů a atributů
 * @author gasior
 */
public class XsdFile {
    
    private int version;
    private String fileName;
    private String fileText;
    private List<String> simpleTypes;
    private List<ComplexType> complexTypes;

    public XsdFile() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
