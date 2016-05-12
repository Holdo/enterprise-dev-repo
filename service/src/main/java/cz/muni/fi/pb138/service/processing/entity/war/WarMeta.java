/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.war;

import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "warMeta")
public class WarMeta {
    private PathVersionPair nameVersionPair;
    private List<String> listenerList;
    private List<String> filterList;


    public WarMeta(PathVersionPair nameVersionPair, List<String> listenerList, List<String> filterList) {
        this.nameVersionPair = nameVersionPair;
        this.listenerList = listenerList;
        this.filterList = filterList;
    }

    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }

    public List<String> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<String> listenerList) {
        this.listenerList = listenerList;
    }

    public List<String> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<String> filterList) {
        this.filterList = filterList;
    }
}
