package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface MyProjectService {
    /*创建与删除一个项目*/
    MyProject createOneProject(String publisherId, MyProject myProject);
    DeleteResult removeOneProjectById(String projectId);

    /*加入与退出一个项目*/
    ModelJoinRecord joinOneProject(String participatorId, String projectId);
    DeleteResult exitOneCourse(String participatorId, String projectId);

    /*查询项目、查询项目的参与者*/
    List<MyModel> getProjectsByPublisher(String publisherId);
    List<MyModel> getProjectsByParticipator(String participatorId);
    List<MyModel> getAllProjects();
    List<User> getParticipatorsByProject(String projectId);
    List<MyModel> searchProjects(String inputStr);

    /*收藏、取消收藏项目*/
    MyFavorModel collectOneProject(String projectId, String userId);
    List<MyFavorModel> getFavorProjectsByUserId(String userId);
    DeleteResult removeOneFavorProject(String projectId, String userId);

}
