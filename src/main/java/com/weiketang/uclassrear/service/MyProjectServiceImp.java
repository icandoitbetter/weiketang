package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.dao.MyModelDao;
import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class MyProjectServiceImp implements MyProjectService{
    @Autowired
    MyModelDao myModelDao;

    @Autowired
    MyFileService myFileService;


    @Override
    public MyProject createOneProject(String publisherId, MyProject myProject) {
        return (MyProject)myModelDao.createOneModel(publisherId, myProject, new MyProject().getModelType());
    }

    @Override
    public DeleteResult removeOneProjectById(String projectId) {
        return myModelDao.removeOneModelById(projectId, new MyProject().getModelType());
    }

    @Override
    public ModelJoinRecord joinOneProject(String participatorId, String projectId) {
        return myModelDao.joinOneModel(participatorId, projectId);
    }

    @Override
    public DeleteResult exitOneCourse(String participatorId, String projectId) {
        return myModelDao.exitOneModel(participatorId, projectId);
    }

    @Override
    public List<MyModel> getProjectsByPublisher(String publisherId) {
        return myModelDao.getModelsByPublisher(publisherId);
    }

    @Override
    public List<MyModel> getProjectsByParticipator(String participatorId) {
        return myModelDao.getModelsByParticipator(participatorId, new MyProject().getModelType());
    }

    @Override
    public List<MyModel> getAllProjects() {
        return myModelDao.getModelsByType(new MyProject().getModelType());
    }

    @Override
    public List<User> getParticipatorsByProject(String projectId) {
        return myModelDao.getParticipatorsByModel(projectId);
    }

    @Override
    public List<MyModel> searchProjects(String inputStr) {
        return myModelDao.searchModels(inputStr, new MyProject().getModelType());
    }

    @Override
    public MyFavorModel collectOneProject(String projectId, String userId) {
        return myModelDao.collectOneModel(projectId, userId);
    }

    @Override
    public List<MyFavorModel> getFavorProjectsByUserId(String userId) {
        return myModelDao.getFavorModelsByUserId(userId, new MyProject().getModelType());
    }

    @Override
    public DeleteResult removeOneFavorProject(String projectId, String userId) {
        return myModelDao.removeOneFavorModel(projectId, userId);
    }
}
