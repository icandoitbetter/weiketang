package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class OnlineExamDaoImp implements OnlineExamDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Exam insertOneExam(Exam exam, String publisherId, String courseId) {
        /*1.检查该课程是否存在，若否，则返回*/
        Course course = this.getOneCourseById(courseId);
        if(course == null) return null;

        /*2.插入一条exam记录*/
        exam.setPublisherId(publisherId);
        exam.setModelId(course.getModelId());
        Exam thisExam = mongoTemplate.insert(exam);

        /*3.用该exam记录的_id字段值更新activityId字段值*/
        mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(thisExam.get_id())),
                new Update().set("activityId", thisExam.get_id()),
                Exam.class
        );

        return thisExam;
    }

    @Override
    public DeleteResult removeOneExam(String examId) {
        if(this.getOneExamByExamId(examId) == null) return null;
        this.removeQuestionRecord(examId);
        return this.removeExamRecord(examId);
    }

    @Override
    public List<Exam> getExamsByCourseId(String courseId) {
        return mongoTemplate.find(
                new Query(Criteria.where("modelId").is(courseId)),
                Exam.class
        );
    }

    @Override
    public List<Exam> getExamsByStudentId(String studentId) {
        /*1.获取该学生参加的全部课程*/
        List<ModelJoinRecord> records = mongoTemplate.find(
                new Query(Criteria.where("participatorId").is(studentId)),
                ModelJoinRecord.class
        );

        /*2.获取上述每门课程的考试*/
        List<Exam> exams = new LinkedList<>();
        for (int i = 0; i < records.size(); i++) {
            String courseId = records.get(i).getModelId();
            List<Exam> examList = this.getExamsByCourseId(courseId);
            exams.addAll(examList);
        }

        return exams;
    }

    @Override
    public Exam getOneExamByExamId(String examId) {
        return mongoTemplate.findById(examId, Exam.class);
    }

    @Override
    public Question insertOneQuestion(Question question, String authorId, String questionType) {

        question.setAuthorId(authorId);
        question.setQuestionType(questionType);
        Question result = mongoTemplate.insert(question);

        mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(result.get_id())),
                new Update().set("questionId", result.get_id()),
                Question.class
        );

        return result;
    }

    @Override
    public QuestionRecord createOneQuestionInPaper(Question question, String examId,
                                                   String authorId, String questionType) {

        /*1.检查exam是否已创建，若否，则返回*/
        if(this.getOneExamByExamId(examId) == null) return null;

        /*2.将题目存入数据库中*/
        Question thisQuestion = this.insertOneQuestion(question, authorId, questionType);

        /*3.创建一条 QuestionRecord 记录，并存入数据库*/
        QuestionRecord record = new QuestionRecord();
        record.setQuestionId(thisQuestion.get_id());
        record.setExamId(examId);
        record.setQuestionType(questionType);

        return mongoTemplate.insert(record);
    }

    @Override
    public DeleteResult removeOneQuestionFromPaper(String examId, String questionId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("examId").is(examId)
                        .and("questionId").is(questionId)),
                QuestionRecord.class
        );
    }

    @Override
    public DeleteResult removeOneQuestionFromDB(String questionId) {
        mongoTemplate.remove(
                new Query(Criteria.where("questionId").is(questionId)),
                QuestionRecord.class
        );
        mongoTemplate.remove(
                new Query(Criteria.where("questionId").is(questionId)),
                ResponseRecord.class
        );
        return mongoTemplate.remove(
                new Query(Criteria.where("questionId").is(questionId)),
                Question.class
        );
    }

    @Override
    public ResponseRecord insertOneResponseRecord(String examId, String questionId, String respondentId, String response) {
        /*1.查询该考试试卷是否存在该题，若否，则返回*/
        QuestionRecord qRecord = this.getOneRecordFromPaper(examId, questionId);
        if(qRecord == null) return null;

        /*2.填写相关信息*/
        ResponseRecord record = new ResponseRecord();
        record.setRespondentId(respondentId);
        record.setExamId(examId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        record.setSubmitDate(df.format(new Date()));
        record.setResponse(response);
        record.setGrade(this.getGrade(questionId, response));
        record.setQuestionRecordId(qRecord.get_id());
        record.setQuestionType(qRecord.getQuestionType());
        record.setQuestionId(qRecord.getQuestionId());

        /*3.清除该考生对该场考试该题的旧的做题记录*/
        this.removeOneResponseRecord(respondentId, examId, questionId);

        /*4.做题记录存入数据库*/
        return mongoTemplate.insert(record);
    }

    @Override
    public List<Question> getOnePartOfPaper(String examId, String questionType) {
        List<Map> maps = this.getQuestionsMapByExamId(examId, questionType);
        List<Question> questions = new LinkedList<>();
        for (int i = 0; i < maps.size(); i++) {
            Map map = maps.get(i);
            Question question = new Question();

            question.setQuestionId(map.get("questionId").toString());
            question.setAuthorId(map.get("authorId").toString());
            question.setQuestionType(map.get("questionType").toString());

            question.setSubject(map.get("subject").toString());
            question.setContent(map.get("content").toString());
            question.setAnswer(map.get("answer").toString());
            question.setAnalysis(map.get("analysis").toString());

            question.setOptA(map.get("optA").toString());
            question.setOptB(map.get("optB").toString());
            question.setOptC(map.get("optC").toString());
            question.setOptD(map.get("optD").toString());

            question.setScore(Integer.valueOf(map.get("score").toString()));

            questions.add(question);
        }
        return questions;
    }

    @Override
    public List<ResponseRecord> getOnePartOfAnswerSheet(String examId, String respondentId,
                                                        String questionType) {
        return mongoTemplate.find(
                new Query(
                        Criteria.where("examId").is(examId)
                                .and("respondentId").is(respondentId)
                                .and("questionType").is(questionType)
                ).with(Sort.by(Sort.Order.asc("questionId"))),
                ResponseRecord.class
        );
    }

    private Course getOneCourseById(String courseId){
        return (Course) mongoTemplate.findById(courseId, MyModel.class);
    }

    private Question getOneQuestionById(String questionId){
        return mongoTemplate.findById(questionId, Question.class);
    }

    private DeleteResult removeExamRecord(String examId){
        return mongoTemplate.remove(
                new Query(Criteria.where("_id").is(examId)),
                Exam.class
        );
    }

    private DeleteResult removeQuestionRecord(String examId){
        return mongoTemplate.remove(
                new Query(Criteria.where("examId").is(examId)),
                QuestionRecord.class
        );
    }

    private int getGrade(String questionId, String response){
        Question question = this.getOneQuestionById(questionId);
        if(question.getAnswer().equals(response)) return question.getScore();
        return 0;
    }

    private DeleteResult removeOneResponseRecord(String respondentId, String examId, String questionId){
        return mongoTemplate.remove(
                new Query(
                        Criteria.where("examId").is(examId)
                                .and("questionId").is(questionId)
                                .and("respondentId").is(respondentId)
                ),
                ResponseRecord.class
        );
    }

    private QuestionRecord getOneRecordFromPaper(String examId, String questionId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("examId").is(examId)
                                .and("questionId").is(questionId)
                ),
                QuestionRecord.class
        );
    }

    private List<Map> getQuestionsMapByExamId(String examId, String questionType) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup(
                        "question",
                        "questionId",
                        "questionId",
                        "questions"
                ),
                Aggregation.match(
                        Criteria.where("examId").is(examId)
                                .andOperator(Criteria.where("questionType").is(questionType))
                ),
                Aggregation.sort(Sort.Direction.ASC, "questionId"),
                Aggregation.project(
                        "questionId", "questionType",
                        "questions.authorId", "questions.subject", "questions.content",
                        "questions.optA", "questions.optB", "questions.optC", "questions.optD",
                         "questions.answer", "questions.analysis", "questions.score"
                ).andExclude("_id"),
                Aggregation.unwind("authorId"),
                Aggregation.unwind("subject"),
                Aggregation.unwind("content"),
                Aggregation.unwind("optA"),
                Aggregation.unwind("optB"),
                Aggregation.unwind("optC"),
                Aggregation.unwind("optD"),
                Aggregation.unwind("answer"),
                Aggregation.unwind("analysis"),
                Aggregation.unwind("score")
        );
        return mongoTemplate.aggregate(
                aggregation,
                "questionRecord",
                Map.class
        ).getMappedResults();
    }

}
