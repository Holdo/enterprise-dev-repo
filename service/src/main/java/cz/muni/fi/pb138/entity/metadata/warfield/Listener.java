package cz.muni.fi.pb138.entity.metadata.warfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Ondřej Gasior
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Listener {

    @XmlValue
    private String listener;

    public Listener() {
    }

    public Listener(String listener) {
        this.listener = listener;
    }

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }
}
