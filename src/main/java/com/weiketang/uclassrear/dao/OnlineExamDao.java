package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;

import java.util.List;

public interface OnlineExamDao {
    /*创建、删除一场考试*/
    Exam insertOneExam(Exam exam, String publisherId, String courseId);
    DeleteResult removeOneExam(String examId);

    /*查询考试*/
    List<Exam> getExamsByCourseId(String courseId);
    List<Exam> getExamsByStudentId(String studentId);
    Exam getOneExamByExamId(String examId);

    /*创建、删除、回答一道题*/
    Question insertOneQuestion(Question question, String authorId, String questionType);
    QuestionRecord createOneQuestionInPaper(Question question, String examId,
                                            String authorId, String questionType);
    DeleteResult removeOneQuestionFromPaper(String examId, String questionId);
    DeleteResult removeOneQuestionFromDB(String questionId);
    ResponseRecord insertOneResponseRecord(String examId, String questionId, String respondentId, String response);

    /*查询一张试卷的全部题目*/
    List<Question> getOnePartOfPaper(String examId, String questionType);
    /*查询一位考生在一张试卷上的答题记录*/
    List<ResponseRecord> getOnePartOfAnswerSheet(String examId, String respondentId, String questionType);

}
