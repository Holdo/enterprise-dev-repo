package cz.muni.fi.pb138.entity.metadata;

import cz.muni.fi.pb138.entity.metadata.warfield.Filter;
import cz.muni.fi.pb138.entity.metadata.warfield.Listener;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by gasior on 22.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "warmeta")
public class WARMetas extends Metas {

    @XmlElement(name = "listener")
    private Listener[] listeners;

    @XmlElement(name = "request")
    private Filter[] filters;

    public WARMetas() {
    }

    public WARMetas(Filter[] filters, Listener[] listeners) {
        this.filters = filters;
        this.listeners = listeners;
    }

    public Listener[] getListeners() {
        return listeners;
    }

    public void setListeners(Listener[] listeners) {
        this.listeners = listeners;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }


}
