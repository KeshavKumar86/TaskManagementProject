package com.keshav.TaskManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class TaskSecurityConfig {
    @Bean
    public UserDetailsManager userAndRoles(DataSource dataSource)
    {
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer->
                                            configurer
                                                    .requestMatchers(HttpMethod.GET,"/api/tasks").hasRole("Developer")
                                                    .requestMatchers(HttpMethod.GET,"/api/tasks/**").hasRole("Developer")
                                                    .requestMatchers(HttpMethod.POST,"/api/tasks").hasRole("Manager")
                                                    .requestMatchers(HttpMethod.PUT,"/api/tasks/**").hasRole("Manager")
                                                    .requestMatchers(HttpMethod.DELETE,"/api/tasks/**").hasRole("Admin")
        );
        //use http basic authentication
        httpSecurity.httpBasic(Customizer.withDefaults());
        //disable csrf
        httpSecurity.csrf(csrf->csrf.disable());
        return httpSecurity.build();
    }
}
