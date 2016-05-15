package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gasior on 14.05.2016.
 */
@Service
public class PathFinder {

    public int getLastVersion(String fullPath, String files) {

        String[] paths = files.split("\n");
        String noSuffix = fullPath.substring(0,fullPath.lastIndexOf(".")-1);
        int version = 0;
        for(String p : paths) {
            if(p.startsWith(noSuffix)) {
                String x = p.substring(noSuffix.length(),p.length());
                String[] y = x.split(".");
                if(version < Integer.valueOf(y[0])) {
                    version = Integer.valueOf(y[0]);
                }
            }
        }
        return version;
    }

    public String getVersionedPath(int version, String fullPath, FileType type) {
        String noSuffix = fullPath.substring(0, fullPath.lastIndexOf(".")-1);
        return noSuffix + "_" + version + type.name();
    }


    public List<Integer> getAllVersions(String fullPath, String list) {
        List<Integer> output = new ArrayList<>();


        String[] paths = list.split("\n");
        String noSuffix = fullPath.substring(0,fullPath.lastIndexOf(".")-1);
        int version = 0;
        for(String p : paths) {
            if(p.startsWith(noSuffix)) {
                String x = p.substring(noSuffix.length(),p.length());
                String[] y = x.split(".");
                output.add(Integer.valueOf(y[0]));
            }
        }

        return output;
    }

    public List<PathVersionPair> getAllFilesByFileType(FileType fileType, String list) {
        List<PathVersionPair> output = new ArrayList<>();

        //TODO

        return output;
    }
}
