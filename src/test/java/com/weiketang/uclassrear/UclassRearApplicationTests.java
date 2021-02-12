package com.weiketang.uclassrear;

import com.weiketang.uclassrear.entity.Student;
import com.weiketang.uclassrear.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootTest
class UclassRearApplicationTests {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        Query query = new Query(Criteria.where("teacherName").is("王虹"));
        Teacher result = mongoTemplate.findOne(query, Teacher.class);
        if(result != null){
            System.out.println("name = " + result.teacherName + ", " + "Id = " + result.teacherId);
        }
    }

}
