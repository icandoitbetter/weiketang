package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.myFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

public interface MyFileDao {

    boolean insertFileInfo(myFile fileInfo);

    boolean alreadyExistInDB(myFile fileInfo);

    boolean updateFileInfo(myFile fileInfo);

    String findFilePathByFileName(String fileName);

}
