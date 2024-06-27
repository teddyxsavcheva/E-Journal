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

                                // Administrator can change all information in the system
                                .requestMatchers("/**").hasAuthority("ADMINISTRATOR")

                                // The teachers, parents, headmasters and students have access only to their school
                                //TODO: Maybe see how to implement this? Also, if I leave the two stars, will this be a problem as I would be able to get all schools?
                                .requestMatchers(HttpMethod.GET, "/schools/**").hasAnyAuthority("HEADMASTER", "TEACHER", "CAREGIVER", "STUDENT")

                                .requestMatchers(HttpMethod.GET, "/school-class/**").hasAnyAuthority("HEADMASTER", "TEACHER")
                                .requestMatchers(HttpMethod.GET, "/student-curriculum/**").hasAnyAuthority("HEADMASTER", "TEACHER")
                                .requestMatchers(HttpMethod.GET, "/curriculums-teachers-disciplines/**").hasAnyAuthority("HEADMASTER", "TEACHER", "STUDENT")

                                .requestMatchers(HttpMethod.GET, "/headmaster/**").hasAuthority("HEADMASTER")
                                .requestMatchers(HttpMethod.GET, "/teacher/**").hasAuthority("TEACHER")
                                .requestMatchers(HttpMethod.GET, "/caregivers/**").hasAuthority("CAREGIVER")
                                .requestMatchers(HttpMethod.GET, "/students/**").hasAuthority("STUDENT")

                                .requestMatchers(HttpMethod.GET, "/disciplines/**").hasAuthority("HEADMASTER")

                                .requestMatchers("/grades/**").hasAuthority("TEACHER")
                                .requestMatchers("/gradeTypes/**").hasAuthority("TEACHER")

                                .requestMatchers("/absences/**").hasAuthority("TEACHER")
                                .requestMatchers("/absenceStatuses/**").hasAuthority("TEACHER")
                                .requestMatchers("/absenceTypes/**").hasAuthority("TEACHER")

                                .requestMatchers(HttpMethod.GET, "/grades/**").hasAnyAuthority("CAREGIVER", "STUDENT", "HEADMASTER")
                                .requestMatchers(HttpMethod.GET, "/absences/**").hasAnyAuthority("HEADMASTER", "CAREGIVER", "STUDENT")
                                .anyRequest().authenticated()

                        )

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}

