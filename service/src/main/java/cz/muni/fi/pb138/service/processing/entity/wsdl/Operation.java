/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.wsdl;

import java.util.List;

/**
 *
 * @author gasior
 */
public class Operation {
    
    private String name;
    private List<String> responses;
    private List<String> requests;

    public Operation() {
    }

    public Operation(String name, List<String> responses, List<String> requests) {
        this.name = name;
        this.responses = responses;
        this.requests = requests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
