/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.service.processing.entity.xsd.ComplexType;
import cz.muni.fi.pb138.service.processing.entity.xsd.XsdMeta;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam
 * elementů a atributů
 *
 * @author gasior
 */
public class XsdFile implements FileBase {

    private NameVersionPair nameVersionPair;
    private byte[] file;
    private List<String> elements;
    private List<String> attributes;
    private List<String> simpleTypes;
    private List<ComplexType> complexTypes;

    public XsdFile() {
    }

    public XsdFile(NameVersionPair nameVersionPair, byte[] file, List<String> elements, List<String> attributes, List<String> simpleTypes, List<ComplexType> complexTypes) {
        this.nameVersionPair = nameVersionPair;
        this.file = file;
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

    public List<ComplexType> getComplexTypes() {
        return complexTypes;
    }

    public void setComplexTypes(List<ComplexType> complexTypes) {
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

}
