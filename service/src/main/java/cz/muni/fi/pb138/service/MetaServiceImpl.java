/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.api.MetaParameterType;
import cz.muni.fi.pb138.api.MetaService;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.QueryFactory;
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
    private QueryFactory queryFactory;

    public final String FILE_DATABASE_NAME = "artifacts";
    public final String META_DATABASE_NAME = "metadata";

    @Override
    public List<PathVersionPair> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) throws IOException {


        String query = queryFactory.getFilesFullPathsByMetaParameterQuery(fileType,parameterType,namespace,parameterName);

        databaseDao.openDatabase(FILE_DATABASE_NAME);
        String rawOutput = databaseDao.runXQuery(query);

        // TODO

        return PathVersionPair.getListFromXml(rawOutput);
    }

    @Override
    public List<PathVersionPair> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType, String namespace) throws IOException {

        String query = queryFactory.getAllMetaParametersByMetaParameterTypeQuery(parameterType,namespace);

        databaseDao.openDatabase(META_DATABASE_NAME);
        String rawOutput = databaseDao.runXQuery(query);

        // TODO

        return PathVersionPair.getListFromXml(rawOutput);

    }

    @Override
    public List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException {
        List<MetaFilePathVersionTriplet> output = new ArrayList<>();
        String query = queryFactory.getAllMetaFilesByMetaFileTypeQuery(metaFileType,namespace);

        // TODO


        return output;
    }

    @Override
    public MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath, String version) throws IOException {
        MetaFilePathVersionTriplet output = new MetaFilePathVersionTriplet();
        String query = queryFactory.getMetaFileByFileFullPathQuery( metaFileType,fullPath,version);

        // TODO

        return output;
    }

    @Override
    public List<PathVersionPair> getMetaParametersByFileFullPath(MetaParameterType parameterType, String fullPath, String version) throws IOException {
        List<PathVersionPair> output = new ArrayList<>();
        String query = queryFactory.getMetaParametersByFileFullPathQuery(parameterType,fullPath,version);



        // TODO




        return output;
    }

  
}
