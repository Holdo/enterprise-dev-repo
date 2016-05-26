/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.wsdl;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "wsdlmeta")
public class WsdlMeta {
    private FileType type;
    private PathVersionPair nameVersionPair;
    private List<String> operation;
    private List<String> response;
    private List<String> request;

    public WsdlMeta() {
    }

    public WsdlMeta(PathVersionPair nameVersionPair, List<String> operations, List<String> responses, List<String> requests) {
        this.nameVersionPair = nameVersionPair;
        this.operation = operations;
        this.response = responses;
        this.request = requests;
        type = FileType.WSDL;

    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

    public List<String> getRequest() {
        return request;
    }

    public void setRequest(List<String> request) {
        this.request = request;
    }
}
