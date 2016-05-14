package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.api.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gasior on 14.05.2016.
 */
public class FileEntry {

    private FileType type;
    private String originalFullPathNoSuffix;
    private int lastestVersion;
    private List<PathVersionPair> versionedPaths;
    

    public FileEntry(String fullPath, FileType type){
        originalFullPathNoSuffix = fullPath.substring(0,fullPath.lastIndexOf('.')-1);
        lastestVersion = 0;
        this.type = type;
    }

    public String deleteVersion(int version) {
        PathVersionPair toRemove = null;
        for (PathVersionPair p : versionedPaths
             ) {
            if(p.getVersion()==version)
                toRemove = p;
        }
        versionedPaths.remove(toRemove);
        return getVersionedPath(version);
    }
    public String addNewVersion() {

        lastestVersion++;
        String versionedPath = getVersionedPath(lastestVersion);
        versionedPaths.add(new PathVersionPair(versionedPath , lastestVersion));
        return versionedPath;
    }

    private String getVersionedPath(int version) {
        return originalFullPathNoSuffix+"_"+version+type.name();
    }

    public String getPathToVersionedFile(int version) {
        for (PathVersionPair pair : versionedPaths) {
            if(pair.getVersion() == version) {
                return pair.getFullPath();
            }
        }
        return null;
    }
    public List<Integer> getVersions() {
        return versionedPaths.stream().map(PathVersionPair::getVersion).collect(Collectors.toList());
    }
    public int getLastestVersion() {
        return lastestVersion;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getOriginalFullPathNoSuffix() {
        return originalFullPathNoSuffix;
    }

    public void setOriginalFullPathNoSuffix(String originalFullPathNoSuffix) {
        this.originalFullPathNoSuffix = originalFullPathNoSuffix;
    }

    public List<PathVersionPair> getVersionedPaths() {
        return versionedPaths;
    }

    public void setVersionedPaths(List<PathVersionPair> versionedPaths) {
        this.versionedPaths = versionedPaths;
    }

    public void setLastestVersion(int lastestVersion) {
        this.lastestVersion = lastestVersion;
    }
}
