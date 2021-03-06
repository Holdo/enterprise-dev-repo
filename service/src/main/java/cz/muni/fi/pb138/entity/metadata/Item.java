package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Ondřej Gasior
 */
public class Item
{
    private String name;

    private String parent;

    private String fullPath;

    private String type;

    private String version;

    public String getParent() {
        return parent;
    }
    @XmlElement (name = "parent")
    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName ()
    {
        return name;
    }

    @XmlElement (name = "name")
    public void setName (String name)
    {
        this.name = name;
    }


    public String getFullPath ()
    {
        return fullPath;
    }

    @XmlElement (name = "fullPath")
    public void setFullPath (String fullPath)
    {
        this.fullPath = fullPath;
    }

    public String getType ()
    {
        return type;
    }
    @XmlElement (name = "type")
    public void setType (String type)
    {
        this.type = type;
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

    public Item(String name, String fullPath, String version, String type) {
        this.name = name;
        this.fullPath = fullPath;
        this.version = version;
        this.type = type;
    }

    public Item() {
    }
}