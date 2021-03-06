package cz.muni.fi.pb138.entity.war;

import cz.muni.fi.pb138.entity.FileBase;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * For WAR archives it is extracted web.xml, list of listeners and filters
 *
 * @author Ondřej Gasior
 */
public class WarFile implements FileBase {

    private byte[] file;
    private HashMap<MetaFileType, byte[]> typeMetaFileMap;
    private VersionedFile pathVersionPair;
    private List<String> listenerList;
    private List<String> filterList;
    private FileType type;
    public WarFile() {
        typeMetaFileMap = new HashMap<>();
        type = FileType.WAR;
    }



    public VersionedFile getPathVersionPair() {
        return pathVersionPair;
    }

    public void setPathVersionPair(VersionedFile pathVersionPair) {
        this.pathVersionPair = pathVersionPair;
    }

    public HashMap<MetaFileType, byte[]> getTypeMetaFileMap() {
        return typeMetaFileMap;
    }

    public void setTypeMetaFileMap(HashMap<MetaFileType, byte[]> typeMetaFileMap) {
        this.typeMetaFileMap = typeMetaFileMap;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public List<String> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<String> listenerList) {
        this.listenerList = listenerList;
    }

    public List<String> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<String> filterList) {
        this.filterList = filterList;
    }

    @Override
    public byte[] getFile() {
        return file;
    }

    @Override
    public byte[] getMeta() throws JAXBException {

            JAXBContext jc;
            Marshaller marshaller;
            jc = JAXBContext.newInstance(WarMeta.class);
            marshaller = jc.createMarshaller();

            StringWriter sw = new StringWriter();
            marshaller.marshal(new WarMeta(pathVersionPair, listenerList, filterList), sw);
            String xmlString = sw.toString();
            return xmlString.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public FileType getType() {
        return type;
    }

    @Override
    public HashMap<MetaFileType, byte[]> getMetaFiles() {
        return typeMetaFileMap;
    }

    @Override
    public void setVersion(int version) {
        pathVersionPair.setVersion(version);
    }


}
