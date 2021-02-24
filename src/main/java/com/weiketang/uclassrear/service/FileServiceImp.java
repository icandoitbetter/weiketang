package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.MyFileDao;
import com.weiketang.uclassrear.entity.MyFile;
import com.weiketang.uclassrear.exception.FileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileServiceImp implements FileService {
    @Autowired
    private MyFileDao myFileDao;

    @Override
    public boolean storeFile(String fileDescription, String fileTitle, MultipartFile file, String storePath, HttpSession session) throws IOException {
        if(file.isEmpty()){
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        file.transferTo(new File(storePath + originalFilename));


        MyFile fileInfo = new MyFile();

        fileInfo.setUploaderId(session.getAttribute("loginUserId").toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileInfo.setUploaderDate(df.format(new Date()));
        fileInfo.setFileTitle(fileTitle);
        fileInfo.setSize(file.getSize());
        fileInfo.setFileName(originalFilename);
        fileInfo.setStorePath(storePath);
        fileInfo.setFileDescription(fileDescription);
        myFileDao.updateFileInfo(fileInfo);

        return true;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        String filePath = myFileDao.findFilePathByFileName(fileName);
        Path path = (FileSystems.getDefault().getPath(filePath)).resolve(fileName).normalize();
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
