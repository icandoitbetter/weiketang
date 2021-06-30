package com.weiketang.uclassrear.service;


import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;

import java.util.List;

public interface OnlineExamService {
    /*创建、删除一场考试*/
    Exam createOneExam(Exam exam, String publisherId, String courseId); /*某位教师发布某门课程的某场考试*/
    DeleteResult removeOneExam(String examId);                          /*根据考试ID，删除某门考试*/

    /*查询考试*/
    List<Exam> getExamsByCourseId(String courseId);                     /*查询某门课程的所有考试*/
    List<Exam> getExamsByStudentId(String studentId);                   /*某个学生查询自己要参加的所有考试*/
    Exam getOneExamByExamId(String examId);                             /*查询某一场考试的信息*/

    /*创建、删除、回答一道题*/
    QuestionRecord createOneQuestionInPaper(String examId, Question question, String authorId,  /*创建一道题*/
                                      String questionType, List<String> answerList);
    DeleteResult removeOneQuestionFromPaper(String examId, String questionId);                  /*删除一道题*/
    DeleteResult removeOneQuestionFromDB(String questionId);                                    /*从数据库中删除一道题（备用API）*/
    ResponseRecord answerOneQuestion(String examId, String questionId,                          /*某个学生在某场考试上回答某道题*/
                                     String respondentId, List<String> responseList);

    /*查询一张试卷的全部题目*/
    List<Question> getOnePartOfPaper(String examId, String questionType);                       /*获取某场考试的完整试题*/
    /*查询一位考生在一张试卷上的答题记录*/
    List<ResponseRecord> getOnePartOfAnswerSheet(String examId, String respondentId, String questionType);  /*获取某位学生在某场考试上的某个模块（单选/多选/填空/判断）的作答记录*/
}
