package cz.muni.fi.pb138.entity.metadata.wsdlfield;

/**
 * @author Ondřej Gasior
 */
public class Response {

    private String name;
    private String parent;

    public Response() {
    }

    public Response(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
