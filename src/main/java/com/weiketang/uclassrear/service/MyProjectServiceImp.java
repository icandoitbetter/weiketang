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
        if(publisherId == null || myProject == null) return null;
        return (MyProject)myModelDao.createOneModel(publisherId, myProject, new MyProject().getModelType());
    }

    @Override
    public DeleteResult removeOneProjectById(String projectId) {
        if(projectId == null) return null;
        return myModelDao.removeOneModelById(projectId, new MyProject().getModelType());
    }

    @Override
    public ModelJoinRecord joinOneProject(String participatorId, String projectId) {
        if(participatorId == null || projectId == null) return null;
        return myModelDao.joinOneModel(participatorId, projectId);
    }

    @Override
    public DeleteResult exitOneProject(String participatorId, String projectId) {
        if(participatorId == null || projectId == null) return null;
        return myModelDao.exitOneModel(participatorId, projectId);
    }

    @Override
    public List<MyModel> getProjectsByPublisher(String publisherId) {
        if(publisherId == null) return null;
        return myModelDao.getModelsByPublisher(publisherId, new MyProject().getModelType());
    }

    @Override
    public List<MyModel> getProjectsByParticipator(String participatorId) {
        if(participatorId == null) return null;
        return myModelDao.getModelsByParticipator(participatorId, new MyProject().getModelType());
    }

    @Override
    public List<MyModel> getAllProjects() {
        return myModelDao.getModelsByType(new MyProject().getModelType());
    }

    @Override
    public List<User> getParticipatorsByProject(String projectId) {
        if(projectId == null) return null;
        return myModelDao.getParticipatorsByModel(projectId);
    }

    @Override
    public List<MyModel> searchProjects(String inputStr) {
        if(inputStr == null) return null;
        return myModelDao.searchModels(inputStr, new MyProject().getModelType());
    }

    @Override
    public MyFavorModel collectOneProject(String projectId, String userId) {
        if(projectId == null || userId == null) return null;
        return myModelDao.collectOneModel(projectId, userId);
    }

    @Override
    public List<MyFavorModel> getFavorProjectsByUserId(String userId) {
        if(userId == null) return null;
        return myModelDao.getFavorModelsByUserId(userId, new MyProject().getModelType());
    }

    @Override
    public DeleteResult removeOneFavorProject(String projectId, String userId) {
        if(projectId == null || userId == null) return null;
        return myModelDao.removeOneFavorModel(projectId, userId);
    }
}
