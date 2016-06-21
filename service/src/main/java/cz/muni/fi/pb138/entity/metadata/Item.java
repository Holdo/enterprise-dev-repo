package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by gasior on 18.06.2016
 */
public class Item
{
    private String name;

    private String fullPath;

    private String type;

    private String version;



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