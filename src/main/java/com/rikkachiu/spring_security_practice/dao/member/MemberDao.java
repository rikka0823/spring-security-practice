package com.rikkachiu.spring_security_practice.dao.member;

import com.rikkachiu.spring_security_practice.model.pojo.Member;
import com.rikkachiu.spring_security_practice.model.pojo.Role;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface MemberDao {

    Member getMemberById(Integer memberId);

    Member getMemberByEmail(String email);

    //    @PreAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("hasRole('ADMIN')")
    Integer createMember(Member member);

    List<Role> getRolesByMemberId(Integer memberId);
}
