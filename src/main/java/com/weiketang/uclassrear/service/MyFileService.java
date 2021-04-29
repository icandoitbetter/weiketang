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
    MyFile getOneFileFromClient(MultipartFile file, MyFile fileInfo,
                                String modelId, String uploaderId) throws IOException;
    ResponseEntity<Resource> sendOneFileToClient(HttpServletRequest request,
                                                 String modelId,
                                                 String fileName) throws IOException;
    DeleteResult removeOneFile(String modelId, String fileName);

    /*查找文件*/
    List<MyFile> getFilesByModelId(String modelId);
    List<MyFile> getFilesByUploaderId(String uploaderId);
    MyFile getOneFileByFileName(String modelId, String fileName);

}
