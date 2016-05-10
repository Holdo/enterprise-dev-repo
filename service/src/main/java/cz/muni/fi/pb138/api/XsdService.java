/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.service.processing.entity.NameVersionPair;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import cz.muni.fi.pb138.service.processing.entity.xsd.ComplexType;
import java.util.List;

/**
 *
 * @author gasior
 */
public interface XsdService {
    // IF VERSION IS NOT SPECIFIED, LAST VERSION IS USED
    
    // THESE methods should be implemented to 13.05.
    public void SaveXsdFile(String name, String textFile);
    public void DeleteXsdFile(String name);
    public XsdFile GetXsdFileByName(String name);
    public List<String> GetAllXsdFileNames();
    
    // THESE can wait
    public List<Integer> GetXsdFileVersions(String name);
    public void DeleteXsdFile(String name, int version);
    public XsdFile GetXsdFileByNameAndVersion(String name, int version);
    
    
    public List<String> GetAllElements();
    public List<String> GetElementsByFileName(String name);
    public List<String> GetElementsByFileName(String name, int version);
    
    public List<String> GetAllAttributes();
    public List<String> GetAttributesByFileName(String name);
    public List<String> GetAttributesByFileName(String name, int version);
    
    public List<String> GetAllSimpleTypes();
    public List<String> GetSimpleTypesByFileName(String name);
    public List<String> GetSimpleTypesByFileName(String name, int version);
    
    public List<ComplexType> GetAllComplexTypes();
    public List<ComplexType> GetComplexTypesByFileName(String name);
    public List<ComplexType> GetComplexTypesByFileName(String name, int version);
  
    public List<NameVersionPair> GetXsdFileNamesBySimpleType(String simpleTypeName);
    public List<NameVersionPair> GetXsdFileNamesByAttribute(String attributeName);
    public List<NameVersionPair> GetXsdFileNamesByElement(String elementName);
    public List<NameVersionPair> GetXsdFileNamesByComplexType(String complexTypeName);
}
