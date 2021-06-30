package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.MyFile;
import com.weiketang.uclassrear.service.MyFileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class FileManageController {
    static final Logger logger = LoggerFactory.getLogger(FileManageController.class);
    @Autowired
    MyFileService myFileService;

    @GetMapping("/upload")
    public String myProject(){
        return "test/3";
    }

    @PostMapping("/upload/{modelId}")
    public String uploadFile(HttpSession session,
                                   @PathVariable("modelId") String model_id,
                                   @RequestParam("fileTitle") String fileTitle,
                                   @RequestParam("fileDescription") String fileDescription,
                                   @RequestPart("srcFile") MultipartFile srcFile){

        /*请前端传入下述内容：fileTitle、fileDescription、modelId*/
        MyFile fileInfo = new MyFile();
        fileInfo.setFileTitle(fileTitle);           /*用户输入的文件标题*/
        fileInfo.setDescription(fileDescription);   /*用户输入的文件描述内容*/
        String modelId = "60892679339a364902f81284";                  /*文件所属的课程或项目的id*/

        /*当前操作的用户id*/
        String uploaderId = session.getAttribute("userId").toString();

        try {
            myFileService.getOneFileFromClient(srcFile, fileInfo, modelId, uploaderId);
        }
        catch (IOException e){
            logger.error("无法获取文件类型", e);
        }

        return "redirect:/home";
    }

    @GetMapping("/download/{modelId}/{fileName}")
    public Object downloadFile( @PathVariable("modelId") String model_id,
                                @PathVariable("fileName") String file_Name,
                                HttpServletRequest request){

        /*请前端传入下述内容：modelId、fileName*/
        String modelId = model_id;      /*文件所属的课程或项目的id*/
        String fileName = file_Name;    /*用户要下载的文件的文件名（加后缀）*/
        ResponseEntity<Resource> responseEntity = null;
        try {
            responseEntity = myFileService.sendOneFileToClient(request, modelId, fileName);
            if(responseEntity == null) return "redirect:/myLecture";
        }
        catch (IOException e){
            logger.error("无法获取文件类型", e);
            return "redirect:/home";
        }

        return responseEntity;
    }

    @GetMapping("/removeOneFile/{modelId}/{fileName}")
    public String removeOneFile(@PathVariable("modelId") String model_id,
                                @PathVariable("fileName") String file_Name){

        /*请前端传入下述内容：modelId、fileName*/
        String modelId = model_id;   /*文件所属的课程或项目的id*/
        String fileName = file_Name;   /*用户要删除的文件的文件名（加后缀）*/
        myFileService.removeOneFile(modelId, fileName);

        return "redirect:/home";
    }

    @GetMapping("/getFilesByModelId/{modelId}")
    public String getFilesByModelId(@PathVariable("modelId") String model_id, Model model){

        String modelId = model_id;   /*请前端传入文件所属的课程或项目的id*/
        List<MyFile> myFiles = myFileService.getFilesByModelId(modelId);
        model.addAttribute("myFiles", myFiles);

        return "test/4";
    }

    @GetMapping("/getFilesByUploaderId")
    public String getFilesByUploaderId(HttpSession session, Model model){

        String uploaderId = session.getAttribute("userId").toString();
        List<MyFile> myFiles = myFileService.getFilesByUploaderId(uploaderId);
        model.addAttribute("myFiles", myFiles);

        return "test/4";
    }

    @GetMapping("/getOneFileByFileName/{modelId}/{fileName}")
    public String getOneFileByFileName(@PathVariable("modelId") String model_id,
                                       @PathVariable("fileName") String file_Name,
                                        Model model){

        /*请前端传入下述内容：modelId、fileName*/
        String modelId = model_id;   /*文件所属的课程或项目的id*/
        String fileName = file_Name;   /*用户要获取的文件的文件名（加后缀）*/
        MyFile myFile = myFileService.getOneFileByFileName(modelId, fileName);
        model.addAttribute("myFile", myFile);

        return "test/4";
    }
}
