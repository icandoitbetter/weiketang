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
    MyProject createOneProject(String publisherId, MyProject myProject);    /*某个用户发布一个项目*/
    DeleteResult removeOneProjectById(String projectId);                    /*删除某个项目*/

    /*加入与退出一个项目*/
    ModelJoinRecord joinOneProject(String participatorId, String projectId);    /*某个用户参加某个项目（备用API）*/
    DeleteResult exitOneProject(String participatorId, String projectId);       /*某个用户退出某个项目（备用API）*/

    /*查询项目、查询项目的参与者*/
    List<MyModel> getProjectsByPublisher(String publisherId);           /*某个用户查询自己发布过的全部项目*/
    List<MyModel> getProjectsByParticipator(String participatorId);     /*某个用户查询自己参与过的全部项目（备用API）*/
    List<MyModel> getAllProjects();                                     /*获取平台的全部项目（用于首页展示）*/
    List<User> getParticipatorsByProject(String projectId);             /*查询某个项目的所有参与者（备用API）*/
    List<MyModel> searchProjects(String inputStr);                      /*根据用户输入的内容，查询匹配到的项目*/

    /*收藏、取消收藏项目*/
    MyFavorModel collectOneProject(String projectId, String userId);    /*某个用户收藏某个项目*/
    List<MyFavorModel> getFavorProjectsByUserId(String userId);         /*某个用户获取自己收藏的全部项目*/
    DeleteResult removeOneFavorProject(String projectId, String userId);/*某个用户取消收藏某个项目*/

}
