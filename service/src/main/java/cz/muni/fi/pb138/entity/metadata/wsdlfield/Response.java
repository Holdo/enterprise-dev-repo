package cz.muni.fi.pb138.entity.metadata.wsdlfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by gasior on 22.06.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {

    @XmlValue
    private String response;

    public Response() {
    }

    public Response(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
