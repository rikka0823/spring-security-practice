//package com.rikkachiu.spring_security_practice.controller;
//
//import com.rikkachiu.spring_security_practice.dao.member.MemberDao;
//import com.rikkachiu.spring_security_practice.model.pojo.Member;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
////@CrossOrigin(origins = "*")
//@RestController
//public class MemberController {
//
//    @Autowired
//    private MemberDao memberDao;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public Member register(@RequestBody Member member) {
//        member.setPassword(passwordEncoder.encode(member.getPassword()));
//
////        System.out.println("controller");
//
//        Integer id = memberDao.createMember(member);
//
//        return memberDao.getMemberById(id);
//    }
//}
