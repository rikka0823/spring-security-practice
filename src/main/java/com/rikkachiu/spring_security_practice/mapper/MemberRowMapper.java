package com.rikkachiu.spring_security_practice.mapper;

import com.rikkachiu.spring_security_practice.model.pojo.Member;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {
        Member member = new Member();
        member.setMemberId(resultSet.getInt("member_id"));
        member.setEmail(resultSet.getString("email"));
        member.setPassword(resultSet.getString("password"));
        member.setName(resultSet.getString("name"));
        member.setAge(resultSet.getInt("age"));

        return member;
    }
}
