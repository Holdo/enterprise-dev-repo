/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import java.util.HashMap;
import java.util.List;

/**
 * Pro WSDL dokumenty se vyextrahuje seznam operací spolu s informací o requestu a response zprávách
 * @author gasior
 */
public class WsdlFile {
    
    private String fileName;
    private String fileText;
    private List<String> operations;
    private HashMap<String, List<String>> operationRequestMap;
    private HashMap<String, List<String>> operationResponseMap;

    public WsdlFile() {
    }

    public WsdlFile(String fileName, String fileText, List<String> operations, HashMap<String, List<String>> operationRequestMap, HashMap<String, List<String>> operationResponseMap) {
        this.fileName = fileName;
        this.fileText = fileText;
        this.operations = operations;
        this.operationRequestMap = operationRequestMap;
        this.operationResponseMap = operationResponseMap;
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

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public HashMap<String, List<String>> getOperationRequestMap() {
        return operationRequestMap;
    }

    public void setOperationRequestMap(HashMap<String, List<String>> operationRequestMap) {
        this.operationRequestMap = operationRequestMap;
    }

    public HashMap<String, List<String>> getOperationResponseMap() {
        return operationResponseMap;
    }

    public void setOperationResponseMap(HashMap<String, List<String>> operationResponseMap) {
        this.operationResponseMap = operationResponseMap;
    }

 
    
}
