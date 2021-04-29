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
        return (Course) myModelDao.createOneModel(teacherId, course, new Course().getModelType());
    }

    @Override
    public DeleteResult removeOneCourseById(String courseId) {
        return myModelDao.removeOneModelById(courseId, new Course().getModelType());
    }

    @Override
    public ModelJoinRecord joinOneCourse(String studentId, String courseId) {
        return myModelDao.joinOneModel(studentId, courseId);
    }

    @Override
    public DeleteResult exitOneCourse(String studentId, String courseId) {
        return myModelDao.exitOneModel(studentId, courseId);
    }

    @Override
    public List<MyModel> getCoursesByTeacher(String teacherId) {
        return myModelDao.getModelsByPublisher(teacherId);
    }

    @Override
    public List<MyModel> getCoursesByStudent(String studentId) {
        return myModelDao.getModelsByParticipator(studentId, new Course().getModelType());
    }

    @Override
    public List<MyModel> getAllCourses() {

        return myModelDao.getModelsByType(new Course().getModelType());
    }

    @Override
    public List<User> getStudentsByCourse(String courseId) {
        return myModelDao.getParticipatorsByModel(courseId);
    }

    @Override
    public List<MyModel> searchCourses(String inputStr) {
        return myModelDao.searchModels(inputStr, new Course().getModelType());
    }

    @Override
    public MyFavorModel collectOneCourse(String courseId, String userId) {
        return myModelDao.collectOneModel(courseId, userId);
    }

    @Override
    public List<MyFavorModel> getFavorCoursesByUserId(String userId) {
        return myModelDao.getFavorModelsByUserId(userId, new Course().getModelType());
    }

    @Override
    public DeleteResult removeOneFavorCourse(String courseId, String userId) {
        return myModelDao.removeOneFavorModel(courseId, userId);
    }
}
