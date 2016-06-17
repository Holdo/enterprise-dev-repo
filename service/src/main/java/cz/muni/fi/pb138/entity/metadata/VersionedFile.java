package cz.muni.fi.pb138.entity.metadata;

/**
 *
 * @author gasior
 */
public class VersionedFile {
    
    private String fullPath;
    private int version;
    private boolean isDirectory;

    public VersionedFile(String fullPath) {
        this.fullPath = fullPath;
    }

    public VersionedFile(String fullPath, int version) {
        this.fullPath = fullPath;
        this.version = version;
        this.isDirectory = false;
    }

    public VersionedFile(String fullPath, int version, boolean isDirectory) {
        this.fullPath = fullPath;
        this.version = version;
        this.isDirectory = isDirectory;
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

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionedFile)) return false;

        VersionedFile that = (VersionedFile) o;

        if (getVersion() != that.getVersion()) return false;
        if (isDirectory() != that.isDirectory()) return false;
        return getFullPath().equals(that.getFullPath());

    }

    @Override
    public int hashCode() {
        int result = getFullPath().hashCode();
        result = 31 * result + getVersion();
        result = 31 * result + (isDirectory() ? 1 : 0);
        return result;
    }
}
