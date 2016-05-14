/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.wsdl;

import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "wsdlmeta")
public class WsdlMeta {
    private PathVersionPair nameVersionPair;
    private List<String> operations;
    private List<String> responses;
    private List<String> requests;

    public WsdlMeta(PathVersionPair nameVersionPair, List<String> operations, List<String> responses, List<String> requests) {
        this.nameVersionPair = nameVersionPair;
        this.operations = operations;
        this.responses = responses;
        this.requests = requests;

    }

    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
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
}
