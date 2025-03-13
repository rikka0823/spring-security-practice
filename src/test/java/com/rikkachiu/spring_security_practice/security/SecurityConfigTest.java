package com.rikkachiu.spring_security_practice.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMovies() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getMovies")
                .header("Authorization", "Basic dGVzdDNAZ21haWwuY29tOjMzMw==");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void watchFreeMovie() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("test1@gmail.com", "111"));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "mock", roles = {"NORMAL_MEMBER"})
    @Test
    public void watchVipMovie() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/watchVipMovie");
//                .with(httpBasic("test2@gmail.com", "222"));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCors() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .options("/hello")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://example.com");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(header().exists("Access-Control-ALLOW-Methods"))
                .andExpect(header().string("Access-Control-ALLOW-Methods", "POST,GET"))
                .andExpect(header().exists("Access-Control-ALLOW-Origin"))
                .andExpect(header().string("Access-Control-ALLOW-Origin", "*"));
    }

    @Test
    public void getMoviesNoCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("test2@gmail.com", "222"));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    public void getMoviesWithCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/watchFreeMovie")
                .with(httpBasic("test2@gmail.com", "222"))
                .with(csrf());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void helloNoCsrf() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/hello");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200));
    }
}