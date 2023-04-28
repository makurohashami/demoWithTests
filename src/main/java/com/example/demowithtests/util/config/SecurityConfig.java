package com.example.demowithtests.util.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: 18-Oct-22 Create 2 users for demo
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(getEncoder())
                .usersByUsernameQuery(
                        "select username, password, enabled from users_auth where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?");

    }

    // TODO: 18-Oct-22 Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/api/").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
