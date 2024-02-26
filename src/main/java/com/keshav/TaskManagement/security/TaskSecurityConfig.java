package com.keshav.TaskManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TaskSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userAndRoles()
    {
        UserDetails user1=User.builder().username("priyam").password("{noop}test1234").roles("Developer").build();
        UserDetails user2=User.builder().username("keshav").password("{noop}test1234").roles("Admin").build();
        UserDetails user3=User.builder().username("smriti").password("{noop}test1234").roles("Manager").build();
        return new InMemoryUserDetailsManager(user1,user2,user3);
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
