/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.wsdl.WsdlMeta;
import cz.muni.fi.pb138.service.processing.entity.xsd.XsdMeta;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pro WSDL dokumenty se vyextrahuje seznam operací spolu s informací o requestu a response zprávách
 * @author gasior
 */
public class WsdlFile implements FileBase {
    
    
    private PathVersionPair nameVersionPair;
    private byte[] file;
    private List<String> operations;
    private List<String> responses;
    private List<String> requests;
    private FileType type;
    public WsdlFile() {
        type = FileType.WSDL;
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

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }

    @Override
    public byte[] getMeta() throws JAXBException {
            JAXBContext jc;
            Marshaller marshaller;
            jc = JAXBContext.newInstance(WsdlMeta.class);
            marshaller = jc.createMarshaller();

            StringWriter sw = new StringWriter();
            marshaller.marshal(new WsdlMeta(nameVersionPair, operations, responses, requests), sw);
            String xmlString = sw.toString();
            return xmlString.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public FileType getType() {
        return type;
    }

    @Override
    public HashMap<MetaFileType, byte[]> getMetaFiles() {
        return new HashMap<>();
    }


    @Override
    public byte[] getFile() {
        return file;
    }

    @Override
    public void setVersion(int version) {
        nameVersionPair.setVersion(version);
    }
}
