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
public interface XsdService {
    
    
    // IF VERSION IS NOT SPECIFIED, LAST VERSION IS USED
   
    public List<String> getAllXsdFileFullPaths();
    public List<String> getAllElements();
    public List<String> getElementsByFileFullPath(String FullPath);
    public List<String> getElementsByFileFullPath(String FullPath, int version);
    
    public List<String> getAllAttributes();
    public List<String> getAttributesByFileFullPath(String FullPath);
    public List<String> getAttributesByFileFullPath(String FullPath, int version);
    
    public List<String> getAllSimpleTypes();
    public List<String> getSimpleTypesByFileFullPath(String FullPath);
    public List<String> getSimpleTypesByFileFullPath(String fullPath, int version);
    
    public List<String> getAllComplexTypes();
    public List<String> getComplexTypesByFileFullPath(String fullPath);
    public List<String> getComplexTypesByFileFullPath(String fullPath, int version);
  
    public List<NameVersionPair> getXsdFileFullPathsBySimpleType(String simpleTypeFullPath);
    public List<NameVersionPair> getXsdFileFullPathsByAttribute(String attributeFullPath);
    public List<NameVersionPair> getXsdFileFullPathsByElement(String elementFullPath);
    public List<NameVersionPair> getXsdFileFullPathsByComplexType(String complexTypeFullPath);
  
}
