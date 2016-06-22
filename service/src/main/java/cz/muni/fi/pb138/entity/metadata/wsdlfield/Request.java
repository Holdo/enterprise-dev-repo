package cz.muni.fi.pb138.entity.metadata.wsdlfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Ondřej Gasior
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {

    @XmlValue
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Request(String request) {
        this.request = request;
    }

    public Request() {
    }
}
