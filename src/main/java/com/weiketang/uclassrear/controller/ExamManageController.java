package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.*;
import com.weiketang.uclassrear.service.OnlineExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Controller
public class ExamManageController {
    @Autowired
    OnlineExamService onlineExamService;

    @GetMapping("/createOneExam/{courseId}")
    public String createOneExam(@PathVariable("courseId") String course_id, HttpSession session){

        Exam exam = new Exam();         /*请前端传入用户输入的exam信息*/
        String courseId = course_id;    /*请前端传入exam所属的课程的id*/

        /*测试数据*/
        Random r = new Random();
        int ran = r.nextInt(5);
        exam = Creator.ExamCreator(ran);
        /*测试数据*/

        String publisherId = session.getAttribute("userId").toString();
        onlineExamService.createOneExam(exam, publisherId, courseId);

        return "redirect:/home";
    }

    @GetMapping("/removeOneExam/{examId}")
    public String removeOneExam(@PathVariable("examId") String exam_id){

        String examId = exam_id; /*请前端传入待删除的exam的id*/
        onlineExamService.removeOneExam(examId);

        return "redirect:/home";
    }

    @GetMapping("/getExamsByCourseId/{courseId}")
    public String getExamsByCourseId(@PathVariable("courseId") String course_id, Model model){

        String courseId = course_id;   /*请前端传入exam所属的课程的id*/
        List<Exam> exams = onlineExamService.getExamsByCourseId(courseId);
        model.addAttribute("exams", exams);

        return "test/5";
    }

    @GetMapping("/getExamsByStudentId")
    public String getExamsByStudentId(HttpSession session, Model model){

        String studentId = session.getAttribute("userId").toString();
        List<Exam> exams = onlineExamService.getExamsByStudentId(studentId);
        model.addAttribute("exams", exams);

        return "test/5";
    }

    @GetMapping("/getOneExamByExamId/{examId}")
    public String getOneExamByExamId(@PathVariable("examId") String exam_id, Model model){

        String examId = exam_id;     /*请前端传入待查找的exam的id*/
        Exam exam = onlineExamService.getOneExamByExamId(examId);
        model.addAttribute("exam3", exam);

        return "redirect:/home";
    }

    @GetMapping("/createOneQuestionInPaper/{examId}")
    public String createOneQuestionInPaper(@PathVariable("examId") String exam_id, HttpSession session){

        /*请前端传入下列内容：examId、question、questionType、answerList*/
        String examId = exam_id;                 /*新建试题所属的考试的id*/
        Question question = new Question();     /*新建试题的内容*/
        String questionType = "null";           /*题型（单选、多选、填空、判断）*/
        List<String> answerList = new LinkedList<>(); /*答案*/

        /*测试数据*/
        Random r = new Random();
        int ran = r.nextInt(20);
        question = Creator.questionCreator(ran);
        questionType = question.getQuestionType();
        answerList.add(question.getAnswer());
        /*测试数据*/

        String authorId = session.getAttribute("userId").toString();
        onlineExamService.createOneQuestionInPaper(examId, question, authorId, questionType, answerList);

        return "redirect:/home";
    }

    @GetMapping("/removeOneQuestionFromPaper/{examId}/{questionId}")
    public String removeOneQuestionFromPaper(@PathVariable("examId") String exam_id,
                                             @PathVariable("questionId") String question_id){

        String examId = exam_id;             /*请前端传入待删除的题目所属的考试的id*/
        String questionId = question_id;     /*请前端传入待删除的题目的id*/
        onlineExamService.removeOneQuestionFromPaper(examId, questionId);

        return "redirect:/home";
    }

    @GetMapping("/answerOneQuestion")
    public String answerOneQuestion(HttpSession session){

        String examId = "null";         /*请前端传入当前考试的id*/
        String questionId = "null";     /*请前端传入当前作答题目的id*/
        List<String> responseList = new LinkedList<>(); /*请前端传入考生该题的作答*/
        String respondentId = session.getAttribute("userId").toString();
        onlineExamService.answerOneQuestion(examId, questionId, respondentId, responseList);

        return "redirect:/home";
    }

    @GetMapping("/getOnePaperByExamId/{examId}")
    public String getOnePaperByExamId(@PathVariable("examId") String exam_id, Model model){

        String examId = exam_id;     /*请前端传入当前考试的id*/
        List<Question> sin = onlineExamService.getOnePartOfPaper(
                examId, new SingleChoiceQuestion().getQuestionType());
        List<Question> mul = onlineExamService.getOnePartOfPaper(
                examId, new MultiChoiceQuestion().getQuestionType());
        List<Question> fill = onlineExamService.getOnePartOfPaper(
                examId, new FillQuestion().getQuestionType());
        List<Question> judge = onlineExamService.getOnePartOfPaper(
                examId, new JudgeQuestion().getQuestionType());

        model.addAttribute("sinQue", sin);
        model.addAttribute("mulQue", mul);
        model.addAttribute("fillQue", fill);
        model.addAttribute("judgeQue", judge);

        return "test/6";
    }

    @GetMapping("/getOneAnswerSheet/{examId}")
    public String getOneAnswerSheet(@PathVariable("examId") String exam_id, HttpSession session, Model model){

        String examId = exam_id;     /*请前端传入当前考试的id*/
        String respondentId = session.getAttribute("userId").toString();

        List<ResponseRecord> sin = onlineExamService.getOnePartOfAnswerSheet(
                examId, respondentId, new SingleChoiceQuestion().getQuestionType());
        List<ResponseRecord> mul = onlineExamService.getOnePartOfAnswerSheet(
                examId, respondentId, new MultiChoiceQuestion().getQuestionType());
        List<ResponseRecord> fill = onlineExamService.getOnePartOfAnswerSheet(
                examId, respondentId, new FillQuestion().getQuestionType());
        List<ResponseRecord> judge = onlineExamService.getOnePartOfAnswerSheet(
                examId, respondentId, new JudgeQuestion().getQuestionType());

        model.addAttribute("sinRe", sin);
        model.addAttribute("mulRe", mul);
        model.addAttribute("fillRe", fill);
        model.addAttribute("judgeRe", judge);

        return "test/7";
    }

}
