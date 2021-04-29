package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.MyFile;
import com.weiketang.uclassrear.service.MyFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FileManageController {
    static final Logger logger = LoggerFactory.getLogger(FileManageController.class);
    @Autowired
    MyFileService myFileService;

    @GetMapping("/upload")
    public String uploadFile(HttpSession session,
                                   @RequestParam("fileTitle") String fileTitle,
                                   @RequestParam("fileDescription") String fileDescription,
                                   @RequestPart("srcFile") MultipartFile srcFile){

        /*请前端传入下述内容：fileTitle、fileDescription、modelId*/
        MyFile fileInfo = new MyFile();
        fileInfo.setFileTitle(fileTitle);           /*用户输入的文件标题*/
        fileInfo.setDescription(fileDescription);   /*用户输入的文件描述内容*/
        String modelId = "null";                   /*文件所属的课程或项目的id*/

        /*当前操作的用户id*/
        String uploaderId = session.getAttribute("userId").toString();

        try {
            myFileService.getOneFileFromClient(srcFile, fileInfo, modelId, uploaderId);
        }
        catch (IOException e){
            logger.error("无法获取文件类型", e);
        }

        return "redirect:/myLecture";
    }

    @GetMapping("/download")
    public Object downloadFile(HttpServletRequest request){

        /*请前端传入下述内容：modelId、fileName*/
        String modelId = "null";   /*文件所属的课程或项目的id*/
        String fileName = "null";   /*用户要下载的文件的文件名（加后缀）*/

        ResponseEntity<Resource> responseEntity = null;
        try {
            responseEntity = myFileService.sendOneFileToClient(request, modelId, fileName);
            if(responseEntity == null) return "redirect:/myLecture";
        }
        catch (IOException e){
            logger.error("无法获取文件类型", e);
            return "redirect:/myLecture";
        }

        return responseEntity;
    }

    @GetMapping("/removeOneFile")
    public String removeOneFile(){

        /*请前端传入下述内容：modelId、fileName*/
        String modelId = "null";   /*文件所属的课程或项目的id*/
        String fileName = "null";   /*用户要删除的文件的文件名（加后缀）*/
        myFileService.removeOneFile(modelId, fileName);

        return "redirect:/myLecture";
    }

    @GetMapping("/getFilesByModelId")
    public String getFilesByModelId(Model model){

        String modelId = "null";   /*请前端传入文件所属的课程或项目的id*/
        List<MyFile> myFiles = myFileService.getFilesByModelId(modelId);
        model.addAttribute("myFiles1", myFiles);

        return "redirect:/myLecture";
    }

    @GetMapping("/getFilesByUploaderId")
    public String getFilesByUploaderId(HttpSession session, Model model){

        String uploaderId = session.getAttribute("userId").toString();
        List<MyFile> myFiles = myFileService.getFilesByUploaderId(uploaderId);
        model.addAttribute("myFiles2", myFiles);

        return "redirect:/myLecture";
    }

    @GetMapping("/getOneFileByFileName")
    public String getOneFileByFileName(Model model){

        /*请前端传入下述内容：modelId、fileName*/
        String modelId = "null";   /*文件所属的课程或项目的id*/
        String fileName = "null";   /*用户要获取的文件的文件名（加后缀）*/
        MyFile myFile = myFileService.getOneFileByFileName(modelId, fileName);
        model.addAttribute("myFile", myFile);

        return "redirect:/myLecture";
    }
}
