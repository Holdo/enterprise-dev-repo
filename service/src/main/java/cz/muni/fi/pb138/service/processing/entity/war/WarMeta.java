package cz.muni.fi.pb138.service.processing.entity.war;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "warmeta")
public class WarMeta {
    private PathVersionPair nameVersionPair;
    private List<String> listener;
    private List<String> filter;
    private FileType type;

    public WarMeta() {
    }

    public WarMeta(PathVersionPair nameVersionPair, List<String> listenerList, List<String> filterList) {
        this.nameVersionPair = nameVersionPair;
        this.listener = listenerList;
        this.filter = filterList;
        type = FileType.WAR;
    }

    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }

    public List<String> getListener() {
        return listener;
    }

    public void setListener(List<String> listener) {
        this.listener = listener;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
