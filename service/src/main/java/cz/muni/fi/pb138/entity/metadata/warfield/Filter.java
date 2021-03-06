package cz.muni.fi.pb138.entity.metadata.warfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Ondřej Gasior
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {

    @XmlValue
    private String filter;

    public Filter(String filter) {
        this.filter = filter;
    }

    public Filter() {
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
