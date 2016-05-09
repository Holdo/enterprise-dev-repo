/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import java.util.List;

/**
 *
 * @author gasior
 */
public interface WsdlService {
    
     // THESE methods should be implemented to 13.05.
    public void SaveWsdlFile(String name, String textFile);
    public void DeleteWsdlFile(String name);
    public WsdlFile GetWsdlFileByName(String name);
    public List<WsdlFile> GetAllWsdlFiles();
    
    // THESE can wait
    public List<String> GetAllOperations();
    public List<String> GetOperationsByFileName(String name);
    public List<String> GetAllRequests();
    public List<String> GetRequestsByFileName(String name);
    public List<String> GetAllResponses();
    public List<String> GetResponsesByFileName(String name);
    
    
    // DO NOT implement these methods FOR NOW
    public List<WsdlFile> GetWsdlFilesByOperation(String operation);
    public List<WsdlFile> GetWsdlFilesByRequest(String request);
    public List<WsdlFile> GetWsdlFilesByResponses(String responses);
}
