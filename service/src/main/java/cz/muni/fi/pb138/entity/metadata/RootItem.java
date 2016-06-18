package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by gasior on 18.06.2016.
 */
public class RootItem {
    private Items items;

    public Items getItems ()
    {
        return items;
    }

    public void setItems (Items items)
    {
        this.items = items;
    }

    public RootItem(Items items) {
        this.items = items;
    }

    public RootItem() {
    }
}
