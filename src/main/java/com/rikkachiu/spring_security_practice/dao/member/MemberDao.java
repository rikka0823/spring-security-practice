package com.rikkachiu.spring_security_practice.dao.member;

import com.rikkachiu.spring_security_practice.model.pojo.Member;

public interface MemberDao {

    Member getMemberById(Integer memberId);

    Member getMemberByEmail(String email);

    Integer createMember(Member member);
}
