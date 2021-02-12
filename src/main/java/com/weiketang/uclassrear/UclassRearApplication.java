package com.weiketang.uclassrear;

import com.weiketang.uclassrear.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class UclassRearApplication {
    public static void main(String[] args) {
        SpringApplication.run(UclassRearApplication.class, args);
    }
}
