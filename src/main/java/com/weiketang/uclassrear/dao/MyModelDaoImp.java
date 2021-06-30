package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository
public class MyModelDaoImp implements MyModelDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OnlineExamDao onlineExamDao;

    @Value("${myconfig.warehousePath}")
    private String warehousePath;

    @Value("${myconfig.recycleBinPath}")
    private String recycleBinPath;


    @Override
    public MyModel createOneModel(String publisher, MyModel myModel, String modelType) {
        /*1.补充重要的信息*/
        myModel.setPublisherId(publisher);
        myModel.setModelType(modelType);

        /*2.插入一条model新纪录*/
        MyModel thisModel = mongoTemplate.insert(myModel);

        /*3.用新纪录的_id字段值更新该记录的modelId字段值*/
        this.updateModelId(thisModel.get_id());

        /*4.创建一个存放该model资源的文件夹*/
        File file = new File(warehousePath + "\\" + thisModel.get_id());
        file.mkdirs();

        return thisModel;
    }

    @Override
    public DeleteResult removeOneModelById(String modelId, String modelType) {
        /*1.删除一个model，需先删除其相关的活动，比如exam*/
        if(modelType.equals(new Course().getModelType())){
            List<Exam> exams = onlineExamDao.getExamsByCourseId(modelId);
            exams.forEach(item->onlineExamDao.removeOneExam(item.getActivityId()));
        }

        /*2.再将该model的资源文件夹移入回收站*/
        File srcFile = new File(warehousePath  + "\\" + modelId);
        File destFile = new File(recycleBinPath + "\\" + modelId);
        srcFile.renameTo(destFile);

        /*3.最后删除该model*/
        return mongoTemplate.remove(
                new Query(Criteria.where("_id").is(modelId)),
                MyModel.class
        );
    }

    @Override
    public ModelJoinRecord joinOneModel(String participatorId, String modelId) {
        /*加入一个model，只需添加一条 ModelJoinRecord 记录*/

        /*1.判断其是否已经加入，若是，则返回*/
        if(this.getRecord(participatorId, modelId) != null) {
            return null;
        }

        /*2.判断该model是否存在，若否，则返回*/
        MyModel myModel = this.getOneModelById(modelId);
        if(myModel == null) return null;

        /*3.填写相关信息*/
        ModelJoinRecord record = new ModelJoinRecord();
        record.setParticipatorId(participatorId);
        record.setModelId(modelId);
        record.setModelType(myModel.getModelType());

        /*4.创建一条 ModelJoinRecord 记录*/
        return mongoTemplate.insert(record);
    }

    @Override
    public DeleteResult exitOneModel(String participatorId, String modelId) {
        /*退出一个model，只需删除一条 ModelJoinRecord 记录*/

        /*1.移除相应的 ModelJoinRecord 记录*/
        return this.removeOneRecordByParticipatorId(participatorId, modelId);
    }

    @Override
    public List<MyModel> getModelsByPublisher(String publisherId, String modelType) {
        return mongoTemplate.find(
                new Query(Criteria.where("publisherId").is(publisherId)
                        .and("modelType").is(modelType)
                ),
                MyModel.class
        );
    }

    @Override
    public List<MyModel> getModelsByParticipator(String participatorId, String modelType) {
        List<Map> maps = this.getModelsMapByParticipator(participatorId, modelType);
        List<MyModel> models = new LinkedList<>();
        for (int i = 0; i < maps.size(); i++) {
            MyModel myModel = new MyModel();
            Map map = maps.get(i);

            myModel.setModelId(map.get("modelId").toString());
            myModel.setPublisherId(map.get("publisherId").toString());
            myModel.setModelType(map.get("modelType").toString());
            myModel.setTitle(map.get("title").toString());
            myModel.setSubject(map.get("subject").toString());
            myModel.setDescription(map.get("description").toString());
            myModel.setTerm(map.get("term").toString());

            models.add(myModel);
        }
        return models;
    }

    @Override
    public List<User> getParticipatorsByModel(String modelId) {
        List<Map> maps = this.getParticipatorsMapByModel(modelId);
        List<User> participators = new LinkedList<>();
        for (int i = 0; i < maps.size(); i++) {
            User participator = new User();
            Map map = maps.get(i);

            participator.setUserId(map.get("participatorId").toString());
            participator.setUserType(map.get("userType").toString());
            participator.setNickName(map.get("nickName").toString());
            participator.setUserName(map.get("userName").toString());
            participator.setInstitute(map.get("institute").toString());
            participator.setPhone(map.get("phone").toString());
            participator.setEmail(map.get("email").toString());
            participator.setGender(map.get("gender").toString());
            participator.setClassName(map.get("className").toString());

            participators.add(participator);
        }

        return participators;
    }

    @Override
    public List<MyModel> searchModels(String inputStr, String modelType) {
        Pattern pattern = Pattern.compile(inputStr, Pattern.CASE_INSENSITIVE);

        List<MyModel> models = mongoTemplate.find(
                new Query(
                        new Criteria().andOperator(
                                new Criteria().orOperator(
                                        Criteria.where("title").regex(pattern),
                                        Criteria.where("subject").regex(pattern),
                                        Criteria.where("description").regex(pattern)
                                ),
                                new Criteria().where("modelType").is(modelType)
                        )
                ),
                MyModel.class
        );

        return models;
    }

    @Override
    public MyModel getOneModelById(String modelId) {
        return mongoTemplate.findById(modelId, MyModel.class);
    }

    @Override
    public List<MyModel> getModelsByType(String modelType) {
        return mongoTemplate.find(
                new Query(Criteria.where("modelType").is(modelType)),
                MyModel.class
        );
    }

    @Override
    public List<MyModel> getAllModels() {
        return mongoTemplate.findAll(MyModel.class);
    }

    @Override
    public MyFavorModel collectOneModel(String modelId, String userId) {
        User user = mongoTemplate.findOne(
                new Query(Criteria.where("userId").is(userId)),
                User.class
        );
        MyModel myModel = mongoTemplate.findById(modelId, MyModel.class);
        if(user == null || myModel == null) return null;

        MyFavorModel favorModel = new MyFavorModel();
        favorModel.setModelId(modelId);
        favorModel.setModelType(myModel.getModelType());
        favorModel.setSubject(myModel.getSubject());
        favorModel.setUserId(userId);

        return mongoTemplate.insert(favorModel);
    }

    @Override
    public List<MyFavorModel> getFavorModelsByUserId(String userId, String modelType) {
        return mongoTemplate.find(
                new Query(Criteria.where("userId").is(userId)
                        .and("modelType").is(modelType)
                ),
                MyFavorModel.class
        );
    }

    @Override
    public DeleteResult removeOneFavorModel(String modelId, String userId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("modelId").is(modelId)
                        .and("userId").is(userId)
                ),
                MyFavorModel.class
        );
    }

    private ModelJoinRecord getRecord(String participatorId, String modelId){
        return mongoTemplate.findOne(
                new Query(Criteria.where("participatorId").is(participatorId)
                        .and("modelId").is(modelId)),
                ModelJoinRecord.class
        );
    }

    private DeleteResult removeOneRecordByParticipatorId(String participatorId, String modelId){
        return mongoTemplate.remove(
                new Query(Criteria.where("participatorId").is(participatorId)
                        .and("modelId").is(modelId)
                ),
                ModelJoinRecord.class
        );
    }

    private List<Map> getModelsMapByParticipator(String participatorId, String modelType){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup(
                        "model",
                        "modelId",
                        "modelId",
                        "models"
                ),
                Aggregation.match(
                        Criteria.where("participatorId").is(participatorId)
                                .andOperator(Criteria.where("modelType").is(modelType))
                ),
                Aggregation.project(
                        "modelId", "modelType",
                        "models.publisherId", "models.title",
                        "models.subject", "models.description", "models.term"
                ).andExclude("_id"),
                Aggregation.unwind("publisherId"),
                Aggregation.unwind("title"),
                Aggregation.unwind("subject"),
                Aggregation.unwind("description"),
                Aggregation.unwind("term")
        );
        return mongoTemplate.aggregate(
                aggregation,
                "modelJoinRecord",
                Map.class
        ).getMappedResults();
    }

    private List<Map> getParticipatorsMapByModel(String modelId){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup(
                        "user",
                        "participatorId",
                        "userId",
                        "participators"
                ),
                Aggregation.match(
                        Criteria.where("modelId").is(modelId)
                ),
                Aggregation.project(
                        "participatorId",
                        "participators.userType", "participators.nickName", "participators.userName", "participators.institute",
                        "participators.phone", "participators.email", "participators.gender",
                        "participators.className"
                ).andExclude("_id"),
                Aggregation.sort(Sort.Direction.ASC, "participatorId"),
                Aggregation.unwind("userType"),
                Aggregation.unwind("nickName"),
                Aggregation.unwind("userName"),
                Aggregation.unwind("institute"),
                Aggregation.unwind("phone"),
                Aggregation.unwind("email"),
                Aggregation.unwind("gender"),
                Aggregation.unwind("className")
        );
        return mongoTemplate.aggregate(
                aggregation,
                "modelJoinRecord",
                Map.class
        ).getMappedResults();
    }

    private MyModel updateModelId(String modelId){
        return mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(modelId)),
                new Update().set("modelId", modelId),
                MyModel.class
        );
    }
}
