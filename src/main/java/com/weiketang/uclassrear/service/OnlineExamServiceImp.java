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
        return onlineExamDao.removeOneExam(examId);
    }

    @Override
    public List<Exam> getExamsByCourseId(String courseId) {
        return onlineExamDao.getExamsByCourseId(courseId);
    }

    @Override
    public List<Exam> getExamsByStudentId(String studentId) {
        return onlineExamDao.getExamsByStudentId(studentId);
    }

    @Override
    public Exam getOneExamByExamId(String examId) {
        return onlineExamDao.getOneExamByExamId(examId);
    }

    @Override
    public QuestionRecord createOneQuestionInPaper(String examId, Question question,
                                                   String authorId, String questionType,
                                                   List<String> answerList) {
        question.setAnswer(this.getStringFromList(answerList));
        return onlineExamDao.createOneQuestionInPaper(question, examId, authorId, questionType);
    }

    @Override
    public DeleteResult removeOneQuestionFromPaper(String examId, String questionId) {
        return onlineExamDao.removeOneQuestionFromPaper(examId, questionId);
    }

    @Override
    public DeleteResult removeOneQuestionFromDB(String questionId) {
        return onlineExamDao.removeOneQuestionFromDB(questionId);
    }

    @Override
    public ResponseRecord answerOneQuestion(String examId, String questionId,
                                            String respondentId, List<String> responseList) {
        return onlineExamDao.insertOneResponseRecord(
                examId,
                questionId,
                respondentId,
                this.getStringFromList(responseList)
        );
    }

    @Override
    public List<Question> getOnePartOfPaper(String examId, String questionType) {

        return onlineExamDao.getOnePartOfPaper(examId, questionType);
    }

    @Override
    public List<ResponseRecord> getOnePartOfAnswerSheet(String examId, String respondentId, String questionType) {
        return onlineExamDao.getOnePartOfAnswerSheet(examId, respondentId, questionType);
    }

    private String getStringFromList(List<String> list){
        if(list == null || list.size() == 0) return "null";
        Collections.sort(list);
        return list.toString();
    }
}
