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
public interface WarService {
    
    public List<String> getAllWarFileFullPaths();
    
    public List<byte[]> getAllWebXmls();
    public byte[] getWebXmlByFileFullPath(String FullPath);
    
    public List<String> getAllFilters();
    public List<String> getFiltersByFileFullPath(String FullPath);
    public List<String> getAllListeners();
    public List<String> getListenersByFileFullPath(String FullPath);
    
    public List<NameVersionPair> getWarFileFullPathsByListener(String listener);
    public List<NameVersionPair> getWarFileFullPathsByFilter(String listener);
   // ?? public List<NameVersionPair> getWarFileFullPathsByWebXml(String webXml);
    
}
