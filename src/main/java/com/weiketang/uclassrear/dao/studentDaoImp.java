package com.weiketang.uclassrear.dao;

import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class StudentDaoImp implements StudentDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Student findByStudentId(LoginUser user) {
        Query query = new Query(Criteria.where("studentId").is(user.userId));
        Student student = mongoTemplate.findOne(query, Student.class);
        return student;
    }
}
