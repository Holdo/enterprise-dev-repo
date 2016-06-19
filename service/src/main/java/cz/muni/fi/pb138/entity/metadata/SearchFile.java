package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by gasior on 19.06.2016.
 */
public class SearchFile {

    private String path;

    private String version;

    public String getPath ()
    {
        return path;
    }
    @XmlElement(name = "path")
    public void setPath (String path)
    {
        this.path = path;
    }

    public String getVersion ()
    {
        return version;
    }
    @XmlElement (name = "version")
    public void setVersion (String version)
    {
        this.version = version;
    }

    public SearchFile(String path, String version) {
        this.path = path;
        this.version = version;
    }

    public SearchFile() {
    }
}
