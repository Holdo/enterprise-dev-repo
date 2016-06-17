package cz.muni.fi.pb138.entity.metadata;

import java.io.File;

/**
 *
 * @author gasior
 */
public class VersionedFile {

    private String name;
    private String fullPath;
    private int version;
    private boolean isDirectory;

    public VersionedFile(String fullPath, int version, boolean isDirectory) {
        this.name = fullPath.substring(fullPath.lastIndexOf(File.separator) + 1);
        this.fullPath = fullPath;
        this.version = version;
        this.isDirectory = isDirectory;
    }

    public VersionedFile(String name, String fullPath, int version, boolean isDirectory) {
        this.name = name;
        this.fullPath = fullPath;
        this.version = version;
        this.isDirectory = isDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!getName().equals(that.getName())) return false;
        return getFullPath().equals(that.getFullPath());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getFullPath().hashCode();
        result = 31 * result + getVersion();
        result = 31 * result + (isDirectory() ? 1 : 0);
        return result;
    }
}
