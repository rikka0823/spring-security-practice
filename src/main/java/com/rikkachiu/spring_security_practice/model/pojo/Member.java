package com.rikkachiu.spring_security_practice.model.pojo;

import lombok.Data;

@Data
public class Member {
    private Integer memberId;
    private String email;
    private String password;
    private String name;
    private Integer age;
}
