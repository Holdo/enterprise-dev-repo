package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ondřej Gasior
 */
@XmlRootElement(name = "items")
public class Items extends Metas {
    private Item[] item;

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
