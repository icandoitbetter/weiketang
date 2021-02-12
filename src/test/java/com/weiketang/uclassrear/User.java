package com.weiketang.uclassrear;

import lombok.Data;

@Data
public class User {
    public String name;

    @Override
    public String toString() {
        return "User{ " + "username = " + name + " }";
    }
}
