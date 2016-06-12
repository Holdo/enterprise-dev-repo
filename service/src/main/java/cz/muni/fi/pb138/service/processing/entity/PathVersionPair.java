package cz.muni.fi.pb138.service.processing.entity;

/**
 *
 * @author gasior
 */
public class PathVersionPair {
    
    private String fullPath;
    private int version;

    public PathVersionPair(String fullPath, int version) {
        this.fullPath = fullPath;
        this.version = version;
    }

    public PathVersionPair(String fullPath) {
        this.fullPath = fullPath;
        this.version = 1;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
