package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.service.processing.entity.FileEntry;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import cz.muni.fi.pb138.service.processing.entity.xsd.XsdMeta;
import net.xqj.basex.bin.L;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by gasior on 14.05.2016.
 */
@Service
@XmlRootElement( name = "pathfinder")
public class PathFinder {

    private HashMap<String,FileEntry> fileEntries;

    public PathFinder() {
        fileEntries = new HashMap<>();
    }

    public List<Integer> getAllVersions(String fullPath) {
        if (fileEntries.containsKey(fullPath)) {
            return fileEntries.get(fullPath).getVersions();
        } else return new ArrayList<>();
    }
    public String deleteFile(String fullPath, int version) {
        if (fileEntries.containsKey(fullPath)) {
            return fileEntries.get(fullPath).deleteVersion(version);
        } else return null;
    }
    public String addFile(String fullPath, FileType type) {
        if (fileEntries.containsKey(fullPath)) {
            return fileEntries.get(fullPath).addNewVersion();
        } else {
            fileEntries.put(fullPath, new FileEntry(fullPath,type));
            return fileEntries.get(fullPath).addNewVersion();
        }
    }
    public int getLastestVersion(String fullPath) {
        if (fileEntries.containsKey(fullPath)) {
            return fileEntries.get(fullPath).getLastestVersion();
        } else return 0;
    }
    public String getVersionedPath(String fullPath, int version) {
        if (fileEntries.containsKey(fullPath)) {
            return fileEntries.get(fullPath).getPathToVersionedFile(version);
        } else return null;
    }

    public List<PathVersionPair> getAllFilesByFileType(FileType fileType, String namespace) {
        List<PathVersionPair> output = new ArrayList<>();

        for (String s : fileEntries.keySet()) {
            if(fileEntries.get(s).getType() == fileType) {
                for (int i : fileEntries.get(s).getVersions()) {
                    if(fileEntries.get(s).getOriginalFullPathNoSuffix().startsWith(namespace)) {
                        output.add(new PathVersionPair(fileEntries.get(s).getOriginalFullPathNoSuffix()+fileType.name(), i));
                    }
                }

            }

        }
        return output;
    }

    public byte[] getMeta() {
        JAXBContext jc;
        Marshaller marshaller;
        File xml = null;
        // TODO
        try {
            jc = JAXBContext.newInstance(XsdMeta.class);
            marshaller = jc.createMarshaller();
            marshaller.marshal(this, xml);
        } catch (JAXBException ex) {
            Logger.getLogger(XsdFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            return Files.readAllBytes(xml.toPath());
        } catch (IOException ex) {
            return null;
        }

    }
}
