package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.MyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MyFileDaoImp implements MyFileDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insertFileInfo(MyFile fileInfo) {
        MyFile result = mongoTemplate.insert(fileInfo);
        if(result != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean alreadyExistInDB(MyFile fileInfo) {
        Query query = new Query(Criteria.where("storePath").is(fileInfo.getStorePath()).and("fileName").is(fileInfo.getFileName()));
        MyFile result = mongoTemplate.findOne(query, MyFile.class);
        if (result != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateFileInfo(MyFile fileInfo) {
        if(this.alreadyExistInDB(fileInfo) == true){
            return false;
        }

        return this.insertFileInfo(fileInfo);
    }

    @Override
    public String findFilePathByFileName(String fileName) {
        Query query = new Query(Criteria.where("fileName").is(fileName));
        MyFile result = mongoTemplate.findOne(query, MyFile.class);
        if(result != null){
            return result.getStorePath();
        }
        return null;
    }
}
