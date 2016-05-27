/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.*;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.PathFinder;
import cz.muni.fi.pb138.service.processing.entity.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasior
 */
@Service
public class MetaServiceImpl implements MetaService {

    @Autowired
    private DatabaseDao databaseDao;
    @Autowired
    private BinaryDao binaryDao;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private PathFinder pathFinder;
    @Autowired
    private FileService fileService;

    public final String FILE_DATABASE_NAME = "artifacts";
    public final String META_DATABASE_NAME = "metadata";

    @Override
    public List<PathVersionPair> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) throws IOException {


        // TODO


        return null;
    }

    @Override
    public List<PathVersionPair> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType, String namespace) throws IOException {



        // TODO

        return null;

    }

    @Override
    public List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException {
        List<MetaFilePathVersionTriplet> output = new ArrayList<>();
        databaseDao.openDatabase(META_DATABASE_NAME);
        if(metaFileType == MetaFileType.WEBXML) {
            for (PathVersionPair path : fileService.getAllFilesByFileType(FileType.WAR, namespace)) {
                MetaFilePathVersionTriplet triplet = new MetaFilePathVersionTriplet();
                byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(path.getVersion(),path.getFullPath(),FileType.WAR)+metaFileType.name());
                triplet.setVersion(path.getVersion());
                triplet.setFullPath(path.getFullPath());
                triplet.setFile(metaFile);
            }
        }
        databaseDao.closeDatabase();

        return output;
    }

    @Override
    public MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException {
        return null;
    }

    @Override
    public MetaFilePathVersionTriplet getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException {
        MetaFilePathVersionTriplet output = new MetaFilePathVersionTriplet();
        databaseDao.openDatabase(META_DATABASE_NAME);
        byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(version, fullPath, FileType.WAR)+"." + metaFileType.toString());
        databaseDao.closeDatabase();

        output.setVersion(version);
        output.setFullPath(fullPath);
        output.setFile(metaFile);
        return output;
    }

    @Override
    public List<PathVersionPair> getMetaParametersByFileFullPath(MetaParameterType parameterType, String fullPath) throws IOException {
        List<PathVersionPair> output = new ArrayList<>();



        // TODO




        return output;
    }

    @Override
    public List<PathVersionPair> getMetaParametersByFileFullPathAndVersion(MetaParameterType parameterType, String fullPath, String version) throws IOException {
        List<PathVersionPair> output = new ArrayList<>();



        // TODO




        return output;
    }

  
}
