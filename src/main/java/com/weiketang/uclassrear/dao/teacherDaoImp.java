package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.Teacher;
import com.weiketang.uclassrear.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDaoImp implements TeacherDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Teacher findByTeacherId(LoginUser user) {
        Query query = new Query(Criteria.where("teacherId").is(user.userId));
        Teacher teacher = mongoTemplate.findOne(query, Teacher.class);
        return teacher;
    }
}
