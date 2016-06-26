package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by gasior on 26.06.2016.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Name {
    @XmlValue
    private String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Name() {
    }
}
