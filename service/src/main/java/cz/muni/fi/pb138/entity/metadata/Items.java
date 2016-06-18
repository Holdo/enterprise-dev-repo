package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by gasior on 18.06.2016.
 */
@XmlRootElement(name = "items")
public class Items {
    List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItems(List<Item> items) {
        this.item = items;
    }

    public Items(List<Item> items) {
        this.item = items;
    }

    public Items() {
    }
}
