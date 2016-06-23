package cz.muni.fi.pb138.entity.war;

import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author Ondřej Gasior
 */
@XmlRootElement( name = "warmeta")
public class WarMeta {
    private VersionedFile pathVersionPair;
    private List<String> listener;
    private List<String> filter;
    private FileType type;

    public WarMeta() {
    }

    public WarMeta(VersionedFile pathVersionPair, List<String> listenerList, List<String> filterList) {
        this.pathVersionPair = pathVersionPair;
        this.listener = listenerList;
        this.filter = filterList;
        type = FileType.WAR;
    }

    public VersionedFile getPathVersionPair() {
        return pathVersionPair;
    }

    public void setPathVersionPair(VersionedFile nameVersionPair) {
        this.pathVersionPair = pathVersionPair;
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
