package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by gasior on 26.06.2016.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Parent {
    @XmlElement(name = "parent")
    private String parent;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Parent(String parent) {
        this.parent = parent;
    }

    public Parent() {
    }
}
