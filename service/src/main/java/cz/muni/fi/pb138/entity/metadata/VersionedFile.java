package cz.muni.fi.pb138.entity.metadata;

import java.io.File;

/**
 *
 * @author gasior
 */
public class VersionedFile {

    private String name;
    private String path;
    private int version;
    private boolean isDirectory;

    public VersionedFile(String path, int version, boolean isDirectory) {
        this.name = path.substring(path.lastIndexOf(File.separator) + 1);
        this.path = path;
        this.version = version;
        this.isDirectory = isDirectory;
    }

    public VersionedFile(String name, String path, int version, boolean isDirectory) {
        this.name = name;
        this.path = path;
        this.version = version;
        this.isDirectory = isDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (!getName().equals(that.getName())) return false;
        return getPath().equals(that.getPath());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPath().hashCode();
        result = 31 * result + getVersion();
        result = 31 * result + (isDirectory() ? 1 : 0);
        return result;
    }
}
