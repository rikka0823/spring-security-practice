//package com.rikkachiu.spring_security_practice.security;
//
//import com.rikkachiu.spring_security_practice.dao.member.MemberDao;
//import com.rikkachiu.spring_security_practice.model.pojo.Member;
//import com.rikkachiu.spring_security_practice.model.pojo.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private MemberDao memberDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = memberDao.getMemberByEmail(username);
//
//        if (member == null) {
//            throw new UsernameNotFoundException("Not found: " + username);
//        }
//
//        String userName = member.getEmail();
//
//        String password = member.getPassword();
//
//        List<Role> roleList = memberDao.getRolesByMemberId(member.getMemberId());
//
////        List<GrantedAuthority> authorities = new ArrayList<>();
////
////        for (Role role : roleList) {
////            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
////        }
//
//        List<GrantedAuthority> authorities = convertToAuthorities(roleList);
//
//        return new User(userName, password, authorities);
//    }
//
//    private List<GrantedAuthority> convertToAuthorities(List<Role> roleList) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role role : roleList) {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        }
//
//        return authorities;
//    }
//
//}
