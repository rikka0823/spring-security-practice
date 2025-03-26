package com.rikkachiu.spring_security_practice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @GetMapping("/getMovies")
    public String getMovies(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getClaims().get("email"));
        return "取得電影列表";
    }

    @PostMapping("/watchFreeMovie")
    public String watchFreeMovie() {
        return "觀看免費電影";
    }

    @RequestMapping("/watchVipMovie")
    public String watchVipMovie() {
        return "觀看 VIP 付費電影";
    }

    @RequestMapping("/uploadMovie")
    public String uploadMovie() {
        return "上傳新電影";
    }

    @RequestMapping ("/deleteMovie")
    public String deleteMovie() {
        return "刪除電影";
    }
}
