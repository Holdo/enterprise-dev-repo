package cz.muni.fi.pb138.entity.wsdl;

import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "wsdlmeta")
public class WsdlMeta {
    private FileType type;
    private VersionedFile pathVersionPair;
    private List<String> operation;
    private List<String> response;
    private List<String> request;

    public WsdlMeta() {
    }

    public WsdlMeta(VersionedFile nameVersionPair, List<String> operations, List<String> responses, List<String> requests) {
        this. pathVersionPair = nameVersionPair;
        this.operation = operations;
        this.response = responses;
        this.request = requests;
        type = FileType.WSDL;

    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public VersionedFile getPathVersionPair() {
        return  pathVersionPair;
    }

    public void setPathVersionPair(VersionedFile pathVersionPair) {
        this. pathVersionPair = pathVersionPair;
    }

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

    public List<String> getRequest() {
        return request;
    }

    public void setRequest(List<String> request) {
        this.request = request;
    }
}
