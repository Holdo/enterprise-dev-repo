package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.api.MetaParameterType;
import org.springframework.stereotype.Service;

/**
 * Created by gasior on 12.05.2016.
 */
@Service
public class QueryFactory {


    public String getFilesFullPathsByMetaParameterQuery(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) {
        return "";
    }

    public String getAllMetaParametersByMetaParameterTypeQuery(MetaParameterType parameterType, String namespace) {
        return "";
    }

    public String getAllMetaFilesByMetaFileTypeQuery(MetaFileType metaFileType, String namespace) {
        return "";
    }

    public String getMetaFileByFileFullPathQuery(MetaFileType metaFileType, String fullPath, String version) {
        return "";
    }

    public String getMetaParametersByFileFullPathQuery(MetaParameterType parameterType, String fullPath, String version) {
        return "";
    }
}
