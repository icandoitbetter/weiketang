package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Controller
public class MyProjectController {
    private static final Logger logger = LoggerFactory.getLogger(MyProjectController.class);
    @Autowired
    FileService fileService;

    @GetMapping("/myProject")
    public String myProject(){
        return "myProject/myProject";
    }

    @PostMapping("/upload")
    public String uploadProject(HttpSession session,
                                @RequestParam("fileTitle") String fileTitle,
                                @RequestParam("fileDescription") String fileDescription,
                                @RequestPart("projectFile") MultipartFile projectFile) throws IOException {
        //打印日志；
        log.info("上传的信息：文件名{}，文件简介{}，文件名{}", fileTitle, fileDescription, projectFile.getOriginalFilename());
        String storePath = "D:\\MyProject\\weiketangFileStore\\";
        boolean result = fileService.storeFile(fileDescription, fileTitle, projectFile, storePath, session);
        return "myProject/myProject";
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadProject(@PathVariable String fileName,
                                                    HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (IOException e) {
            logger.error("无法获取文件类型", e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
