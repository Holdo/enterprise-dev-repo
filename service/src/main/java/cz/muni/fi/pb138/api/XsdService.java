/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import java.util.List;

/**
 *
 * @author gasior
 */
public interface XsdService {
    
    // THESE methods should be implemented to 13.05.
    public void SaveXsdFile(String name, String textFile);
    public void DeleteXsdFile(String name);
    public XsdFile GetXsdFileByName(String name);
    public List<String> GetAllXsdFileNames();
    
    // THESE can wait
    public List<String> GetAllElements();
    public List<String> GetElementsByFileName(String name);
    public List<String> GetAllAttributes();
    public List<String> GetAttributesByFileName(String name);
    public List<String> GetAllSimpleTypes();
    public List<String> GetSimpleTypesByFileName(String name);
    public List<String> GetAllComplexTypes();
    public List<String> GetComplexTypesByFileName(String name);
    
    
    // DO NOT implement these methods FOR NOW
    public List<String> GetXsdFileNamesBySimpleType(String simpleType);
    public List<String> GetXsdFileNamesByComplexType(String complexType);
    public List<String> GetXsdFileNamesByAttribute(String attribute);
    public List<String> GetXsdFileNamesByElement(String element);
}
