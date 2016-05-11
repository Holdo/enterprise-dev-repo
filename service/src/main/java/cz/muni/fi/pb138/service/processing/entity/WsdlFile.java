/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import java.util.List;

/**
 * Pro WSDL dokumenty se vyextrahuje seznam operací spolu s informací o requestu a response zprávách
 * @author gasior
 */
public class WsdlFile implements FileBase {
    
    
    private NameVersionPair nameVersionPair;
    private byte[] file;
    private List<String> operations;
    private List<String> responses;
    private List<String> requests;

    public WsdlFile() {
    }

    public WsdlFile(NameVersionPair nameVersionPair, byte[] file, List<String> operations, List<String> responses, List<String> requests) {
        this.nameVersionPair = nameVersionPair;
        this.file = file;
        this.operations = operations;
        this.responses = responses;
        this.requests = requests;
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
    public byte[] getMeta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public byte[] getFile() {
        return file;
    }

    @Override
    public String getFilePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMetaPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    
}
