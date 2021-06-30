package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.*;
import com.weiketang.uclassrear.service.MyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
public class MyCourseController {
    @Autowired
    MyCourseService myCourseService;

    @GetMapping("/createOneCourse")
    public String createOneCourse(HttpSession session){

        String teacherId = session.getAttribute("userId").toString();
        Course course = new Course();   /*请前端传入用户输入的课程信息*/

        /* 测试数据 */
        Random r = new Random();
        int ran = r.nextInt(5);
        course = Creator.courseCreator(ran);
        /* 测试数据 */

        myCourseService.createOneCourse(teacherId, course);

        return "redirect:/home";
    }

    @GetMapping("/removeOneCourseById/{courseId}")
    public String removeOneCourseById(@PathVariable("courseId") String course_Id){

        String courseId = course_Id;   /*请前端传入课程的id*/
        myCourseService.removeOneCourseById(courseId);

        return "redirect:/home";
    }

    @GetMapping("/joinOneCourse/{courseId}")
    public String joinOneCourse(@PathVariable("courseId") String course_Id, HttpSession session){

        String studentId = session.getAttribute("userId").toString();
        String courseId = course_Id;   /*请前端传入课程的id*/
        myCourseService.joinOneCourse(studentId, courseId);

        return "redirect:/home";
    }

    @GetMapping("/exitOneCourse/{courseId}")
    public String exitOneCourse(@PathVariable("courseId") String course_Id, HttpSession session){

        String studentId = session.getAttribute("userId").toString();
        String courseId = course_Id;   /*请前端传入课程的id*/
        myCourseService.exitOneCourse(studentId, courseId);

        return "redirect:/home";
    }

    @GetMapping("/getCoursesByUserId")
    public String getCoursesByTeacher(HttpSession session, Model model){

        String userId = session.getAttribute("userId").toString();
        String userType = session.getAttribute("userType").toString();

        List<MyModel> courses = null;
        if(userType.equals(new Student().getUserType())){
            courses = myCourseService.getCoursesByStudent(userId);
        }else{
            courses = myCourseService.getCoursesByTeacher(userId);
        }
        model.addAttribute("courses", courses);

        return "test/1";
    }

    @GetMapping("/getAllCourses")
    public String getAllCourses(Model model){

        List<MyModel> courses = myCourseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "test/1";
    }

    @GetMapping("/getStudentsByCourse/{courseId}")
    public String getStudentsByCourse(@PathVariable("courseId") String course_Id, Model model){

        String courseId = course_Id;   /*请前端传入课程的id*/
        List<User> students = myCourseService.getStudentsByCourse(courseId);
        model.addAttribute("students", students);

        return "test/2";
    }

    @GetMapping("/searchCourses/{inputStr}")
    public String searchCourses(@PathVariable("inputStr") String inputStr, Model model){

        String input = inputStr;   /*请前端传入用户输入的搜索关键字*/
        List<MyModel> courses = myCourseService.searchCourses(input);
        model.addAttribute("courses", courses);

        return "test/1";
    }

    @GetMapping("/collectOneCourse/{courseId}")
    public String collectOneCourse(@PathVariable("courseId") String course_Id, HttpSession session){

        String courseId = course_Id;   /*请前端传入课程的id*/
        String userId = session.getAttribute("userId").toString();
        myCourseService.collectOneCourse(courseId, userId);

        return "redirect:/home";
    }

    @GetMapping("/getFavorCoursesByUserId")
    public String getFavorCoursesByUserId(HttpSession session, Model model){

        String userId = session.getAttribute("userId").toString();
        List<MyFavorModel> favorModels = myCourseService.getFavorCoursesByUserId(userId);
        model.addAttribute("favorModels", favorModels);

        return "redirect:/home";
    }

    @GetMapping("/removeOneFavorCourse/{courseId}")
    public String removeOneFavorCourse(@PathVariable("courseId") String course_Id, HttpSession session){

        String courseId = course_Id;   /*请前端传入课程的id*/
        String userId = session.getAttribute("userId").toString();
        myCourseService.removeOneFavorCourse(courseId, userId);

        return "redirect:/home";
    }
}
