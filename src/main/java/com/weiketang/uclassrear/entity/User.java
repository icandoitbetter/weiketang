package com.weiketang.uclassrear.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {
    private String userId;
    private String password;
    private String userType;

    private String nickName = "null";
    private String userName = "null";
    private String institute = "null";

    private String phone = "null";
    private String email = "null";
    private String gender = "null";

    public String role = "null";
    public String className = "null";

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", institute='" + institute + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

}
