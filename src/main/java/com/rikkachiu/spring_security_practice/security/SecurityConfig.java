package com.rikkachiu.spring_security_practice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


//@EnableMethodSecurity
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//
//                .authorizeHttpRequests(request -> request
////                               .requestMatchers("/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/register").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/hello").hasAnyRole("ADMIN", "USER")
////                                .requestMatchers(HttpMethod.GET, "/test").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/test").hasRole("ADMIN")
////                                .anyRequest().authenticated()
//                                .anyRequest().denyAll()
//                )
//                .build();

        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/getMovies", "/watchFreeMovie").authenticated()
                        .requestMatchers("/watchVipMovie").hasAnyRole("ADMIN", "VIP_MEMBER")
                        .requestMatchers("/uploadMovie", "/deleteMovie").hasRole("ADMIN")

                        .requestMatchers("/api1").hasRole("ADMIN")
                        .requestMatchers("/api2").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api3")
                        .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasIpAddress('192.168.0.1/24')"))
                        .anyRequest().denyAll()
                )

                .addFilterBefore(new MyFilter2(), BasicAuthenticationFilter.class)
                .addFilterAfter(new MyFilter1(), BasicAuthenticationFilter.class)
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
//                .password("{bcrypt}$2a$10$.9DmXJJvZDDascfuN3Yd8ec.EzjJF101BWIo7U/2ePDmLORgmyZpK")
////                .password("{noop}user2")
//                .build();
//
//        UserDetails user3 = User
//                .withUsername("user3")
//                .password("{noop}user3")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }
}
