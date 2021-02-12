package com.weiketang.uclassrear.entity;

import lombok.Data;

@Data
public class Student {
    public String nickName;
    public String password;
    public String studentName;
    public String institute;
    public String className;
    public String studentId;
    public String phone;
    public String email;
    public String gender;
    public String[] joinCourseId;
}
