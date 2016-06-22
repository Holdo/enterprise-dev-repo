package cz.muni.fi.pb138.entity.metadata.wsdlfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by gasior on 22.06.2016.
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
