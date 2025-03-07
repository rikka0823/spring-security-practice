package com.rikkachiu.spring_security_practice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .csrf(csrf -> csrf.disable())
               .httpBasic(Customizer.withDefaults())
               .formLogin(Customizer.withDefaults())

               .authorizeHttpRequests(request -> request
//                               .requestMatchers("/**").permitAll()
                       .requestMatchers(HttpMethod.GET, "/test").authenticated()
                       .requestMatchers(HttpMethod.POST, "/register").permitAll()
//                       .anyRequest().authenticated()
                       .anyRequest().denyAll()
               )
               .build();
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails user1 = User
//                .withUsername("user1")
//                .password("{noop}user1")
//                .roles("ADMIN", "USER")
//                .build();
//
//        UserDetails user2 = User
//                .withUsername("user2")
//                .roles("USER")
//                .password("{bcrypt}$2a$10$gVX0NWXV3..h2JnjgDFBRe76qXFp7ut40ZEGeJyrDgg6hAilbV5a.")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}
