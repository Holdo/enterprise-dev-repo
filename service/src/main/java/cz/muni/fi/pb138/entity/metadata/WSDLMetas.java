package cz.muni.fi.pb138.entity.metadata;

import cz.muni.fi.pb138.entity.metadata.wsdlfield.Operation;
import cz.muni.fi.pb138.entity.metadata.wsdlfield.Request;
import cz.muni.fi.pb138.entity.metadata.wsdlfield.Response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ondřej Gasior
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "wsdlmeta")
public class WSDLMetas extends Metas {

    @XmlElement(name = "operation")
    private Operation[] operations;

    @XmlElement(name = "request")
    private Request[] requests;

    @XmlElement(name = "response")
    private Response[] responses;

    public WSDLMetas() {
    }

    public WSDLMetas(Operation[] operations, Request[] requests, Response[] responses) {
        this.operations = operations;
        this.requests = requests;
        this.responses = responses;
    }

    public Operation[] getOperations() {
        return operations;
    }

    public void setOperations(Operation[] operations) {
        this.operations = operations;
    }

    public Request[] getRequests() {
        return requests;
    }

    public void setRequests(Request[] requests) {
        this.requests = requests;
    }

    public Response[] getResponses() {
        return responses;
    }

    public void setResponses(Response[] responses) {
        this.responses = responses;
    }
}
