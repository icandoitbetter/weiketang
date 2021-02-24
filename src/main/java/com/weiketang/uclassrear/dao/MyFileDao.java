package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.MyFile;

public interface MyFileDao {

    boolean insertFileInfo(MyFile fileInfo);

    boolean alreadyExistInDB(MyFile fileInfo);

    boolean updateFileInfo(MyFile fileInfo);

    String findFilePathByFileName(String fileName);

}
