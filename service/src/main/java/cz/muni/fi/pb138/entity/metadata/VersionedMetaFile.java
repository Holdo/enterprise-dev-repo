package cz.muni.fi.pb138.entity.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author Ondřej Gasior
 */
public class VersionedMetaFile {
    
    private String fullPath;

    @JsonIgnore
    private byte[] file;

    private String fileAsString;

    private int version;

    public VersionedMetaFile(String fullPath, byte[] file, int version) {
        this.fullPath = fullPath;
        this.file = file;
        this.version = version;
    }

    public VersionedMetaFile() {

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
        this.fileAsString = new String(file, StandardCharsets.UTF_8);
    }

    public String getFileAsString() {
        return fileAsString;
    }

    public void setFileAsString(String fileAsString) {
        this.fileAsString = fileAsString;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    
}
