package com.weiketang.uclassrear.service;

import com.weiketang.uclassrear.dao.OnlineExamDao;
import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OnlineExamServiceImp implements OnlineExamService {
    @Autowired
    private OnlineExamDao onlineExamDao;

    @Override
    public boolean createOneQuestion(Exam exam, HttpSession session, Question question) {
        if(onlineExamDao.ExamHasBeenCreate(exam) == null){
            if(this.creatOneExam(session, exam) == null){
                return false;
            }
        }
        question.setAuthorId(session.getAttribute("loginUserId").toString());

        PaperQuestionManager p = new PaperQuestionManager();
        p.setExamId(exam.getExamId());
        p.setQuestionType(question.getType());

        String questionId = onlineExamDao.createOneQuestion(question, p);
        if(questionId != null){
            return true;
        }
        return false;
    }

    @Override
    public void deleteOneQuestion(Question question, Exam exam) {
        PaperQuestionManager p = new PaperQuestionManager();
        p.setExamId(exam.getExamId());
        p.setQuestionId(question.getQuestionId());
        p.setQuestionType(question.getType());

        onlineExamDao.deleteOneQuestion(p);
    }

    public boolean answerOneQuestion(Exam exam, HttpSession session, Question question, String response) {
        if(!onlineExamDao.QuestionInTheExam(exam, question)){
            return false;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ResponseRecord responseRecord = new ResponseRecord();
        responseRecord.setQuestionId(question.getQuestionId());
        responseRecord.setExamId(exam.getExamId());
        responseRecord.setRespondentId(session.getAttribute("loginUserId").toString());
        responseRecord.setSubmitDate(df.format(new Date()));
        responseRecord.setResponse(response);
        responseRecord.setQuestionType(question.getType());

        int grade = 0;
        if(question.getAnswer().equals(response)){
            grade = question.score;
        }
        responseRecord.setGrade(grade);

        return onlineExamDao.addResponseRecord(responseRecord);
    }

    @Override
    public String creatOneExam(HttpSession session, Exam exam) {
        exam.setPublisherId(session.getAttribute("loginUserId").toString());
        return onlineExamDao.addExam(exam);
    }
}
