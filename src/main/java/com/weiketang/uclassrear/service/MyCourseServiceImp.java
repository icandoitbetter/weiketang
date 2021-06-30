package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.dao.MyModelDao;
import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MyCourseServiceImp implements MyCourseService{
    @Autowired
    MyModelDao myModelDao;

    @Autowired
    MyFileService myFileService;


    @Override
    public Course createOneCourse(String teacherId, Course course) {
        if(teacherId == null || course == null) return null;
        return (Course) myModelDao.createOneModel(teacherId, course, new Course().getModelType());
    }

    @Override
    public DeleteResult removeOneCourseById(String courseId) {
        if(courseId == null) return null;
        return myModelDao.removeOneModelById(courseId, new Course().getModelType());
    }

    @Override
    public ModelJoinRecord joinOneCourse(String studentId, String courseId) {
        if(studentId == null || courseId == null) return null;
        return myModelDao.joinOneModel(studentId, courseId);
    }

    @Override
    public DeleteResult exitOneCourse(String studentId, String courseId) {
        if(studentId == null || courseId == null) return null;
        return myModelDao.exitOneModel(studentId, courseId);
    }

    @Override
    public List<MyModel> getCoursesByTeacher(String teacherId) {
        if(teacherId == null) return null;
        return myModelDao.getModelsByPublisher(teacherId, new Course().getModelType());
    }

    @Override
    public List<MyModel> getCoursesByStudent(String studentId) {
        if(studentId == null) return null;
        return myModelDao.getModelsByParticipator(studentId, new Course().getModelType());
    }

    @Override
    public List<MyModel> getAllCourses() {

        return myModelDao.getModelsByType(new Course().getModelType());
    }

    @Override
    public List<User> getStudentsByCourse(String courseId) {
        if(courseId == null) return null;
        return myModelDao.getParticipatorsByModel(courseId);
    }

    @Override
    public List<MyModel> searchCourses(String inputStr) {
        if(inputStr == null) return null;
        return myModelDao.searchModels(inputStr, new Course().getModelType());
    }

    @Override
    public MyFavorModel collectOneCourse(String courseId, String userId) {
        if(courseId == null || userId == null) return  null;
        return myModelDao.collectOneModel(courseId, userId);
    }

    @Override
    public List<MyFavorModel> getFavorCoursesByUserId(String userId) {
        if(userId == null) return  null;
        return myModelDao.getFavorModelsByUserId(userId, new Course().getModelType());
    }

    @Override
    public DeleteResult removeOneFavorCourse(String courseId, String userId) {
        if(courseId == null || userId == null) return null;
        return myModelDao.removeOneFavorModel(courseId, userId);
    }
}
