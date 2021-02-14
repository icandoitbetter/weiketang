package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.myFile;
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
    public boolean insertFileInfo(myFile fileInfo) {
        myFile result = mongoTemplate.insert(fileInfo);
        if(result != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean alreadyExistInDB(myFile fileInfo) {
        Query query = new Query(Criteria.where("storePath").is(fileInfo.getStorePath()));
        myFile result = mongoTemplate.findOne(query, myFile.class);
        if (result != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateFileInfo(myFile fileInfo) {
        if(this.alreadyExistInDB(fileInfo) == true){
            return this.updateFileInfo(fileInfo);
        }
        return this.insertFileInfo(fileInfo);
    }

    @Override
    public String findFilePathByFileName(String fileName) {
        Query query = new Query(Criteria.where("fileName").is(fileName));
        myFile result = mongoTemplate.findOne(query, myFile.class);
        if(result != null){
            return result.getStorePath();
        }
        return null;
    }
}
