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

        Student student1 = new Student();
        student1.setUserId("SA20225001");
        student1.setUserName("张华");
        student1.setPassword("123456");
        Student student2 = new Student();
        student2.setUserId("SA20225002");
        student2.setUserName("李磊");
        student2.setPassword("123456");
        Student student3 = new Student();
        student3.setUserId("SA20225003");
        student3.setUserName("赵刚");
        student3.setPassword("123456");

        mongoTemplate.insert(student1);
        mongoTemplate.insert(student2);
        mongoTemplate.insert(student3);

        Teacher teacher1 = new Teacher();
        teacher1.setUserId("STR20225001");
        teacher1.setUserName("梁明");
        teacher1.setPassword("674745");
        Teacher teacher2 = new Teacher();
        teacher2.setUserId("STR20225002");
        teacher2.setUserName("黄利");
        teacher2.setPassword("123456");
        Teacher teacher3 = new Teacher();
        teacher3.setUserId("STR20225003");
        teacher3.setUserName("王虹");
        teacher3.setPassword("123456");

        mongoTemplate.insert(teacher1);
        mongoTemplate.insert(teacher2);
        mongoTemplate.insert(teacher3);

    }

}
