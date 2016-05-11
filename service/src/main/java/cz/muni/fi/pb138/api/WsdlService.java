/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.service.processing.entity.NameVersionPair;
import java.util.List;

/**
 *
 * @author gasior
 */
public interface WsdlService {
    
    public List<String> getAllWsdlFileFullPaths();
    
    public List<String> getAllOperations();
    public List<String> getOperationsByFileFullPath(String name);
    public List<String> getOperationsByFileFullPathAndVersion(String name, int version);
    
    public List<String> getAllRequests();
    public List<String> getRequestsByFileFullPath(String name);
    public List<String> getRequestsByFileFullPathAndVersion(String name, int version);
    
    public List<String> getAllResponses();
    public List<String> getResponsesByFileFullPath(String name);
    public List<String> getResponsesByFileFullPathAndVersion(String name, int version);
    
    
    public List<NameVersionPair> getWsdlFileFullPathsByOperation(String operation);
    public List<NameVersionPair> getWsdlFileFullPathsByRequest(String request);
    public List<NameVersionPair> getWsdlFileFullPathsByResponses(String responses);
   
}
