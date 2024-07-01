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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
        http
                .csrf(AbstractHttpConfigurer::disable) // so that we don't need to work with tokens
                .authorizeHttpRequests
                        (authz -> authz

                                /* The Headmaster can see all endpoints, but only the Admin can edit them */

                                // Everyone can see the school they are from, but only the admin can edit
                                .requestMatchers(HttpMethod.POST, "/schools/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.GET, "/schools/**").hasAnyAuthority
                                        ("ADMINISTRATOR","HEADMASTER", "TEACHER", "CAREGIVER", "STUDENT")
                                .requestMatchers(HttpMethod.PUT, "/schools/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/schools/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/school-type/**").hasAnyAuthority
                                        ("ADMINISTRATOR","HEADMASTER", "TEACHER", "CAREGIVER", " STUDENT")
                                .requestMatchers(HttpMethod.POST, "/school-type/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/school-type/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/school-type/**").hasAuthority
                                        ("ADMINISTRATOR")

                                // Everyone can see the school class, but only the admin can edit
                                .requestMatchers(HttpMethod.GET, "/school-class/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER", "TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/school-class/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/school-class/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/school-class/**").hasAuthority
                                        ("ADMINISTRATOR")

                                // Everyone can see the curriculum, but only the admin can edit
                                .requestMatchers(HttpMethod.GET, "/student-curriculum/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER", "TEACHER", "CAREGIVER", "STUDENT")
                                .requestMatchers(HttpMethod.POST, "/student-curriculum/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/student-curriculum/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/student-curriculum/**").hasAuthority
                                        ("ADMINISTRATOR")

                                // Everyone can see the program for the semester, but only the admin can edit
                                .requestMatchers(HttpMethod.GET, "/curriculums-teachers-disciplines/**").hasAnyAuthority
                                        ("ADMINISTRATOR", "HEADMASTER", "TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/curriculums-teachers-disciplines/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/curriculums-teachers-disciplines/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/curriculums-teachers-disciplines/**").hasAuthority
                                        ("ADMINISTRATOR")

                                //
                                .requestMatchers(HttpMethod.GET, "/headmaster/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER")
                                .requestMatchers(HttpMethod.POST, "/headmaster/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/headmaster/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/headmaster/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/teacher/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER","TEACHER")
                                .requestMatchers(HttpMethod.POST, "/teacher/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/teacher/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/teacher/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/teacher-qualifications/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER","TEACHER")
                                .requestMatchers(HttpMethod.POST, "/teacher-qualifications/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/teacher-qualifications/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/teacher-qualifications/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/caregivers/**").hasAnyAuthority("ADMINISTRATOR","HEADMASTER","CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/caregivers/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/caregivers/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/caregivers/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/students/**").hasAnyAuthority("ADMINISTRATOR","HEADMASTER", "TEACHER","STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/students/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/students/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/students/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/disciplines/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER", "TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/disciplines/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/disciplines/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/disciplines/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/discipline-types/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER", "TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/discipline-types/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/discipline-types/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/discipline-types/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/gradeTypes/**").hasAnyAuthority("ADMINISTRATOR","HEADMASTER","TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/gradeTypes/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/gradeTypes/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/gradeTypes/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/absenceStatuses/**").hasAnyAuthority("ADMINISTRATOR","HEADMASTER","TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/absenceStatuses/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/absenceStatuses/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/absenceStatuses/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/absenceTypes/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER", "TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST, "/absenceTypes/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/absenceTypes/**").hasAuthority
                                        ("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/absenceTypes/**").hasAuthority
                                        ("ADMINISTRATOR")

                                .requestMatchers(HttpMethod.GET, "/grades/**").hasAnyAuthority("ADMINISTRATOR", "HEADMASTER", "TEACHER", "STUDENT", "CAREGIVER")
                                .requestMatchers(HttpMethod.POST,"/grades/**").hasAnyAuthority("ADMINISTRATOR","TEACHER")
                                .requestMatchers(HttpMethod.PUT,"/grades/**").hasAnyAuthority("ADMINISTRATOR","TEACHER")
                                .requestMatchers(HttpMethod.DELETE,"/grades/**").hasAnyAuthority("ADMINISTRATOR","TEACHER")

                                // So that the teacher can edit absences and the rest of them could access them
                                .requestMatchers(HttpMethod.GET, "/absences/**").hasAnyAuthority("ADMINISTRATOR","HEADMASTER", "TEACHER", "CAREGIVER", "STUDENT")
                                .requestMatchers(HttpMethod.POST, "/absences/**").hasAnyAuthority("ADMINISTRATOR","TEACHER")
                                .requestMatchers(HttpMethod.PUT, "/absences/**").hasAnyAuthority("ADMINISTRATOR","TEACHER")
                                .requestMatchers(HttpMethod.DELETE, "/absences/**").hasAnyAuthority("ADMINISTRATOR","TEACHER")

                                .anyRequest().authenticated()

                        )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}

