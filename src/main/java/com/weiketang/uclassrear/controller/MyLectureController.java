package com.weiketang.uclassrear.controller;

import com.weiketang.uclassrear.entity.Exam;
import com.weiketang.uclassrear.entity.SingleChoiceQuestion;
import com.weiketang.uclassrear.service.OnlineExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MyLectureController {
    @Autowired
    private OnlineExamService onlineExamService;

    public Exam ExamCreator(Exam exam){
        exam.setDeadline("2022-12-05 00:00:00");
        exam.setExamDescription("描述：这是一门幼儿园学前班数学考试");
        exam.setExamTitle("考试标题：学前班数学期末考试");
        exam.setFullScore(100);
        exam.setTerm("2021-2022春季学期");
        exam.setTips("提示：带上铅笔尺子和橡皮擦");

        return exam;
    }

    public SingleChoiceQuestion sQuestionCreator(SingleChoiceQuestion singleChoiceQuestion){
        singleChoiceQuestion.setType("SingleChoiceQuestion");
        singleChoiceQuestion.setQuestion("题目：1+1等于几？");
        singleChoiceQuestion.setOptionA("等于1");
        singleChoiceQuestion.setOptionB("等于2");
        singleChoiceQuestion.setOptionC("等于3");
        singleChoiceQuestion.setOptionD("不知道");

        singleChoiceQuestion.setAnswer("B");
        singleChoiceQuestion.setScore(2);
        singleChoiceQuestion.setSubject("幼儿园算术");
        singleChoiceQuestion.setAnalysis("分析：这是一道加法计算题");
        return singleChoiceQuestion;
    }


    @GetMapping("/myLecture")
    public String myLecture(HttpSession session){
        if(session.getAttribute("userRole").equals("STUDENT")){
            return "myLecture/myLecture_student";
        }

        return "myLecture/myLecture_teacher";
    }

    @GetMapping("/onlineExamTeacher")
    public String editPaper(){
        return "myLecture/onlineExam_teacher";
    }

    @GetMapping("/creatExam")
    public String creatExam(HttpSession session){
        Exam exam = new Exam();
        exam = this.ExamCreator(exam);
        String examId = onlineExamService.creatOneExam(session, exam);
        exam.setExamId(examId);

        SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion = this.sQuestionCreator(singleChoiceQuestion);
        onlineExamService.createOneQuestion(exam, session, singleChoiceQuestion);
        return "redirect:/myLecture";
    }

    @GetMapping("/takeExam")
    public String takeExam(HttpSession session){
        Exam exam = new Exam();
        exam = this.ExamCreator(exam);

        SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion = this.sQuestionCreator(singleChoiceQuestion);
        singleChoiceQuestion.setType("SingleChoiceQuestion");
        singleChoiceQuestion.setQuestionId("602a6c55ff296f22d02c3913");
        exam.setExamId("602a6328434c5f5f8ce650e4");

        onlineExamService.answerOneQuestion(exam, session, singleChoiceQuestion, "A");

        return "redirect:/myLecture";
    }

    @GetMapping("/deleteExam")
    public String deleteQuestion(){
        Exam exam = new Exam();
        exam = this.ExamCreator(exam);
        exam.setExamId("602a6328434c5f5f8ce650e4");

        SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion = this.sQuestionCreator(singleChoiceQuestion);
        singleChoiceQuestion.setQuestionId("602a6c55ff296f22d02c3913");

        onlineExamService.deleteOneQuestion(singleChoiceQuestion, exam);
        return "redirect:/myLecture";
    }
}
