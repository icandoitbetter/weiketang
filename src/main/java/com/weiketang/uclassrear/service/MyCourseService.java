package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;
import java.util.List;

public interface MyCourseService {
    /*创建与删除一门课程*/
    Course createOneCourse(String teacherId, Course course);    /*某位老师创建一门课程*/
    DeleteResult removeOneCourseById(String courseId);          /*根据课程id删除该课程*/

    /*加入与退出一门课程*/
    ModelJoinRecord joinOneCourse(String studentId, String courseId);   /*某位学生加入某门课程*/
    DeleteResult exitOneCourse(String studentId, String courseId);      /*某位学生退出某门课程*/
    /*待检测！*****************************/

    /*查询课程、查询参与课程的学生*/
    List<MyModel> getCoursesByTeacher(String teacherId);    /*查询某位老师创建的全部课程*/
    List<MyModel> getCoursesByStudent(String studentId);    /*查询某位学生创建的全部课程*/
    List<MyModel> getAllCourses();                          /*获取本平台上的全部课程（首页展示课程）*/
    List<User> getStudentsByCourse(String courseId);        /*查询某门课程中，所有参加该课程的学生*/
    List<MyModel> searchCourses(String inputStr);           /*根据用户输入的搜索信息，查询匹配的课程（模糊查询）*/

    /*收藏、取消收藏课程*/
    MyFavorModel collectOneCourse(String courseId, String userId);  /*某个用户收藏某门课程*/
    List<MyFavorModel> getFavorCoursesByUserId(String userId);      /*某个用户查询自己收藏的全部课程*/
    DeleteResult removeOneFavorCourse(String courseId, String userId);  /*某个用户取消收藏某门课程*/

}
