package cz.muni.fi.pb138.entity.metadata;

/**
 *
 * @author Ondřej Gasior
 */
public class MetaFilePathVersionTriplet {
    
    private String fullPath;
    private byte[] file;
    private int version;

    public MetaFilePathVersionTriplet(String fullPath, byte[] file, int version) {
        this.fullPath = fullPath;
        this.file = file;
        this.version = version;
    }

    public MetaFilePathVersionTriplet() {

    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    
}
