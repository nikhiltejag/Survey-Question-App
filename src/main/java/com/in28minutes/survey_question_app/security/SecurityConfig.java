package com.in28minutes.survey_question_app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().passwordEncoder(getPasswordEncoder())
        .withUser("user1").password("secret1").roles("USER")
        .and().withUser("admin1").password("secret1").roles("USER", "ADMIN");
    }

    protected void configure(HttpSecurity http) throws Exception{

        http.httpBasic().and().cors().and().authorizeRequests()
        .antMatchers("/welcome").permitAll()
        .antMatchers("/surveys/**").hasRole("USER")
        .antMatchers("/users/**").hasRole("USER")
        .antMatchers("/**").hasRole("ADMIN")
        .and().csrf().disable()
        .headers().frameOptions().disable();
    }

    private PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
