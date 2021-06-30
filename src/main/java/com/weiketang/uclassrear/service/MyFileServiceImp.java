package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.dao.MyFileDao;
import com.weiketang.uclassrear.entity.MyFile;
import com.weiketang.uclassrear.exception.FileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
public class MyFileServiceImp implements MyFileService{
    @Value("${myconfig.warehousePath}")
    private String warehousePath;

    @Autowired
    private MyFileDao myFileDao;

    @Override
    public MyFile getOneFileFromClient(MultipartFile file, MyFile fileInfo,
                                       String modelId, String uploaderId) throws IOException {
        if(file == null || fileInfo == null ||
                modelId == null || uploaderId == null) return null;
        return myFileDao.addFile(file, fileInfo, modelId, uploaderId);
    }

    @Override
    public ResponseEntity<Resource> sendOneFileToClient(HttpServletRequest request,
                                                        String modelId,
                                                        String fileName) throws IOException {
        if(request == null || modelId == null || fileName == null) return null;

        /*1.从服务器本地获取客户端指定下载的文件*/
        Resource resource = this.getFileAsResource(modelId, fileName);
        if(resource == null) return null;

        /*2.设置传输的文件类型？*/
        String contentType = request.getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        /*3.生成并发送响应的http报文实体？*/
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                new String(resource.getFilename().getBytes("utf-8"), "ISO8859-1") +
                                "\""
                )
                .body(resource);
    }

    @Override
    public DeleteResult removeOneFile(String modelId, String fileName) {
        if(modelId == null || fileName == null) return null;
        return myFileDao.removeOneFile(modelId, fileName);
    }

    @Override
    public List<MyFile> getFilesByModelId(String modelId) {
        if(modelId == null) return null;
        return myFileDao.getFilesByModelId(modelId);
    }

    @Override
    public List<MyFile> getFilesByUploaderId(String uploaderId) {
        if(uploaderId == null) return null;
        return myFileDao.getFilesByUploaderId(uploaderId);
    }

    @Override
    public MyFile getOneFileByFileName(String modelId, String fileName) {
        if(modelId == null || fileName == null) return null;
        return myFileDao.getOneFileByFileName(modelId, fileName);
    }

    private Resource getFileAsResource(String modelId, String fileName) {
        if(modelId == null || fileName == null) return null;

        if(myFileDao.getOneFileByFileName(modelId, fileName) == null) {
            return null;
        }

        Path path = (FileSystems.getDefault()
                .getPath(warehousePath + "\\" + modelId + "\\"))
                .resolve(fileName).normalize();

        try {
            UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new FileException("file " + fileName + " not found");
        }
        catch (MalformedURLException e) {
            throw new FileException ("file " + fileName + " not found", e);
        }
    }
}
