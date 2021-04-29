package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.MyFile;
import com.weiketang.uclassrear.entity.MyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class MyFileDaoImp implements MyFileDao{
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${myconfig.warehousePath}")
    private String warehousePath;

    @Value("${myconfig.recycleBinPath}")
    private String recycleBinPath;

    private MyModel getModelByModelId(String modelId){
        return mongoTemplate.findById(modelId, MyModel.class);
    }

    private DeleteResult removeOneFileInfo(String modelId, String fileName){
        return mongoTemplate.remove(
                new Query(
                        Criteria.where("modelId").is(modelId)
                                .and("fileName").is(fileName)
                ),
                MyFile.class
        );
    }

    @Override
    public MyFile addFile(MultipartFile file, MyFile fileInfo,
                          String modelId, String uploaderId) throws IOException {
        /*1.判断该所属model是否存在，若存在，才存入服务器本地*/
        MyModel myModel = this.getModelByModelId(modelId);
        if(myModel == null){
            return null;
        }

        /*2.填写相关的重要信息*/
        fileInfo.setModelType(myModel.getModelType());
        fileInfo.setModelId(modelId);
        fileInfo.setUploaderId(uploaderId);
        fileInfo.setFileName(file.getOriginalFilename());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileInfo.setUploaderDate(df.format(new Date()));

        /*3. 存储文件到本地指定路径*/
        file.transferTo(
                new File(warehousePath + "\\" +
                        modelId + "\\" + fileInfo.getFileName())
        );

        /*4.如果该文件原本就存在，则覆盖其原先的记录*/
        this.removeOneFileInfo(fileInfo.getModelId(), fileInfo.getFileName());
        return mongoTemplate.insert(fileInfo);
    }

    @Override
    public DeleteResult removeOneFile(String modelId, String fileName) {
        /*1.判断该文件是否存在，若存在，才进行remove操作*/
        if(this.getOneFileByFileName(modelId, fileName) == null){
            return null;
        }

        /*2.将该文件移入回收站*/
        File srcFile = new File(warehousePath + "\\" + modelId + "\\" + fileName);
        new File(recycleBinPath + "\\" + modelId).mkdirs();
        File destFile = new File(recycleBinPath + "\\" + modelId + "\\" + fileName);
        if(!srcFile.renameTo(destFile)) return null;

        /*3.删除该文件存储记录*/
        return this.removeOneFileInfo(modelId, fileName);
    }

    @Override
    public List<MyFile> getFilesByModelId(String modelId) {
        return mongoTemplate.find(
                new Query(Criteria.where("modelId").is(modelId)),
                MyFile.class
        );
    }

    @Override
    public List<MyFile> getFilesByUploaderId(String uploaderId) {
        return mongoTemplate.find(
                new Query(Criteria.where("uploaderId").is(uploaderId)),
                MyFile.class
        );
    }

    @Override
    public MyFile getOneFileByFileName(String modelId, String fileName) {
        return mongoTemplate.findOne(
                new Query(Criteria.where("modelId").is(modelId)
                        .and("fileName").is(fileName)
                ),
                MyFile.class
        );
    }
}
