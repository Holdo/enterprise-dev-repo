package cz.muni.fi.pb138.entity.metadata;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathVersionPair)) return false;

        PathVersionPair that = (PathVersionPair) o;

        if (getVersion() != that.getVersion()) return false;
        return getFullPath().equals(that.getFullPath());

    }

    @Override
    public int hashCode() {
        int result = getFullPath().hashCode();
        result = 31 * result + getVersion();
        return result;
    }
}
