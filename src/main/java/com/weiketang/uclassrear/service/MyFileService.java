package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.MyFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface MyFileService {
    /*上传、下载、删除一个文件*/
    MyFile getOneFileFromClient(MultipartFile file, MyFile fileInfo,    /*某个用户上传某门课或某个项目的文件*/
                                String modelId, String uploaderId) throws IOException;
    ResponseEntity<Resource> sendOneFileToClient(HttpServletRequest request,    /*下载某门课或某个项目的某个文件*/
                                                 String modelId,
                                                 String fileName) throws IOException;
    DeleteResult removeOneFile(String modelId, String fileName);        /*删除某门课或某个项目的某个文件*/

    /*查找文件*/
    List<MyFile> getFilesByModelId(String modelId);         /*查询某门课或某个项目的全部文件信息*/
    List<MyFile> getFilesByUploaderId(String uploaderId);   /*某位用户查询自己上传过的文件信息*/
    MyFile getOneFileByFileName(String modelId, String fileName);   /*获取某门课的某个文件的信息*/

}
