package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;
import java.util.List;

public interface MyCourseService {
    /*创建与删除一门课程*/
    Course createOneCourse(String teacherId, Course course);
    DeleteResult removeOneCourseById(String courseId);

    /*加入与退出一门课程*/
    ModelJoinRecord joinOneCourse(String studentId, String courseId);
    DeleteResult exitOneCourse(String studentId, String courseId);      /*待检测！*****************************/

    /*查询课程、查询参与课程的学生*/
    List<MyModel> getCoursesByTeacher(String teacherId);
    List<MyModel> getCoursesByStudent(String studentId);
    List<MyModel> getAllCourses();
    List<User> getStudentsByCourse(String courseId);
    List<MyModel> searchCourses(String inputStr);

    /*收藏、取消收藏课程*/
    MyFavorModel collectOneCourse(String courseId, String userId);
    List<MyFavorModel> getFavorCoursesByUserId(String userId);
    DeleteResult removeOneFavorCourse(String courseId, String userId);

}
