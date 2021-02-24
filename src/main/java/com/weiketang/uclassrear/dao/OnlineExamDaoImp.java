package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class OnlineExamDaoImp implements OnlineExamDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String createOneQuestion(Question question, PaperQuestionManager paperQuestionManager) {
        String questionId = new String();
        if(question instanceof  SingleChoiceQuestion == true){
            SingleChoiceQuestion result = mongoTemplate.insert((SingleChoiceQuestion) question);
            questionId = result.getQuestionId();
        }
        else if(question instanceof MultiChoiceQuestion == true){
            MultiChoiceQuestion result = mongoTemplate.insert((MultiChoiceQuestion) question);
            questionId = result.getQuestionId();
        }
        else if(question instanceof FillQuestion == true){
            FillQuestion result = mongoTemplate.insert((FillQuestion) question);
            questionId = result.getQuestionId();
        }
        else if(question instanceof JudgeQuestion == true){
            JudgeQuestion result = mongoTemplate.insert((JudgeQuestion) question);
            questionId = result.getQuestionId();
        }

        if(questionId != null){
            paperQuestionManager.setQuestionId(questionId);
            mongoTemplate.insert(paperQuestionManager);
            return questionId;
        }
        return null;
    }

    @Override
    public void deleteOneQuestion(PaperQuestionManager p) {

        Query query = new Query(Criteria.where("questionId").is(p.getQuestionId()).
                                        and("questionType").is(p.getQuestionType()).
                                        and("examId").is(p.getExamId()));
        mongoTemplate.remove(query, PaperQuestionManager.class);

    }

    @Override
    public boolean addResponseRecord(ResponseRecord responseRecord) {
        ResponseRecord result = mongoTemplate.insert(responseRecord);

        if(result != null){
            return true;
        }

        return false;
    }

    @Override
    public String ExamHasBeenCreate(Exam exam) {
        if(exam == null){
            return null;
        }

        String examName = exam.getExamTitle();
        String term = exam.getTerm();

        Query query = new Query(Criteria.where("examTitle").is(examName).and("term").is(term));
        Exam result = mongoTemplate.findOne(query, Exam.class);
        if(result != null){
            return result.getExamId();
        }

        return null;
    }

    @Override
    public boolean QuestionInTheExam(Exam exam, Question question) {
        PaperQuestionManager p = new PaperQuestionManager();
        p.setExamId(exam.getExamId());
        p.setQuestionId(question.getQuestionId());
        p.setQuestionType(question.getType());

        Query query = new Query(Criteria.where("examId").is(p.getExamId()).
                                        and("questionId").is(p.getQuestionId()).
                                        and("questionType").is(p.getQuestionType()));
        PaperQuestionManager result = mongoTemplate.findOne(query, PaperQuestionManager.class);
        if(result != null){
            return true;
        }
        return false;
    }

    @Override
    public String addExam(Exam exam) {
        String examId = this.ExamHasBeenCreate(exam);
        if(examId == null){
            Exam result = mongoTemplate.insert(exam);
            examId = result.getExamId();
        }
        return examId;
    }
}
