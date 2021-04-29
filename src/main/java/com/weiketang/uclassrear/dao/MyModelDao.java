package com.weiketang.uclassrear.dao;

import com.mongodb.client.result.DeleteResult;
import com.weiketang.uclassrear.entity.*;
import java.util.List;

public interface MyModelDao {

    MyModel createOneModel(String publisher, MyModel myModel, String ModelType);
    DeleteResult removeOneModelById(String modelId, String modelType);

    ModelJoinRecord joinOneModel(String participatorId, String modelId);
    DeleteResult exitOneModel(String participatorId, String modelId);

    List<MyModel> getModelsByPublisher(String publisherId);
    List<MyModel> getModelsByParticipator(String participatorId, String modelType);
    List<User> getParticipatorsByModel(String modelId);
    List<MyModel> searchModels(String inputStr, String modelType);
    MyModel getOneModelById(String modelId);
    List<MyModel> getModelsByType(String modelType);
    List<MyModel> getAllModels();

    MyFavorModel collectOneModel(String modelId, String userId);
    List<MyFavorModel> getFavorModelsByUserId(String userId, String modelType);
    DeleteResult removeOneFavorModel(String modelId, String userId);

}
