/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.service.processing.entity.WarFile;
import java.util.List;

/**
 *
 * @author gasior
 */
public interface WarService {
    
    
    // THESE methods should be implemented to 13.05.
    public void SaveWarFile(String name, byte[] fileBytes);
    public void DeleteWarFile(String name);
    public WarFile GetWarFileByName(String name);
    public List<String> GetAllWarFileNames();
    
    
    // THESE can wait
    public List<String> GetAllWebXmls();
    public String GetWebXmlByFileName(String name);
    public List<String> GetAllFilters();
    public List<String> GetFiltersByFileName(String name);
    public List<String> GetAllListeners();
    public List<String> GetListenersByFileName(String name);
    
    
    // DO NOT implement these methods FOR NOW
    public List<String> GetWarFileNamesByListener(String listener);
    public List<String> GetWarFileNamesByFilter(String listener);
    public List<String> GetWarFileNamesByWebXml(String webXml);
    
}
