package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.MyFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface MyFileDao {
    MyFile addFile(MultipartFile file,
                   MyFile fileInfo, String modelId,
                   String uploaderId) throws IOException;
    DeleteResult removeOneFile(String modelId, String fileName);

    List<MyFile> getFilesByModelId(String modelId);
    List<MyFile> getFilesByUploaderId(String uploaderId);
    MyFile getOneFileByFileName(String modelId, String fileName);
}
