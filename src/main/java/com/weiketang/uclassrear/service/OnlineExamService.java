package com.weiketang.uclassrear.service;


import com.weiketang.uclassrear.entity.Exam;
import com.weiketang.uclassrear.entity.Question;

import javax.servlet.http.HttpSession;

public interface OnlineExamService {
    boolean createOneQuestion(Exam exam, HttpSession session, Question question);
    void deleteOneQuestion(Question question, Exam exam);
    boolean answerOneQuestion(Exam exam, HttpSession session, Question question, String response);
    String creatOneExam(HttpSession session, Exam exam);
}
