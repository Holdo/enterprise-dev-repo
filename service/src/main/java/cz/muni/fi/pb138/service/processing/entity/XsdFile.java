/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.xsd.XsdMeta;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam
 * elementů a atributů
 *
 * @author gasior
 */
public class XsdFile implements FileBase {

    private PathVersionPair nameVersionPair;
    private byte[] file;
    private List<String> elements;
    private List<String> attributes;
    private List<String> simpleTypes;
    private List<String> complexTypes;

    public XsdFile() {
    }

    public XsdFile(PathVersionPair nameVersionPair, byte[] file, List<String> elements, List<String> attributes, List<String> simpleTypes, List<String> complexTypes) {
        this.nameVersionPair = nameVersionPair;
        this.file = file;
        this.elements = elements;
        this.attributes = attributes;
        this.simpleTypes = simpleTypes;
        this.complexTypes = complexTypes;
    }

    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }

    public void setFile(byte[] file) {
        this.file = file;
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

    public List<String> getComplexTypes() {
        return complexTypes;
    }

    public void setComplexTypes(List<String> complexTypes) {
        this.complexTypes = complexTypes;
    }



    @Override
    public byte[] getMeta() {
        JAXBContext jc;
        Marshaller marshaller;
        File xml = null;
        try {
            jc = JAXBContext.newInstance(XsdMeta.class);
            marshaller = jc.createMarshaller();
            marshaller.marshal(new XsdMeta(nameVersionPair, elements, attributes, simpleTypes, complexTypes), xml);
        } catch (JAXBException ex) {
            Logger.getLogger(XsdFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            return Files.readAllBytes(xml.toPath());
        } catch (IOException ex) {
            return null;
        }

    }

    @Override
    public byte[] getFile() {
        return file;
    }
    @Override
    public HashMap<MetaFileType, byte[]> getMetaFiles() {
        return new HashMap<>();
    }

    @Override
    public String getMetaFilePath(MetaFileType type) {
        return null;
    }

    @Override
    public String getFilePath() {
        return nameVersionPair.getFullPath();
    }

    @Override
    public String getMetaPath() {
        return nameVersionPair.getFullPath() + ".xml";
    }

}
