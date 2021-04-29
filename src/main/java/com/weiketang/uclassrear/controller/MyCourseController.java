package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.*;
import com.weiketang.uclassrear.service.MyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyCourseController {
    @Autowired
    MyCourseService myCourseService;

    @GetMapping("/createOneCourse")
    public String createOneCourse(HttpSession session){

        String teacherId = session.getAttribute("userId").toString();
        Course course = new Course();   /*请前端传入用户输入的课程信息；*/
        myCourseService.createOneCourse(teacherId, course);

        return "redirect:/myLecture";
    }

    @GetMapping("/removeOneCourseById")
    public String removeOneCourseById(){

        String courseId = "null";   /*请前端传入课程的id*/
        myCourseService.removeOneCourseById(courseId);

        return "redirect:/myLecture";
    }

    @GetMapping("/joinOneCourse")
    public String joinOneCourse(HttpSession session){

        String studentId = session.getAttribute("userId").toString();
        String courseId = "null";   /*请前端传入课程的id*/
        myCourseService.joinOneCourse(studentId, courseId);

        return "redirect:/myLecture";
    }

    @GetMapping("/exitOneCourse")
    public String exitOneCourse(HttpSession session){

        String studentId = session.getAttribute("userId").toString();
        String courseId = "null";   /*请前端传入课程的id*/
        myCourseService.exitOneCourse(studentId, courseId);

        return "redirect:/myLecture";
    }

    @GetMapping("/getCoursesByTeacher")
    public String getCoursesByTeacher(HttpSession session, Model model){

        String teacherId = session.getAttribute("userId").toString();
        List<MyModel> courses = myCourseService.getCoursesByTeacher(teacherId);
        model.addAttribute("courses1", courses);

        return "redirect:/myLecture";
    }

    @GetMapping("/getCoursesByStudent")
    public String getCoursesByStudent(HttpSession session, Model model){

        String studentId = session.getAttribute("userId").toString();
        List<MyModel> courses = myCourseService.getCoursesByStudent(studentId);
        model.addAttribute("courses2", courses);

        return "redirect:/myLecture";
    }

    @GetMapping("/getAllCourses")
    public String getAllCourses(Model model){

        List<MyModel> courses = myCourseService.getAllCourses();
        model.addAttribute("courses3", courses);

        return "redirect:/myLecture";
    }

    @GetMapping("/getStudentsByCourse")
    public String getStudentsByCourse(Model model){

        String courseId = "null";   /*请前端传入课程的id*/
        List<User> students = myCourseService.getStudentsByCourse(courseId);
        model.addAttribute("students", students);

        return "redirect:/myLecture";
    }

    @GetMapping("/searchCourses")
    public String searchCourses(Model model){

        String inputStr = "null";   /*请前端传入用户输入的搜索内容*/
        List<MyModel> courses = myCourseService.searchCourses(inputStr);
        model.addAttribute("courses4", courses);

        return "redirect:/myLecture";
    }



    @GetMapping("/collectOneCourse")
    public String collectOneCourse(HttpSession session){

        String courseId = "null";   /*请前端传入课程的id*/
        String userId = session.getAttribute("userId").toString();
        myCourseService.collectOneCourse(courseId, userId);

        return "redirect:/myLecture";
    }

    @GetMapping("/getFavorCoursesByUserId")
    public String getFavorCoursesByUserId(HttpSession session, Model model){

        String userId = session.getAttribute("userId").toString();
        List<MyFavorModel> favorModels = myCourseService.getFavorCoursesByUserId(userId);
        model.addAttribute("favorModels", favorModels);

        return "redirect:/myLecture";
    }

    @GetMapping("/removeOneFavorCourse")
    public String removeOneFavorCourse(HttpSession session){

        String courseId = "null";   /*请前端传入课程的id*/
        String userId = session.getAttribute("userId").toString();
        myCourseService.removeOneFavorCourse(courseId, userId);

        return "redirect:/myLecture";
    }
}
