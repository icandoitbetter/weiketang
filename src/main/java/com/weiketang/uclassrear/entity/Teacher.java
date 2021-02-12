package com.weiketang.uclassrear.entity;

import lombok.Data;

@Data
public class Teacher {
    public String nickName;
    public String password;
    public String teacherName;
    public String institute;
    public String role;
    public String teacherId;
    public String phone;
    public String email;
    public String gender;
    public String[] teachCourseId;
}
