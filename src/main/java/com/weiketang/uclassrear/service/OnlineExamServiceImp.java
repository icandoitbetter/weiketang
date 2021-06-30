package com.weiketang.uclassrear.service;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.dao.OnlineExamDao;
import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OnlineExamServiceImp implements OnlineExamService {
    @Autowired
    private OnlineExamDao onlineExamDao;

    @Override
    public Exam createOneExam(Exam exam, String publisherId, String courseId) {
        return onlineExamDao.insertOneExam(exam, publisherId, courseId);
    }

    @Override
    public DeleteResult removeOneExam(String examId) {
        if(examId == null) return null;
        return onlineExamDao.removeOneExam(examId);
    }

    @Override
    public List<Exam> getExamsByCourseId(String courseId) {
        if(courseId == null) return null;
        return onlineExamDao.getExamsByCourseId(courseId);
    }

    @Override
    public List<Exam> getExamsByStudentId(String studentId) {
        if(studentId == null) return null;
        return onlineExamDao.getExamsByStudentId(studentId);
    }

    @Override
    public Exam getOneExamByExamId(String examId) {
        if(examId == null) return null;
        return onlineExamDao.getOneExamByExamId(examId);
    }

    @Override
    public QuestionRecord createOneQuestionInPaper(String examId, Question question,
                                                   String authorId, String questionType,
                                                   List<String> answerList) {
        if(examId == null || question == null ||
        authorId == null || questionType == null || answerList == null) {
            return null;
        }

        question.setAnswer(this.getStringFromList(answerList));
        return onlineExamDao.createOneQuestionInPaper(question, examId, authorId, questionType);
    }

    @Override
    public DeleteResult removeOneQuestionFromPaper(String examId, String questionId) {
        if(examId == null || questionId == null) return null;
        return onlineExamDao.removeOneQuestionFromPaper(examId, questionId);
    }

    @Override
    public DeleteResult removeOneQuestionFromDB(String questionId) {
        if(questionId == null) return null;
        return onlineExamDao.removeOneQuestionFromDB(questionId);
    }

    @Override
    public ResponseRecord answerOneQuestion(String examId, String questionId,
                                            String respondentId, List<String> responseList) {
        if(examId == null || questionId == null ||
                respondentId == null || responseList ==  null) return null;

        return onlineExamDao.insertOneResponseRecord(
                examId,
                questionId,
                respondentId,
                this.getStringFromList(responseList)
        );
    }

    @Override
    public List<Question> getOnePartOfPaper(String examId, String questionType) {
        if(examId == null || questionType == null) return null;
        return onlineExamDao.getOnePartOfPaper(examId, questionType);
    }

    @Override
    public List<ResponseRecord> getOnePartOfAnswerSheet(String examId, String respondentId,
                                                        String questionType) {
        if(examId == null || respondentId == null ||
                questionType == null) return null;
        return onlineExamDao.getOnePartOfAnswerSheet(examId, respondentId, questionType);
    }

    private String getStringFromList(List<String> list){
        if(list == null || list.size() == 0) return "null";
        Collections.sort(list);
        return list.toString();
    }
}
