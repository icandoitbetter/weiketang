package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;

public interface OnlineExamDao {
    String createOneQuestion(Question question, PaperQuestionManager paperQuestionManager);
    void deleteOneQuestion(PaperQuestionManager paperQuestionManager);

    boolean addResponseRecord(ResponseRecord responseRecord);

    String ExamHasBeenCreate(Exam exam);
    boolean QuestionInTheExam(Exam exam, Question question);
    String addExam(Exam exam);
}
