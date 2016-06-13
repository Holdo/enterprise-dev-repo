package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;

import javax.xml.bind.JAXBException;
import java.util.HashMap;

/**
 *
 * @author gasior
 */
public interface FileBase {

    byte[] getFile();

    byte[] getMeta() throws JAXBException;

    FileType getType();

    HashMap<MetaFileType, byte[]> getMetaFiles();

    void setVersion(int version);
}
