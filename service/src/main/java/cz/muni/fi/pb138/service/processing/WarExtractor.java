/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.service.processing.entity.WarFile;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasior
 */
public class WarExtractor {

    private final WarFile warFile;
    private final byte[] file;
    private final String fullPath;

    public WarExtractor(byte[] file, String fullPath) {
        this.file = file;
        this.fullPath = fullPath;
        
        warFile = new WarFile();
        warFile.setFile(file);
        warFile.setNameVersionPair(new PathVersionPair(fullPath));
        warFile.setFilterList(extractFilters());
        warFile.setListenerList(extractListeners());
        extractMetaFiles(MetaFileType.WEBXML);
    }

    private List<String> extractListeners() {
        List<String> linsteners = new ArrayList<String>();

        // TODO
        return linsteners;
    }
    private List<String> extractFilters() {
        List<String> filters = new ArrayList<String>();

        // TODO
        return filters;
    }



    private void extractMetaFiles(MetaFileType webxml) {
        // TODO
    }



    public WarFile getWarFile() {
        return warFile;
    }
 
   
}
