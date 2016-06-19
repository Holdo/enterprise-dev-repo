package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by gasior on 18.06.2016.
 */
@XmlRootElement(name = "items")
public class Items {
    Item[] item;

    public Item[] getItem() {
        return item;
    }

    @XmlElement(name = "item")
    public void setItems(Item[] items) {
        this.item = items;
    }

    public Items(Item[] items) {
        this.item = items;
    }

    public Items() {
    }
}