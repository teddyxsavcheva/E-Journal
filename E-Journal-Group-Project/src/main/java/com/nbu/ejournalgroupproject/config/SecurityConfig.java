package com.nbu.ejournalgroupproject.config;

import com.nbu.ejournalgroupproject.service.UserPrincipleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecurityConfig {

    private final UserPrincipleService userPrincipleService;

    public SecurityConfig(UserPrincipleService userPrincipleService) {
        this.userPrincipleService = userPrincipleService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userPrincipleService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests
                        (authz -> authz
                                // TODO: Ask how they will know which headmaster is currently logged? So that he could access only his school?
                                // The teachers, parents, headmasters and students have access only to their school
                                //TODO: Maybe see how to implement this? Also, if I leave the two stars, will this be a problem as I would be able to get all schools?
                                .requestMatchers(HttpMethod.GET, "/schools/**").hasAnyAuthority("HEADMASTER", "TEACHER", "CAREGIVER", "STUDENT")

                                // So that they can see the school classes
                                .requestMatchers(HttpMethod.GET, "/school-class/**").hasAnyAuthority("HEADMASTER", "TEACHER")
                                // So that they could see the curriculum? Idk if the teacher should be here or if I should add the student
                                .requestMatchers(HttpMethod.GET, "/student-curriculum/**").hasAnyAuthority("HEADMASTER", "TEACHER")
                                // So that headmaster, teacher and student could access the program?
                                .requestMatchers(HttpMethod.GET, "/curriculums-teachers-disciplines/**").hasAnyAuthority("HEADMASTER", "TEACHER", "STUDENT")

                                // So that each role can access their endpoints as well
                                .requestMatchers(HttpMethod.GET, "/headmaster/**").hasAuthority("HEADMASTER")
                                .requestMatchers(HttpMethod.GET, "/teacher/**").hasAuthority("TEACHER")
                                .requestMatchers(HttpMethod.GET, "/caregivers/**").hasAuthority("CAREGIVER")
                                .requestMatchers(HttpMethod.GET, "/students/**").hasAuthority("STUDENT")

                                // The headmaster can see all disciplines
                                .requestMatchers(HttpMethod.GET, "/disciplines/**").hasAuthority("HEADMASTER")

                                // Honestly idk if I even need those
                                .requestMatchers("/gradeTypes/**").hasAuthority("TEACHER")
                                .requestMatchers("/absenceStatuses/**").hasAuthority("TEACHER")
                                .requestMatchers("/absenceTypes/**").hasAuthority("TEACHER")

                                // So that the teacher can edit grades and the rest of them could access them
                                .requestMatchers(HttpMethod.GET, "/grades/**").hasAnyAuthority("CAREGIVER", "STUDENT", "HEADMASTER")
                                .requestMatchers("/grades/**").hasAuthority("TEACHER")

                                // So that the teacher can edit grades and the rest of them could access them
                                .requestMatchers(HttpMethod.GET, "/absences/**").hasAnyAuthority("HEADMASTER", "CAREGIVER", "STUDENT")
                                .requestMatchers("/absences/**").hasAuthority("TEACHER")

                                // Administrator can change all information in the system
                                // TODO: But if I enable it, the rest doesn't work?
                                //.requestMatchers("/**").hasAuthority("ADMINISTRATOR")

                                .anyRequest().authenticated()

                        )

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}

