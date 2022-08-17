package com.mysite.SBBPractice2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/**").permitAll().and().csrf().ignoringAntMatchers("/h2-console/**").and().headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        return http.build();
    }
}
