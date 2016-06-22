package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ondřej Gasior
 */
@XmlRootElement(name = "files")
public class SearchResult {

    private SearchFile[] file;

    public SearchFile[] getFile ()
    {
        return file;
    }

    @XmlElement(name = "file")
    public void setFile (SearchFile[] file)
    {
        this.file = file;
    }

    public SearchResult() {
    }

    public SearchResult(SearchFile[] file) {
        this.file = file;
    }
}
