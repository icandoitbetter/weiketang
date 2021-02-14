package com.weiketang.uclassrear.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface FileService {
    boolean storeFile(String fileDescription, String title, MultipartFile file, String storePath, HttpSession session) throws IOException;
    Resource loadFileAsResource(String fileName);
}
